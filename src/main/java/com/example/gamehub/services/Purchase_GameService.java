package com.example.gamehub.services;

import com.example.gamehub.dtos.Purchase_GameDTO;
import com.example.gamehub.models.Purchase_Game;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface Purchase_GameService {

    List<Purchase_Game> getAllPurchaseGame();
    List<Purchase_GameDTO> getAllPurchaseGameDTO();


}
