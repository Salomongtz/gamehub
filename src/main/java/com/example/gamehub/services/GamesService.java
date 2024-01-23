package com.example.gamehub.services;

import com.example.gamehub.dtos.GamesDTO;
import com.example.gamehub.models.Games;

import java.util.List;

public interface GamesService {

    List<Games> getAllGames();
    List<GamesDTO> getAllGamesDTO();
}
