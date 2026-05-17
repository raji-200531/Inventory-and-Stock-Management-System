package com.inventory.stockmanagement.service.impl;

import com.inventory.stockmanagement.exception.*;
import com.inventory.stockmanagement.model.product.Product;
import com.inventory.stockmanagement.repository.ProductFileRepository;
import com.inventory.stockmanagement.service.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductFileRepository repo; private final AuditFileService audit;
    public ProductServiceImpl(ProductFileRepository repo, AuditFileService audit) { this.repo = repo; this.audit = audit; }
    public List<Product> findAll() { return repo.findAll(); }
    public List<Product> search(String q) { return repo.search(q); }
    public List<Product> findLowStockProducts() { return repo.findLowStockProducts(); }
    public Product findById(Long id) { return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found")); }
    public Product save(Product p) {
        validate(p); boolean create = p.getId() == null;
        if (create && repo.existsBySkuIgnoreCase(p.getSku())) throw new DuplicateResourceException("SKU already exists");
        if (!create) {
            Product old = findById(p.getId());
            if (!old.getSku().equalsIgnoreCase(p.getSku()) && repo.existsBySkuIgnoreCase(p.getSku())) throw new DuplicateResourceException("SKU already exists");
        }
        Product saved = repo.save(p); audit.log("PRODUCT", create ? "CREATE" : "UPDATE", saved.displayDetails()); return saved;
    }
    public Product saveWithoutAudit(Product p) { validate(p); return repo.save(p); }
    public void delete(Long id) { Product p = findById(id); repo.deleteById(id); audit.log("PRODUCT", "DELETE", p.displayDetails()); }
    private void validate(Product p) {
        if (p.getSku() == null || p.getSku().isBlank()) throw new InvalidOperationException("SKU is required");
        if (p.getName() == null || p.getName().isBlank()) throw new InvalidOperationException("Product name is required");
        if (p.getUnitPrice() == null) p.setUnitPrice(BigDecimal.ZERO);
        if (p.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) throw new InvalidOperationException("Unit price cannot be negative");
        if (p.getQuantity() < 0) throw new InvalidOperationException("Quantity cannot be negative");
        if (p.getReorderLevel() < 0) throw new InvalidOperationException("Reorder level cannot be negative");
    }
}
