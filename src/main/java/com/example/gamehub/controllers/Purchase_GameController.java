package com.example.gamehub.controllers;

import com.example.gamehub.repositories.Purchase_GameRepository;
import com.example.gamehub.services.Purchase_GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchaseGame")
public class Purchase_GameController {

    @Autowired
    private Purchase_GameService purchaseGameService;
}
