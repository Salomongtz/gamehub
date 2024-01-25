package com.example.gamehub.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Games {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title, description, developer, publisher, imageURL;
    private Long sales,stock;
    private double price;
    private LocalDate releaseDate;
    private float discount;
    private Rating rating;
    @ElementCollection
    private List<GameGenre> genres;
    @ElementCollection
    private List<GamePlatform> platforms;
    @OneToMany(mappedBy = "games")
    private List<Customer_Game> customerGames = new ArrayList<>();
    @OneToMany(mappedBy = "games")
    private List<Purchase_Game> purchaseGames = new ArrayList<>();

    public Games() {
    }

    public Games(String title, String description, String developer, String publisher, String imageURL, Long sales,
                 double price, LocalDate releaseDate, float discount, Rating rating, List<GameGenre> genres,
                 List<GamePlatform> platforms) {
        this.title = title;
        this.description = description;
        this.developer = developer;
        this.publisher = publisher;
        this.imageURL = imageURL;
        this.sales = sales;
        this.price = price;
        this.releaseDate = releaseDate;
        this.discount = discount;
        this.rating = rating;
        this.genres = genres;
        this.platforms = platforms;
    }

    public void addCustomerGame(Customer_Game customerGame) {
        customerGame.setGames(this);
        customerGames.add(customerGame);
    }

    public void addPurchaseGame(Purchase_Game purchaseGame) {
        purchaseGame.setGame(this);
        purchaseGames.add(purchaseGame);
    }

    public Long getId() {
        return id;
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

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public List<GameGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<GameGenre> genres) {
        this.genres = genres;
    }

    public List<GamePlatform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<GamePlatform> platforms) {
        this.platforms = platforms;
    }

    public List<Customer_Game> getCustomerGames() {
        return customerGames;
    }

    public void setCustomerGames(List<Customer_Game> customerGames) {
        this.customerGames = customerGames;
    }

    public List<Purchase_Game> getPurchaseGames() {
        return purchaseGames;
    }

    public void setPurchaseGames(List<Purchase_Game> purchaseGames) {
        this.purchaseGames = purchaseGames;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Games{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", sales=" + sales +
                ", price=" + price +
                ", date=" + releaseDate +
                ", discount=" + discount +
                ", genre='" + genres + '\'' +
                ", platform='" + platforms + '\'' +
                ", customerGames=" + customerGames +
                '}';
    }
}
