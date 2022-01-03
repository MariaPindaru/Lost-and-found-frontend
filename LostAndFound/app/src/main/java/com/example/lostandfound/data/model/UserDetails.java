package com.example.lostandfound.data.model;

public class UserDetails {
    private String id;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String email_address;

    public UserDetails(String id, String first_name, String last_name, String phone_number, String email_address) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.email_address = email_address;
    }

    public UserDetails(String id,String first_name, String last_name, String phone_number) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }
}
