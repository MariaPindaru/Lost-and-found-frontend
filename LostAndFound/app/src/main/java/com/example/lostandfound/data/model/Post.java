package com.example.lostandfound.data.model;

import android.media.Image;

import java.util.Date;

public class Post {

    private String title;
    private String description;
    private String location;
    private Date date;
    private int image;

    // Constructor
    public Post(String title, String description, int course_image) {
        this.title = title;
        this.description = description;
        this.image = course_image;
    }

    public Post(String title, String description, String location, Date date, int image) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.image = image;
    }

    // Getter and Setter
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}