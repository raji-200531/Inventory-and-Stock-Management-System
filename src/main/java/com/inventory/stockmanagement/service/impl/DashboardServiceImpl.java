package com.inventory.stockmanagement.service.impl;

import com.inventory.stockmanagement.dto.DashboardDTO;
import com.inventory.stockmanagement.service.*;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final ProductService products; private final CategoryService categories; private final SupplierService suppliers; private final StockInService stockIns; private final StockOutService stockOuts; private final UserService users;
    public DashboardServiceImpl(ProductService products, CategoryService categories, SupplierService suppliers, StockInService stockIns, StockOutService stockOuts, UserService users) { this.products = products; this.categories = categories; this.suppliers = suppliers; this.stockIns = stockIns; this.stockOuts = stockOuts; this.users = users; }
    public DashboardDTO getDashboard() {
        DashboardDTO d = new DashboardDTO();
        d.setProductCount(products.findAll().size()); d.setCategoryCount(categories.findAll().size()); d.setSupplierCount(suppliers.findAll().size()); d.setStockInCount(stockIns.findAll().size()); d.setStockOutCount(stockOuts.findAll().size()); d.setUserCount(users.findAll().size()); d.setLowStockProducts(products.findLowStockProducts()); return d;
    }
}
