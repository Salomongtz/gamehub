package com.example.gamehub.models;

import jakarta.persistence.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name, lastName, email, password;
    private RoleType role=RoleType.CUSTOMER;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Purchase purchase;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Customer_Game customer_game;

    public Customer() {
    }

    public Customer(String name, String lastName, String email, String password, RoleType role) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
