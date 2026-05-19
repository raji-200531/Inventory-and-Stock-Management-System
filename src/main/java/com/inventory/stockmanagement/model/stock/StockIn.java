package com.inventory.stockmanagement.model.stock;

import java.time.LocalDate;

public class StockIn extends StockTransaction {
    private Long supplierId;

    public StockIn() {

    }

    public StockIn(Long productId, Long supplierId, int quantity, LocalDate transactionDate, String note) {
        super(productId, quantity, transactionDate, note);
        this.supplierId = supplierId;
    }

    @Override
    public int calculateStockEffect() {
        return getQuantity();
    }

    @Override
    public String getTransactionType() {
        return "STOCK_IN";
    }

    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }
}
