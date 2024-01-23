package com.example.gamehub.controllers;

import com.example.gamehub.records.GameRecord;
import com.example.gamehub.repositories.GamesRepository;
import com.example.gamehub.services.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games")
public class GamesController {

    @Autowired
    private GamesService gamesService;

    @PostMapping
    public ResponseEntity<String> createGame(@RequestBody GameRecord gameRecord) {
        return gamesService.createGame(gameRecord);
    }
}
