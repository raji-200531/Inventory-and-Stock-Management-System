package com.inventory.stockmanagement.service.impl;

import com.inventory.stockmanagement.exception.*;
import com.inventory.stockmanagement.model.user.*;
import com.inventory.stockmanagement.repository.UserAccountFileRepository;
import com.inventory.stockmanagement.service.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserAccountFileRepository repo; private final AuditFileService audit;
    public UserServiceImpl(UserAccountFileRepository repo, AuditFileService audit) { this.repo = repo; this.audit = audit; }
    public List<UserAccount> findAll() { return repo.findAll(); }
    public List<UserAccount> search(String q) { return repo.search(q); }
    public UserAccount findById(Long id) { return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found")); }
    public UserAccount save(UserAccount u) {
        validate(u); boolean create = u.getId() == null;
        if (create && repo.existsByUsername(u.getUsername())) throw new DuplicateResourceException("Username already exists");
        if (!create) {
            UserAccount old = findById(u.getId());
            if (!old.getUsername().equalsIgnoreCase(u.getUsername()) && repo.existsByUsername(u.getUsername())) throw new DuplicateResourceException("Username already exists");
        }
        UserAccount roleUser = copyAsRole(u);
        UserAccount saved = repo.save(roleUser); audit.log("USER", create ? "CREATE" : "UPDATE", saved.displayDetails()); return saved;
    }
    public void delete(Long id) { UserAccount u = findById(id); repo.deleteById(id); audit.log("USER", "DELETE", u.displayDetails()); }
    public Optional<UserAccount> authenticate(String username, String password) { return repo.findByUsername(username).filter(UserAccount::isActive).filter(u -> u.getPassword() != null && u.getPassword().equals(password)); }
    private void validate(UserAccount u) { if (u.getName()==null || u.getName().isBlank()) throw new InvalidOperationException("Name is required"); if (u.getUsername()==null || u.getUsername().isBlank()) throw new InvalidOperationException("Username is required"); if (u.getPassword()==null || u.getPassword().isBlank()) throw new InvalidOperationException("Password is required"); if (u.getRole()==null) u.setRole(UserRole.STAFF); }
    private UserAccount copyAsRole(UserAccount s) {
        UserAccount t = s.getRole() == UserRole.ADMIN ? new AdminUser() : new StaffUser();
        t.setId(s.getId()); t.setCreatedAt(s.getCreatedAt()); t.setUpdatedAt(s.getUpdatedAt()); t.setName(s.getName()); t.setEmail(s.getEmail()); t.setPhone(s.getPhone()); t.setAddress(s.getAddress()); t.setUsername(s.getUsername()); t.setPassword(s.getPassword()); t.setRole(s.getRole()); t.setActive(s.isActive()); return t;
    }
}
