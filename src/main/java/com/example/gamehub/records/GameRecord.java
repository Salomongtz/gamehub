package com.example.gamehub.records;

import com.example.gamehub.models.*;

import java.time.LocalDate;
import java.util.List;

public record GameRecord(
        String title,
        String description,
        String developer,
        String publisher,
        String imageURL,
        Long sales,
        double price,
        LocalDate releaseDate,
        float discount,
        Rating rating,
        List<GameGenre> genres,
        List<GamePlatform> platforms
        ) {
}
