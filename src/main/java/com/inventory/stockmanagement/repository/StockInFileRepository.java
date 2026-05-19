package com.inventory.stockmanagement.repository;

import com.inventory.stockmanagement.model.stock.StockIn;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public class StockInFileRepository extends GenericFileRepository<StockIn> {
    public StockInFileRepository(@Value("${app.storage-dir:data}") String dir) {
        super(dir, "stock_in.txt", new FileMapper<>() {
            public String toLine(StockIn x) { return TextFileUtil.join(s(x.getId()), s(x.getCreatedAt()), s(x.getUpdatedAt()), s(x.getProductId()), s(x.getSupplierId()), s(x.getQuantity()), s(x.getTransactionDate()), x.getNote()); }
            public StockIn fromLine(String line) {
                String[] p = TextFileUtil.split(line);
                StockIn x = new StockIn();
                x.setId(l(p.length>0?p[0]:"")); x.setCreatedAt(dt(p.length>1?p[1]:"")); x.setUpdatedAt(dt(p.length>2?p[2]:""));
                x.setProductId(l(p.length>3?p[3]:"")); x.setSupplierId(l(p.length>4?p[4]:"")); x.setQuantity(i(p.length>5?p[5]:"0")); x.setTransactionDate(LocalDate.parse((p.length>6 && !p[6].isBlank()) ? p[6] : LocalDate.now().toString())); x.setNote(p.length>7?p[7]:"");
                return x;
            }
        });
    }
}
