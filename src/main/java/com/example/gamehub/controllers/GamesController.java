package com.example.gamehub.controllers;

import com.example.gamehub.dtos.GamesDTO;
import com.example.gamehub.records.GameRecord;
import com.example.gamehub.services.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    public List<GamesDTO> getActiveGamesDTO() {
        return gamesService.getActiveGamesDTO();
    }
    @GetMapping("/all")
    public List<GamesDTO> getAllGames() {
        return gamesService.getAllGamesDTO();
    }

    @GetMapping("/{id}")
    public GamesDTO getGameById(@PathVariable Long id) {
        return gamesService.findGameDTOById(id);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<String> updateGame(@PathVariable Long id, @RequestBody GameRecord gameRecord) {
        return gamesService.updateGame(id, gameRecord);
    }

    @PatchMapping("/state/{id}/{state}")
    public ResponseEntity<String>deleteGameById(@PathVariable Long id, @PathVariable boolean state) {
        return gamesService.changeGameState(id, state);
    }
}
