package com.example.gamehub.controllers;

import com.example.gamehub.dtos.GamesDTO;
import com.example.gamehub.records.GameRecord;
import com.example.gamehub.repositories.GamesRepository;
import com.example.gamehub.services.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GamesController {

    @Autowired
    private GamesService gamesService;

    @PostMapping
    public ResponseEntity<String> createGame(@RequestBody GameRecord gameRecord) {
        return gamesService.createGame(gameRecord);
    }

    @GetMapping
    public List<GamesDTO> getAllGames() {
        return gamesService.getAllGamesDTO();
    }

    @GetMapping("/{id}")
    public GamesDTO getGameById(@PathVariable Long id) {
        return gamesService.getGameDTOById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteGameById(@PathVariable Long id){
        return gamesService.deleteById(id);
    }
}
