package com.ig.igtask.model;

import org.apache.commons.math3.util.Precision;

import javax.persistence.*;

@Entity
@Table(name = "STOCK")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "STOCK_NAME")
    private String stockName;

    public Stock(){};

    public Stock(String stockName){
        this.stockName = stockName;
    }

    public long getId() {
        return this.id;
    }

    public String getStockName() {
        return this.stockName;
    }

    @Transient
    public double getCurrentPrice(){
        return Precision.round(1 + Math.random() * (1500 - 1), 2);
    }
}
