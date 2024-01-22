package com.example.gamehub.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate purchaseDate;
    private Double totalAmount;
    @ManyToOne
    private Customer customer;
}
