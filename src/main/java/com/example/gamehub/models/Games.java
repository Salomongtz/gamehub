package com.example.gamehub.models;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
public class Games {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Games() {
    }

    public Games(Long id, String title, String description, Long sales, double price, LocalDate date, float discount, String genre, String platform) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.sales = sales;
        this.price = price;
        this.date = date;
        this.discount = discount;
        this.genre = genre;
        this.platform = platform;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSales() {
        return sales;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "Games{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", sales=" + sales +
                ", price=" + price +
                ", date=" + date +
                ", discount=" + discount +
                ", genre='" + genre + '\'' +
                ", platform='" + platform + '\'' +
                '}';
    }
}
