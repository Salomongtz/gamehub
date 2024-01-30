package com.example.gamehub.dtos;

import com.example.gamehub.models.Customer;
import com.example.gamehub.models.Customer_Game;
import com.example.gamehub.models.Purchase;

import java.util.List;

public class CustomerDTO {

    private Long id;
    private String name, lastName, email;

    private List<PurchaseDTO> purchases;

    private List<GamesDTO> games;

    public CustomerDTO() {
    }

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.purchases = customer.getPurchases().stream().map(PurchaseDTO::new).toList();
        this.games = customer.getGames().stream().map(GamesDTO::new).toList();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<PurchaseDTO> getPurchases() {
        return purchases;
    }

    public List<GamesDTO> getGames() {
        return games;
    }
}
