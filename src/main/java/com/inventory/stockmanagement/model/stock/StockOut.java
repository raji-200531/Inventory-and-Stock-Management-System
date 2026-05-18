package com.inventory.stockmanagement.model.stock;

import java.time.LocalDate;

public class StockOut extends StockTransaction {
    private String reason;

    public StockOut() {}
    public StockOut(Long productId, int quantity, LocalDate transactionDate, String reason, String note) {
        super(productId, quantity, transactionDate, note);
        this.reason = reason;
    }

    @Override
    public int calculateStockEffect() {
        return -getQuantity();
    }

    @Override
    public String getTransactionType() {
        return "STOCK_OUT";
    }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
