package com.inventory.stockmanagement.repository;

import com.inventory.stockmanagement.model.stock.StockOut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public class StockOutFileRepository extends GenericFileRepository<StockOut> {
    public StockOutFileRepository(@Value("${app.storage-dir:data}") String dir) {
        super(dir, "stock_out.txt", new FileMapper<>() {
            public String toLine(StockOut x) { return TextFileUtil.join(s(x.getId()), s(x.getCreatedAt()), s(x.getUpdatedAt()), s(x.getProductId()), s(x.getQuantity()), s(x.getTransactionDate()), x.getReason(), x.getNote()); }
            public StockOut fromLine(String line) {
                String[] p = TextFileUtil.split(line);
                StockOut x = new StockOut();
                x.setId(l(p.length>0?p[0]:"")); x.setCreatedAt(dt(p.length>1?p[1]:"")); x.setUpdatedAt(dt(p.length>2?p[2]:""));
                x.setProductId(l(p.length>3?p[3]:"")); x.setQuantity(i(p.length>4?p[4]:"0")); x.setTransactionDate(LocalDate.parse((p.length>5 && !p[5].isBlank()) ? p[5] : LocalDate.now().toString())); x.setReason(p.length>6?p[6]:""); x.setNote(p.length>7?p[7]:"");
                return x;
            }
        });
    }
}
