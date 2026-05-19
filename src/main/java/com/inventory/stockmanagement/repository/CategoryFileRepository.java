package com.inventory.stockmanagement.repository;

import com.inventory.stockmanagement.model.product.Category;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CategoryFileRepository extends GenericFileRepository<Category> {
    public CategoryFileRepository(@Value("${app.storage-dir:data}") String dir) {
        super(dir, "categories.txt", new FileMapper<>() {
            public String toLine(Category c) { return TextFileUtil.join(s(c.getId()), s(c.getCreatedAt()), s(c.getUpdatedAt()), c.getName(), c.getDescription(), s(c.isActive())); }
            public Category fromLine(String line) {
                String[] p = TextFileUtil.split(line);
                Category c = new Category();
                c.setId(l(p.length>0?p[0]:"")); c.setCreatedAt(dt(p.length>1?p[1]:"")); c.setUpdatedAt(dt(p.length>2?p[2]:""));
                c.setName(p.length>3?p[3]:""); c.setDescription(p.length>4?p[4]:""); c.setActive(b(p.length>5?p[5]:"true"));
                return c;
            }
        });
    }
    public List<Category> search(String keyword) {
        String q = keyword == null ? "" : keyword.toLowerCase();
        return findAll().stream().filter(c -> q.isBlank() || c.getName().toLowerCase().contains(q) || c.getDescription().toLowerCase().contains(q)).toList();
    }
}
