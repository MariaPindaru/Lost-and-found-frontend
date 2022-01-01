package com.example.lostandfound.data.model;

import android.media.Image;

import java.io.Serializable;
import java.util.Date;

public class Post  implements Serializable {

    private String id;
    private String user_id;
    private String title;
    private String type;
    private String description;
    private String location;
    private Date date;
    private String picture;// = new String("https://www.freeiconspng.com/thumbs/no-image-icon/no-image-icon-6.png");

    public Post(String id, String user_id, String title, String type, String description, String location, Date date, String image) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.type = type;
        this.description = description;
        this.location = location;
        this.date = date;
        this.picture = image;
    }


    // Constructor
    public Post(String title, String description, String course_image) {
        this.title = title;
        this.description = description;
        this.picture = course_image;
    }

    public Post(String title, String description, String location, Date date, String image) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.date = date;
        this.picture = image;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}