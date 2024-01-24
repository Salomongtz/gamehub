package com.example.gamehub.dtos;

import com.example.gamehub.models.Games;
import com.example.gamehub.models.Purchase;
import com.example.gamehub.models.Purchase_Game;
import jakarta.persistence.ManyToOne;

public class Purchase_GameDTO {

    private Long id;
    private Long productId;
    private Long gameId;
    private int quantity;
    @ManyToOne
    private Purchase purchase;
    @ManyToOne
    private Games games;

    public Purchase_GameDTO(Purchase_Game purchaseGame) {
        id = purchaseGame.getGameId();
        productId = purchaseGame.getPurchaseId();
        gameId = purchaseGame.getGameId();
        quantity = purchaseGame.getQuantity();
        purchase = purchaseGame.getPurchase();
        games = purchaseGame.getGame();
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getGameId() {
        return gameId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public Games getGame() {
        return games;
    }
}
