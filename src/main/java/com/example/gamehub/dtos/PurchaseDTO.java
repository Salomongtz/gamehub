package com.example.gamehub.dtos;

import com.example.gamehub.models.Purchase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDTO {
    private final LocalDate purchaseDate;
    private final Double totalAmount;
    private final List<Purchase_GameDTO> games;

    public PurchaseDTO(Purchase purchase) {
        this.purchaseDate = purchase.getPurchaseDate();
        this.totalAmount = purchase.getTotalAmount();
        this.games = purchase.getPurchaseGames().stream().map(Purchase_GameDTO::new).toList();
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public List<Purchase_GameDTO> getGames() {
        return games;
    }
}
