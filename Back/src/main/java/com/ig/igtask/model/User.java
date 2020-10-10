package com.ig.igtask.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "identifier", unique = true)
    @NotBlank(message = "You need to provide user identifier")
    private String identifier;

    public long getId(){
        return this.id;
    }

    public String getIdentifier(){
        return this.identifier;
    }

    public User(){};

    public User(String identifier){
        this.identifier = identifier;
    }
}
