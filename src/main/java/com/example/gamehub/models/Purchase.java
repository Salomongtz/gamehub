package com.example.gamehub.models;

import jakarta.persistence.*;

import java.time.LocalDate;
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
    private List<Purchase_Game> purchaseGames;

    public Purchase() {
    }

    public Purchase(LocalDate purchaseDate, Double totalAmount, Customer customer, List<Purchase_Game> purchaseGames) {
        this.purchaseDate = purchaseDate;
        this.totalAmount = totalAmount;
        this.customer = customer;
        this.purchaseGames = purchaseGames;
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
