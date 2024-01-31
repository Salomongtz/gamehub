package com.example.gamehub.records;

import com.example.gamehub.models.*;

import java.time.LocalDate;
import java.util.List;

public record GameRecord(
        String title,
        String image,
        Long stock,
        String description,
        String developer,
        String publisher,

        String imageURL,
//        String coverURL,
        String longDescription,
        Long sales,
        double price,
        LocalDate releaseDate,
        float discount,
        Rating rating,
        List<String> screenshots,
        List<GameGenre> genres,
        List<GamePlatform> platforms
        ) {
}
