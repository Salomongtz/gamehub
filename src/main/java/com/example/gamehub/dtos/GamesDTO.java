package com.example.gamehub.dtos;

import com.example.gamehub.models.GameGenre;
import com.example.gamehub.models.GamePlatform;
import com.example.gamehub.models.Games;

import java.time.LocalDate;
import java.util.List;

public class GamesDTO {

    private final Long id;
    private final String title, description, image;
    private final Long stock, sales, owned;
    private final double price;
    private final LocalDate date;
    private final float discount;
    private final List<GameGenre> genre;
    private final List<GamePlatform> platforms;


    public GamesDTO(Games games) {
        id = games.getId();
        title = games.getTitle();
        image = games.getImageURL();
        stock = games.getStock();
        description = games.getDescription();
        sales = games.getSales();
        price = games.getPrice();
        date = games.getReleaseDate();
        discount = games.getDiscount();
        genre = games.getGenres();
        platforms = games.getPlatforms();
        this.owned = games.getOwned() == null ? 0L : games.getOwned();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public Long getStock() {
        return stock;
    }

    public String getDescription() {
        return description;
    }

    public Long getSales() {
        return sales;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }

    public float getDiscount() {
        return discount;
    }

    public List<GameGenre> getGenre() {
        return genre;
    }

    public List<GamePlatform> getPlatforms() {
        return platforms;
    }

    public Long getOwned() {
        return owned;
    }
}
