package com.example.gamehub.services;

import com.example.gamehub.dtos.GamesDTO;
import com.example.gamehub.models.Games;
import com.example.gamehub.records.GameRecord;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GamesService {

    List<Games> getAllGames();
    List<GamesDTO> getAllGamesDTO();
    ResponseEntity<String> createGame(GameRecord gameRecord);
    Games findById(Long id);
    Games findByTitle(String title);
    GamesDTO findGameDTOById(Long id);
    ResponseEntity<String> updateGame(Long id, GameRecord gameRecord);
    ResponseEntity<String> deleteById(Long id);

}
