package com.inventory.stockmanagement.model.stock;

import com.inventory.stockmanagement.model.base.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public abstract class StockTransaction extends BaseEntity {
    private Long productId;
    private int quantity;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate transactionDate = LocalDate.now();
    private String note;

    protected StockTransaction() {}

    protected StockTransaction(Long productId, int quantity, LocalDate transactionDate, String note) {
        this.productId = productId;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
        this.note = note;
    }

    public abstract int calculateStockEffect();
    public abstract String getTransactionType();

    @Override
    public String displayDetails() {
        return getTransactionType() + " | Product ID: " + productId + " | Qty: " + quantity + " | Date: " + transactionDate;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public LocalDate getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
