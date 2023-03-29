package com.example.googlebookslisting;

import android.graphics.Bitmap;


public class Model {
    private String title;
    private String authors;
    private Bitmap thumbnail;
    private String thumbnailUrl;

    public Model(String title, String authors, Bitmap thumbnail) {
        this.title = title;
        this.authors = authors;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
