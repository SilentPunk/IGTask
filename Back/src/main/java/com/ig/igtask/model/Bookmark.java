package com.ig.igtask.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BOOKMARK")
public class Bookmark {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @OneToOne(targetEntity = Stock.class)
    private Stock stock;

    @Column(name = "STOCK_PRICE")
    private float stockPrice;

    @OneToOne(targetEntity = User.class)
    private User user;

    @Column(nullable = false, name = "CREATION_TIMESTAMP")
    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @Column(nullable = false, name = "UPDATE_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime updateTimestamp;

    public Bookmark(Stock stock, float stockPrice, User user){
        this.stock = stock;
        this.stockPrice = stockPrice;
        this.user = user;
    }

}
