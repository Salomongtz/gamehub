package com.example.gamehub.dtos;

import com.example.gamehub.models.Customer;
import com.example.gamehub.models.Customer_Game;
import com.example.gamehub.models.Purchase;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;

public class CustomerDTO {

    private Long id;
    private String name, lastName, email;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Purchase> purchases;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Customer_Game> customer_games;

    public CustomerDTO() {
    }

    public CustomerDTO(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.purchases = customer.getPurchases();
        this.customer_games = customer.getCustomer_games();
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

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public List<Customer_Game> getCustomer_games() {
        return customer_games;
    }
}
