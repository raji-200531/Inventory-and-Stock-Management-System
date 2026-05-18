package com.inventory.stockmanagement.service.impl;

import com.inventory.stockmanagement.exception.*;
import com.inventory.stockmanagement.model.supplier.Supplier;
import com.inventory.stockmanagement.repository.SupplierFileRepository;
import com.inventory.stockmanagement.service.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierFileRepository repo; private final AuditFileService audit;
    public SupplierServiceImpl(SupplierFileRepository repo, AuditFileService audit) { this.repo = repo; this.audit = audit; }
    public List<Supplier> findAll() { return repo.findAll(); }
    public List<Supplier> search(String q) { return repo.search(q); }
    public Supplier findById(Long id) { return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier not found")); }
    public Supplier save(Supplier s) {
        if (s.getCompanyName() == null || s.getCompanyName().isBlank()) throw new InvalidOperationException("Company name is required");
        if (s.getName() == null || s.getName().isBlank()) throw new InvalidOperationException("Contact person name is required");
        boolean create = s.getId() == null;
        Supplier saved = repo.save(s);
        audit.log("SUPPLIER", create ? "CREATE" : "UPDATE", saved.displayDetails());
        return saved;
    }
    public void delete(Long id) { Supplier s = findById(id); repo.deleteById(id); audit.log("SUPPLIER", "DELETE", s.displayDetails()); }
}
