package com.ig.igtask.model;

import org.apache.commons.math3.util.Precision;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "STOCK")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "STOCK_NAME", unique = true)
    @NotBlank(message = "Stock name is mandatory")
    private String stockName;

    @Transient
    private final double currentPrice = Precision.round(1 + Math.random() * (1500 - 1), 2);

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

    public double getCurrentPrice(){
        return this.currentPrice;
    }
}
