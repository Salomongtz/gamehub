package com.example.gamehub.models;

import com.example.gamehub.records.PurchaseRecord;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName, lastName, email, password;
    @Enumerated(EnumType.STRING)
    private RoleType role = RoleType.CUSTOMER;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Purchase> purchases;
    @ManyToMany(mappedBy = "customers")
    private List<Games> games = new ArrayList<>();

    private List <PurchaseRecord> cart = new ArrayList<>();

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public void addPurchase(Purchase purchase) {
        purchase.setCustomer(this);
        purchases.add(purchase);
    }

    public void addGame(Games game) {
        game.getCustomers().add(this);
        games.add(game);
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

    public List<Games> getGames() {
        return games;
    }

    public void setGames(List<Games> games) {
        this.games = games;
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
                '}';
    }

    public List<PurchaseRecord> getCart() {
        return cart;
    }

    public void setCart(List<PurchaseRecord> cart) {
        this.cart = cart;
    }
}
