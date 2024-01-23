package com.example.gamehub.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name, lastName, email, password;
    @Enumerated(EnumType.STRING)
    private RoleType role=RoleType.CUSTOMER;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Purchase> purchases;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Customer_Game> customer_games;

    public Customer() {
    }

    public Customer(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public List<Customer_Game> getCustomer_games() {
        return customer_games;
    }

    public void setCustomer_games(List<Customer_Game> customer_games) {
        this.customer_games = customer_games;
    }
}
