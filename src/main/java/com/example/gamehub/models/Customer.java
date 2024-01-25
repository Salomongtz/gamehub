package com.example.gamehub.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName, lastName, email, password;
    @Enumerated(EnumType.STRING)
    private RoleType role=RoleType.CUSTOMER;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Purchase> purchases;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Customer_Game> customer_games;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public void addPurchase(Purchase purchase){
        purchase.setCustomer(this);
        purchases.add(purchase);
    }

    public void addCustomer_game(Customer_Game customer_game){
        customer_game.setCustomer(this);
        customer_games.add(customer_game);
    }
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", purchases=" + purchases +
                ", customer_games=" + customer_games +
                '}';
    }
}
