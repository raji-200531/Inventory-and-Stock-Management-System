package com.inventory.stockmanagement.model.product;

import com.inventory.stockmanagement.exception.InvalidOperationException;
import com.inventory.stockmanagement.model.base.BaseEntity;

import java.math.BigDecimal;

public class Product extends BaseEntity {
    private String sku;
    private String name;
    private String description;
    private BigDecimal unitPrice = BigDecimal.ZERO;
    private int quantity;
    private int reorderLevel = 5;
    private Long categoryId;
    private Long supplierId;
    private boolean active = true;

    public Product() {}

    public Product(String sku, String name, String description, BigDecimal unitPrice, int quantity, int reorderLevel, Long categoryId, Long supplierId) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
    }

    public void increaseStock(int amount) {
        if (amount <= 0) throw new InvalidOperationException("Stock-in quantity must be greater than zero");
        quantity += amount;
    }

    public void decreaseStock(int amount) {
        if (amount <= 0) throw new InvalidOperationException("Stock-out quantity must be greater than zero");
        if (quantity < amount) throw new InvalidOperationException("Not enough stock for " + name);
        quantity -= amount;
    }

    public boolean isLowStock() {
        return quantity <= reorderLevel;
    }

    @Override
    public String displayDetails() {
        return sku + " - " + name + " | Qty: " + quantity + " | Price: " + unitPrice;
    }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(int reorderLevel) { this.reorderLevel = reorderLevel; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
