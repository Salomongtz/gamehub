package com.example.gamehub.services.implement;

import com.example.gamehub.dtos.Purchase_GameDTO;
import com.example.gamehub.models.Purchase_Game;
import com.example.gamehub.repositories.Purchase_GameRepository;
import com.example.gamehub.services.Purchase_GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Purchase_GameServiceImplement implements Purchase_GameService {

    @Autowired
    Purchase_GameRepository purchaseGameRepository;

    @Override
    public List<Purchase_Game> getAllPurchaseGame() {
        return purchaseGameRepository.findAll();
    }

    @Override
    public List<Purchase_GameDTO> getAllPurchaseGameDTO() {
        return getAllPurchaseGame().stream().map(Purchase_GameDTO::new).collect(Collectors.toList());
    }
}
