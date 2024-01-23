package com.example.gamehub.services.implement;

import com.example.gamehub.dtos.GamesDTO;
import com.example.gamehub.models.GameGenre;
import com.example.gamehub.models.Games;
import com.example.gamehub.records.GameRecord;
import com.example.gamehub.repositories.GamesRepository;
import com.example.gamehub.services.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GamesServiceImplement implements GamesService {

    @Autowired
    GamesRepository gamesRepository;

    @Override
    public List<Games> getAllGames() {
        return gamesRepository.findAll();
    }

    @Override
    public List<GamesDTO> getAllGamesDTO() {
        return getAllGames().stream().map(GamesDTO::new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> createGame(GameRecord gameRecord) {
        Games game = new Games(gameRecord.title(), gameRecord.description(), gameRecord.developer(),
                gameRecord.description(), gameRecord.imageURL(), gameRecord.sales(), gameRecord.price(),
                gameRecord.releaseDate(), gameRecord.discount(), gameRecord.rating(), gameRecord.genres(),
                gameRecord.platforms());
        gamesRepository.save(game);
        return new ResponseEntity<>("Created successfully!", HttpStatus.CREATED);
    }
}
