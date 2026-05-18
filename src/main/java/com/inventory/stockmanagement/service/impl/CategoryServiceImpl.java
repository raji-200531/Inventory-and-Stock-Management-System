package com.inventory.stockmanagement.service.impl;

import com.inventory.stockmanagement.exception.*;
import com.inventory.stockmanagement.model.product.Category;
import com.inventory.stockmanagement.repository.CategoryFileRepository;
import com.inventory.stockmanagement.service.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryFileRepository repo; private final AuditFileService audit;
    public CategoryServiceImpl(CategoryFileRepository repo, AuditFileService audit) { this.repo = repo; this.audit = audit; }
    public List<Category> findAll() { return repo.findAll(); }
    public List<Category> search(String q) { return repo.search(q); }
    public Category findById(Long id) { return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found")); }
    public Category save(Category c) {
        if (c.getName() == null || c.getName().isBlank()) throw new InvalidOperationException("Category name is required");
        boolean create = c.getId() == null;
        Category saved = repo.save(c);
        audit.log("CATEGORY", create ? "CREATE" : "UPDATE", saved.displayDetails());
        return saved;
    }
    public void delete(Long id) { Category c = findById(id); repo.deleteById(id); audit.log("CATEGORY", "DELETE", c.displayDetails()); }
}
