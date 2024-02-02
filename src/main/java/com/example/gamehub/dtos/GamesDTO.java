package com.example.gamehub.dtos;

import com.example.gamehub.models.GameGenre;
import com.example.gamehub.models.GamePlatform;
import com.example.gamehub.models.Games;
import com.example.gamehub.models.Rating;

import java.time.LocalDate;
import java.util.List;

public class GamesDTO {

    private final Long id;
    private final String title, description, image, developer, publisher, longDescription;
    private final Long stock, sales, owned;
    private final double price;
    private final LocalDate date;
    private final float discount;
    private final List<String> screenshots;
    private final List<GameGenre> genre;
    private final List<GamePlatform> platforms;
    private final Rating rating;
    private boolean stateGame;


    public GamesDTO(Games games) {
        id = games.getId();
        title = games.getTitle();
        image = games.getImageURL();
        stock = games.getStock();
        description = games.getDescription();
        longDescription = games.getLongDescription();
        sales = games.getSales();
        price = games.getPrice();
        date = games.getReleaseDate();
        discount = games.getDiscount();
        genre = games.getGenres();
        platforms = games.getPlatforms();
        developer = games.getDeveloper();
        publisher = games.getPublisher();
        screenshots = games.getScreenshots();
        this.owned = games.getOwned() == null ? 0L : games.getOwned();
        this.rating = games.getRating() == null ? Rating.RP : games.getRating();
        stateGame = games.isStateGame();
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

    public Rating getRating() {
        return rating;
    }

    public boolean isStateGame() {
        return stateGame;
    }

    public String getDeveloper() {
        return developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public List<String> getScreenshots() {
        return screenshots;
    }
}
