package com.ig.igtask.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "BOOKMARK",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"stock_id", "user_id"})}
)
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @OneToOne(targetEntity = Stock.class)
    @NotNull(message = "Stock is mandatory")
    private Stock stock;

    @Column(name = "STOCK_PRICE")
    private float stockPrice;

    @OneToOne(targetEntity = User.class)
    @NotNull(message = "User data is mandatory")
    private User user;

    @Column(nullable = false, name = "CREATION_TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @Column(nullable = false, name = "UPDATE_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime updateTimestamp;

    public Bookmark(){};

    public Bookmark(Stock stock, float stockPrice, User user){
        this.stock = stock;
        this.stockPrice = stockPrice;
        this.user = user;
    }

    public Bookmark(Stock stock, User user){
        this.stock = stock;
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    public Stock getStock(){
        return this.stock;
    }

    public float getStockPrice(){
        return this.stockPrice;
    }

    public void setStockPrice(float stockPrice){
        this.stockPrice = stockPrice;
    }
}
