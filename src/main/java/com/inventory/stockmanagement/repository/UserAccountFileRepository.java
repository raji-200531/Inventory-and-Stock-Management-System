package com.inventory.stockmanagement.repository;

import com.inventory.stockmanagement.model.user.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class UserAccountFileRepository extends GenericFileRepository<UserAccount> {
    public UserAccountFileRepository(@Value("${app.storage-dir:data}") String dir) {
        super(dir, "users.txt", new FileMapper<>() {
            public String toLine(UserAccount x) { return TextFileUtil.join(s(x.getId()), s(x.getCreatedAt()), s(x.getUpdatedAt()), x.getName(), x.getEmail(), x.getPhone(), x.getAddress(), x.getUsername(), x.getPassword(), s(x.getRole()), s(x.isActive())); }
            public UserAccount fromLine(String line) {
                String[] p = TextFileUtil.split(line);
                UserRole role = UserRole.valueOf((p.length>9 && !p[9].isBlank()) ? p[9] : "STAFF");
                UserAccount x = role == UserRole.ADMIN ? new AdminUser() : new StaffUser();
                x.setId(l(p.length>0?p[0]:"")); x.setCreatedAt(dt(p.length>1?p[1]:"")); x.setUpdatedAt(dt(p.length>2?p[2]:""));
                x.setName(p.length>3?p[3]:""); x.setEmail(p.length>4?p[4]:""); x.setPhone(p.length>5?p[5]:""); x.setAddress(p.length>6?p[6]:"");
                x.setUsername(p.length>7?p[7]:""); x.setPassword(p.length>8?p[8]:""); x.setRole(role); x.setActive(b(p.length>10?p[10]:"true"));
                return x;
            }
        });
    }
    public Optional<UserAccount> findByUsername(String username) {
        return findAll().stream().filter(x -> x.getUsername() != null && x.getUsername().equalsIgnoreCase(username)).findFirst();
    }
    public boolean existsByUsername(String username) { return findByUsername(username).isPresent(); }
    public List<UserAccount> search(String keyword) {
        String q = keyword == null ? "" : keyword.toLowerCase();
        return findAll().stream().filter(x -> q.isBlank() || x.getUsername().toLowerCase().contains(q) || x.getName().toLowerCase().contains(q) || x.getRole().name().toLowerCase().contains(q)).toList();
    }
}
