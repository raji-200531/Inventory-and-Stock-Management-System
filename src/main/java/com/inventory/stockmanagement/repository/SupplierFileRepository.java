package com.inventory.stockmanagement.repository;

import com.inventory.stockmanagement.model.supplier.Supplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class SupplierFileRepository extends GenericFileRepository<Supplier> {
    public SupplierFileRepository(@Value("${app.storage-dir:data}") String dir) {
        super(dir, "suppliers.txt", new FileMapper<>() {
            public String toLine(Supplier x) { return TextFileUtil.join(s(x.getId()), s(x.getCreatedAt()), s(x.getUpdatedAt()), x.getName(), x.getCompanyName(), x.getEmail(), x.getPhone(), x.getAddress(), s(x.isActive())); }
            public Supplier fromLine(String line) {
                String[] p = TextFileUtil.split(line);
                Supplier x = new Supplier();
                x.setId(l(p.length>0?p[0]:"")); x.setCreatedAt(dt(p.length>1?p[1]:"")); x.setUpdatedAt(dt(p.length>2?p[2]:""));
                x.setName(p.length>3?p[3]:""); x.setCompanyName(p.length>4?p[4]:""); x.setEmail(p.length>5?p[5]:""); x.setPhone(p.length>6?p[6]:""); x.setAddress(p.length>7?p[7]:""); x.setActive(b(p.length>8?p[8]:"true"));
                return x;
            }
        });
    }
    public List<Supplier> search(String keyword) {
        String q = keyword == null ? "" : keyword.toLowerCase();
        return findAll().stream().filter(x -> q.isBlank() || x.getCompanyName().toLowerCase().contains(q) || x.getName().toLowerCase().contains(q) || x.getEmail().toLowerCase().contains(q)).toList();
    }
}
