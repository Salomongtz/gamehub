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
    @Column(columnDefinition = "TEXT")
    private String longDescription;
    private Long sales,stock, owned=0L;
    private double price;
    private LocalDate releaseDate;
    private float discount;
    private Rating rating;
    private List<String> screenshots = new ArrayList<>();
    @ElementCollection
    private List<GameGenre> genres;
    @ElementCollection
    private List<GamePlatform> platforms;
    @OneToMany(mappedBy = "games")
    private List<Purchase_Game> purchaseGames = new ArrayList<>();

    public Games() {
    }

    public Games(String title, String description, String developer, String publisher, String imageURL, String longDescription, Long sales,
                 double price, Long stock, LocalDate releaseDate, float discount, Rating rating, List<GameGenre> genres,
                 List<GamePlatform> platforms, List<String> screenshots ) {
        this.title = title;
        this.description = description;
        this.longDescription = longDescription == null ? "" : longDescription;
        this.developer = developer;
        this.publisher = publisher;
        this.imageURL = imageURL;
        this.screenshots = screenshots == null ? new ArrayList<>() : screenshots;
        this.sales = sales == null ? 0L : sales;
        this.price = price;
        this.stock = stock;
        this.releaseDate = releaseDate;
        this.discount = discount;
        this.rating = rating == null ? Rating.RP : rating;
        this.genres = genres;
        this.platforms = platforms;
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

    public Long getOwned() {
        return owned;
    }

    public void setOwned(Long owned) {
        this.owned = owned;
    }
    //    public String getCoverURL() {
//        return coverURL;
//    }
//
//    public void setCoverURL(String coverURL) {
//        this.coverURL = coverURL;
//    }
//
    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public List<String> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<String> screenshots) {
        this.screenshots = screenshots;
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
                '}';
    }


}
