package com.example.gamehub.dtos;

import com.example.gamehub.models.Customer_Game;
import com.example.gamehub.models.Games;
import com.example.gamehub.models.GameGenre;
import com.example.gamehub.models.GamePlatform;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GamesDTO {

    private Long id;
    private String title;
    private String image;
    private Long stock;
    private String description;
    private Long sales;
    private double price;
    private LocalDate date;
    private float discount;

    private List<GameGenre> genre;

    private List<GamePlatform> platforms;

    private List<Customer_Game> customerGames = new ArrayList<>();

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
        customerGames = games.getCustomerGames();
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

    public List<Customer_Game> getCustomerGames() {
        return customerGames;
    }
}
