package com.example.gamehub.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Purchase_Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long gameId;
    private int quantity;

    public Purchase_Game() {
    }

    public Purchase_Game(Long id, Long productId, Long gameId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.gameId = gameId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Purchase_Game{" +
                "id=" + id +
                ", productId=" + productId +
                ", gameId=" + gameId +
                ", quantity=" + quantity +
                '}';
    }
}
