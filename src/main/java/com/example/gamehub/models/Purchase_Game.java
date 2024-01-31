package com.example.gamehub.models;

import jakarta.persistence.*;

@Entity
public class Purchase_Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @ManyToOne
    private Purchase purchase;
    @ManyToOne
    private Games games;

    public Purchase_Game() {
    }

    public Purchase_Game(int quantity) {
        this.quantity = quantity;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Games getGame() {
        return games;
    }

    public void setGame(Games games) {
        this.games = games;
    }

    @Override
    public String toString() {
        return "Purchase_Game{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", purchase=" + purchase +
                ", game=" + games +
                '}';
    }
}
