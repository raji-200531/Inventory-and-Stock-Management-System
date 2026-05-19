package com.inventory.stockmanagement.service.impl;

import com.inventory.stockmanagement.exception.*;
import com.inventory.stockmanagement.model.product.Product;
import com.inventory.stockmanagement.model.stock.StockIn;
import com.inventory.stockmanagement.repository.StockInFileRepository;
import com.inventory.stockmanagement.service.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class StockInServiceImpl implements StockInService {
    private final StockInFileRepository repo; private final ProductService products; private final SupplierService suppliers; private final AuditFileService audit;
    public StockInServiceImpl(StockInFileRepository repo, ProductService products, SupplierService suppliers, AuditFileService audit) {
        this.repo = repo;
        this.products = products;
        this.suppliers = suppliers;
        this.audit = audit; }
    public List<StockIn> findAll() { return repo.findAll(); }
    public StockIn findById(Long id) { return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Stock-in record not found")); }

    public StockIn create(StockIn s) {
        validate(s); Product p = products.findById(s.getProductId());  //find product
        suppliers.findById(s.getSupplierId());
        p.increaseStock(s.getQuantity());
        products.saveWithoutAudit(p); //save updated product
        StockIn saved = repo.save(s); audit.log("STOCK_IN", "CREATE", saved.displayDetails() + " | +" + saved.calculateStockEffect());
        return saved; }

    public StockIn update(Long id, StockIn s) {
        StockIn old = findById(id); validate(s);
        Product oldProduct = products.findById(old.getProductId());
        oldProduct.decreaseStock(old.getQuantity());
        products.saveWithoutAudit(oldProduct);
        //Apply new quantity
        Product newProduct = products.findById(s.getProductId()); suppliers.findById(s.getSupplierId());
        newProduct.increaseStock(s.getQuantity());
        products.saveWithoutAudit(newProduct);
        s.setId(id); StockIn saved = repo.save(s); audit.log("STOCK_IN", "UPDATE", saved.displayDetails());
        return saved;
    }
    public void delete(Long id) {
        StockIn s = findById(id);
        Product p = products.findById(s.getProductId());
        p.decreaseStock(s.getQuantity());
        products.saveWithoutAudit(p);
        repo.deleteById(id);
        audit.log("STOCK_IN", "DELETE", s.displayDetails()); }

    //Validation part
    private void validate(StockIn s) {
        if (s.getProductId() == null) throw new InvalidOperationException("Product is required");
        if (s.getSupplierId() == null) throw new InvalidOperationException("Supplier is required");
        if (s.getQuantity() <= 0) throw new InvalidOperationException("Quantity must be greater than zero");
        if (s.getTransactionDate() == null) s.setTransactionDate(LocalDate.now()); }
}
