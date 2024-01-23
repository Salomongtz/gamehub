package com.example.gamehub.services.implement;

import com.example.gamehub.dtos.GamesDTO;
import com.example.gamehub.models.Game;
import com.example.gamehub.repositories.GamesRepository;
import com.example.gamehub.services.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GamesServiceImplement implements GamesService {

    @Autowired
    GamesRepository gamesRepository;

    @Override
    public List<Game> getAllGames() {
        return gamesRepository.findAll();
    }

    @Override
    public List<GamesDTO> getAllGamesDTO() {
        return getAllGames().stream().map(GamesDTO::new).collect(Collectors.toList());
    }
}
