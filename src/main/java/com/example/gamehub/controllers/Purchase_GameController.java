package com.example.gamehub.controllers;

import com.example.gamehub.repositories.Purchase_GameRepository;
import com.example.gamehub.services.Purchase_GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/purchaseGame")
public class Purchase_GameController {

    @Autowired
    private Purchase_GameService purchaseGameService;


}
