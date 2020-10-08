package com.ig.igtask.model;

import javax.persistence.*;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "identifier")
    private String identifier;
}
