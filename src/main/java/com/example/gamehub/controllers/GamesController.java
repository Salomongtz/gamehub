package com.example.gamehub.controllers;

import com.example.gamehub.repositories.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games")
public class GamesController {

    @Autowired
    private GamesRepository gamesRepository;
}
