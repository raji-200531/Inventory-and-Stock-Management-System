package com.inventory.stockmanagement.service.impl;

import com.inventory.stockmanagement.exception.*;
import com.inventory.stockmanagement.model.product.Product;
import com.inventory.stockmanagement.model.stock.StockOut;
import com.inventory.stockmanagement.repository.StockOutFileRepository;
import com.inventory.stockmanagement.service.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class StockOutServiceImpl implements StockOutService {
    private final StockOutFileRepository repo; private final ProductService products; private final AuditFileService audit;
    public StockOutServiceImpl(StockOutFileRepository repo, ProductService products, AuditFileService audit) { this.repo = repo; this.products = products; this.audit = audit; }
    public List<StockOut> findAll() { return repo.findAll(); }
    public StockOut findById(Long id) { return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Stock-out record not found")); }
    public StockOut create(StockOut s) { validate(s); Product p = products.findById(s.getProductId()); p.decreaseStock(s.getQuantity()); products.saveWithoutAudit(p); StockOut saved = repo.save(s); audit.log("STOCK_OUT", "CREATE", saved.displayDetails() + " | " + saved.calculateStockEffect()); return saved; }
    public StockOut update(Long id, StockOut s) {
        StockOut old = findById(id); validate(s);
        Product oldProduct = products.findById(old.getProductId()); oldProduct.increaseStock(old.getQuantity()); products.saveWithoutAudit(oldProduct);
        Product newProduct = products.findById(s.getProductId()); newProduct.decreaseStock(s.getQuantity()); products.saveWithoutAudit(newProduct);
        s.setId(id); StockOut saved = repo.save(s); audit.log("STOCK_OUT", "UPDATE", saved.displayDetails()); return saved;
    }
    public void delete(Long id) { StockOut s = findById(id); Product p = products.findById(s.getProductId()); p.increaseStock(s.getQuantity()); products.saveWithoutAudit(p); repo.deleteById(id); audit.log("STOCK_OUT", "DELETE", s.displayDetails()); }
    private void validate(StockOut s) { if (s.getProductId() == null) throw new InvalidOperationException("Product is required"); if (s.getQuantity() <= 0) throw new InvalidOperationException("Quantity must be greater than zero"); if (s.getTransactionDate() == null) s.setTransactionDate(LocalDate.now()); }
}
