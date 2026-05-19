package com.inventory.stockmanagement.service;
import com.inventory.stockmanagement.model.stock.StockIn; import java.util.List;
public interface StockInService { List<StockIn> findAll();
    StockIn findById(Long id); StockIn create(StockIn s);
    StockIn update(Long id, StockIn s); void delete(Long id); }