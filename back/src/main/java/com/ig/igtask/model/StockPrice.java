package com.ig.igtask.model;

public class StockPrice{
    private Stock stock;
    private double currentPrice;

    public StockPrice(Stock stock, double currentPrice) {
        this.stock = stock;
        this.currentPrice = currentPrice;
    }

    public Stock getStock() {
        return stock;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }
}