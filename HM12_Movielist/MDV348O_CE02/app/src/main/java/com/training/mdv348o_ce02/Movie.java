package com.training.mdv348o_ce02;



public class Movie {
    private String title;
    private String description;
    private int backgroundImageResourceId;
    private int cardImageResourceId;

    public Movie(String title, String description, int backgroundImageResourceId, int cardImageResourceId) {
        this.title = title;
        this.description = description;
        this.backgroundImageResourceId = backgroundImageResourceId;
        this.cardImageResourceId = cardImageResourceId;
    }

    // Getters and Setters

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

    public int getBackgroundImageResourceId() {
        return backgroundImageResourceId;
    }

    public void setBackgroundImageResourceId(int backgroundImageResourceId) {
        this.backgroundImageResourceId = backgroundImageResourceId;
    }

    public int getCardImageResourceId() {
        return cardImageResourceId;
    }

    public void setCardImageResourceId(int cardImageResourceId) {
        this.cardImageResourceId = cardImageResourceId;
    }
}
