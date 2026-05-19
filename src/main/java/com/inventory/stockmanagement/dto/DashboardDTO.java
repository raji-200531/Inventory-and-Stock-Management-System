package com.inventory.stockmanagement.dto;

import com.inventory.stockmanagement.model.product.Product;
import java.util.List;

public class DashboardDTO {
    private long productCount;
    private long categoryCount;
    private long supplierCount;
    private long stockInCount;
    private long stockOutCount;
    private long userCount;
    private List<Product> lowStockProducts;

    public long getProductCount() { return productCount; }
    public void setProductCount(long productCount) { this.productCount = productCount; }
    public long getCategoryCount() { return categoryCount; }
    public void setCategoryCount(long categoryCount) { this.categoryCount = categoryCount; }
    public long getSupplierCount() { return supplierCount; }
    public void setSupplierCount(long supplierCount) { this.supplierCount = supplierCount; }
    public long getStockInCount() { return stockInCount; }
    public void setStockInCount(long stockInCount) { this.stockInCount = stockInCount; }
    public long getStockOutCount() { return stockOutCount; }
    public void setStockOutCount(long stockOutCount) { this.stockOutCount = stockOutCount; }
    public long getUserCount() { return userCount; }
    public void setUserCount(long userCount) { this.userCount = userCount; }
    public List<Product> getLowStockProducts() { return lowStockProducts; }
    public void setLowStockProducts(List<Product> lowStockProducts) { this.lowStockProducts = lowStockProducts; }
}
