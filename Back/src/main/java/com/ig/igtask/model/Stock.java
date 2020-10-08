package com.ig.igtask.model;

import javax.persistence.*;

@Entity
@Table(name = "STOCK")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "STOCK_NAME")
    private String stockName;

    @Transient
    private double currentPrice = 1 + Math.random() * (1500 - 1);

    public int getId() {
        return this.id;
    }

    public String getStockName() {
        return this.stockName;
    }

    public double getCurrentPrice(){
        return this.currentPrice;
    }
}
