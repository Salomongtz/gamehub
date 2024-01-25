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

        if(gameRecord.title().isBlank()){
            return new ResponseEntity<>("este campo no puede estar vacio", HttpStatus.FORBIDDEN);
        }
        if(gameRecord.description().isBlank()){
            return new ResponseEntity<>("este campo no puede estar vacio", HttpStatus.FORBIDDEN);
        }

        if(gameRecord.genres().isEmpty()){
            return new ResponseEntity<>("este campo no puede estar vacio", HttpStatus.FORBIDDEN);
        }
        if(gameRecord.price() == 0){
            return new ResponseEntity<>("No puede estar vacio ni ser cero", HttpStatus.FORBIDDEN);
        }

        gamesRepository.save(game);
        return new ResponseEntity<>("Created successfully!", HttpStatus.CREATED);
    }

    @Override
    public Games findById(Long id) {
        return gamesRepository.findById(id).orElse(null);
    }

    @Override
    public Games findByTitle(String title) {
        return gamesRepository.findByTitle(title);
    }

    @Override
    public Games getGameById(Long id) {
        return gamesRepository.findById(id).orElse(null);
    }

    @Override
    public GamesDTO getGameDTOById(Long id) {
        return new GamesDTO(getGameById(id));
    }
}
