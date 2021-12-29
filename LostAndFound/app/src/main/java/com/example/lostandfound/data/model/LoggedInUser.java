package com.example.lostandfound.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String id;
    private String username;
    private String password;

    public LoggedInUser() {
    }

    public LoggedInUser(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public LoggedInUser(String userId, String displayName) {
        this.id = userId;
        this.username = displayName;
    }

    public String getUserId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}