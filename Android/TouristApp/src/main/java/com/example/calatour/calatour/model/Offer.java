package com.example.calatour.calatour.model;

/**
 * Created by Rici on 21-Nov-18.
 */

public class Offer {
    String title;
    String description;
    Integer price;
    Integer image;
    Boolean favorite;
    Integer timesDisplay;

    public Offer(String title, String description, Integer price, Integer image) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public Offer(String title, String description, Integer price, Integer image, Boolean favorite, Integer timesDisplay) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.favorite = favorite;
        this.timesDisplay = timesDisplay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Integer getTimesDisplay() {
        return timesDisplay;
    }

    public void setTimesDisplay(Integer timesDisplay) {
        this.timesDisplay = timesDisplay;
    }
}
