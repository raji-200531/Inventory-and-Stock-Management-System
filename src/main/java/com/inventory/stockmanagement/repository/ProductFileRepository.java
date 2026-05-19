package com.inventory.stockmanagement.repository;

import com.inventory.stockmanagement.model.product.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class ProductFileRepository extends GenericFileRepository<Product> {
    public ProductFileRepository(@Value("${app.storage-dir:data}") String dir) {
        super(dir, "products.txt", new FileMapper<>() {
            public String toLine(Product x) { return TextFileUtil.join(s(x.getId()), s(x.getCreatedAt()), s(x.getUpdatedAt()), x.getSku(), x.getName(), x.getDescription(), s(x.getUnitPrice()), s(x.getQuantity()), s(x.getReorderLevel()), s(x.getCategoryId()), s(x.getSupplierId()), s(x.isActive())); }
            public Product fromLine(String line) {
                String[] p = TextFileUtil.split(line);
                Product x = new Product();
                x.setId(l(p.length>0?p[0]:"")); x.setCreatedAt(dt(p.length>1?p[1]:"")); x.setUpdatedAt(dt(p.length>2?p[2]:""));
                x.setSku(p.length>3?p[3]:""); x.setName(p.length>4?p[4]:""); x.setDescription(p.length>5?p[5]:"");
                x.setUnitPrice(new BigDecimal((p.length>6 && !p[6].isBlank()) ? p[6] : "0")); x.setQuantity(i(p.length>7?p[7]:"0")); x.setReorderLevel(i(p.length>8?p[8]:"5")); x.setCategoryId(l(p.length>9?p[9]:"")); x.setSupplierId(l(p.length>10?p[10]:"")); x.setActive(b(p.length>11?p[11]:"true"));
                return x;
            }
        });
    }
    public List<Product> search(String keyword) {
        String q = keyword == null ? "" : keyword.toLowerCase();
        return findAll().stream().filter(x -> q.isBlank() || x.getSku().toLowerCase().contains(q) || x.getName().toLowerCase().contains(q)).toList();
    }
    public boolean existsBySkuIgnoreCase(String sku) {
        return findAll().stream().anyMatch(x -> x.getSku() != null && x.getSku().equalsIgnoreCase(sku));
    }
    public List<Product> findLowStockProducts() {
        return findAll().stream().filter(Product::isLowStock).toList();
    }
}
