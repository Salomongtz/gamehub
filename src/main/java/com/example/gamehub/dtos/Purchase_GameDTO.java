package com.example.gamehub.dtos;

import com.example.gamehub.models.Games;
import com.example.gamehub.models.Purchase;
import com.example.gamehub.models.Purchase_Game;
import jakarta.persistence.ManyToOne;

public class Purchase_GameDTO {

    private final Long id;
    private final int quantity;
//    private final PurchaseDTO purchase;
    private final GamesDTO games;

    public Purchase_GameDTO(Purchase_Game purchaseGame) {
        id = purchaseGame.getId();
        quantity = purchaseGame.getQuantity();
//        purchase = new PurchaseDTO(purchaseGame.getPurchase());
        games = new GamesDTO(purchaseGame.getGame());
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

//    public PurchaseDTO getPurchase() {
//        return purchase;
//    }

    public GamesDTO getGame() {
        return games;
    }
}
