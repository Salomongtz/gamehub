package com.example.gamehub.models;

import jakarta.persistence.*;

@Entity
public class Customer_Game {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@ManyToOne
private Customer customer;
}
