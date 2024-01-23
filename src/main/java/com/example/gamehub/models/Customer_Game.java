package com.example.gamehub.models;

import jakarta.persistence.*;

@Entity
public class Customer_Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Game game;

    public Customer_Game() {
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Game getGames() {
        return game;
    }

    public void setGames(Game game) {
        this.game = game;
    }
}
