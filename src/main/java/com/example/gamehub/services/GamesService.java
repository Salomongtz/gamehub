package com.example.gamehub.services;

import com.example.gamehub.dtos.GamesDTO;
import com.example.gamehub.models.Game;

import java.util.List;

public interface GamesService {

    List<Game> getAllGames();
    List<GamesDTO> getAllGamesDTO();
}
