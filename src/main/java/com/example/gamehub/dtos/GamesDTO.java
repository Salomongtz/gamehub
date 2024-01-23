package com.example.gamehub.dtos;

import com.example.gamehub.models.Customer_Game;
import com.example.gamehub.models.Games;
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
    private String genre;
    @ElementCollection
    private String platform;
    @OneToMany
    private List<Customer_Game> customerGames = new ArrayList<>();

    public GamesDTO(Games games) {
        id = games.getId();
        title = games.getTitle();
        description = games.getDescription();
        sales = games.getSales();
        price = games.getPrice();
        date = games.getDate();
        discount = games.getDiscount();
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
}
