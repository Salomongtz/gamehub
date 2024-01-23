package com.example.gamehub.dtos;

import com.example.gamehub.models.Customer_Game;
import com.example.gamehub.models.Game;
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
    private String description;
    private Long sales;
    private double price;
    private LocalDate date;
    private float discount;
    @ElementCollection
    private List<GameGenre> genre;
    @ElementCollection
    private List<GamePlatform> platforms;
    @OneToMany
    private List<Customer_Game> customerGames = new ArrayList<>();

    public GamesDTO(Game game) {
        id = game.getId();
        title = game.getTitle();
        description = game.getDescription();
        sales = game.getSales();
        price = game.getPrice();
        date = game.getDate();
        discount = game.getDiscount();
        genre = game.getGenre();
        platforms = game.getPlatforms();
        customerGames = game.getCustomerGames();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
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
