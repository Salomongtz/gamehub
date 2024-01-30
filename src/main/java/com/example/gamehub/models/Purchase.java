package com.example.gamehub.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate purchaseDate;
    private Double totalAmount;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "purchase")
    private List<Purchase_Game> purchaseGames = new ArrayList<>();

    public Purchase() {
    }

    public Purchase(LocalDate purchaseDate, Double totalAmount) {
        this.purchaseDate = purchaseDate;
        this.totalAmount = totalAmount;
    }

    public void addPurchaseGame(Purchase_Game purchaseGame) {
        purchaseGame.setPurchase(this);
        purchaseGames.add(purchaseGame);
    }

    public Long getId() {
        return id;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Purchase_Game> getPurchaseGames() {
        return purchaseGames;
    }

    public void setPurchaseGames(List<Purchase_Game> purchaseGames) {
        this.purchaseGames = purchaseGames;
    }
}
