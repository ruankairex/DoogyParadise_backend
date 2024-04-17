package com.ispan.dogland.model.dto;

import java.io.Serializable;

public class Passport implements Serializable {
    private String username;
    private String email;
    private Integer userId;
    private String role;
    private String photoUrl;

    // Constructors
    public Passport() {
    }

    public Passport(String username, String email, Integer userId, String role) {
        this.username = username;
        this.email = email;
        this.userId = userId;
        this.role = role;
    }

    public Passport(String username, String email, Integer userId, String role, String photoUrl) {
        this.username = username;
        this.email = email;
        this.userId = userId;
        this.role = role;
        this.photoUrl = photoUrl;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Passport{");
        sb.append("username='").append(username).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", userId=").append(userId);
        sb.append(", role='").append(role).append('\'');
        sb.append(", PhotoUrl='").append(photoUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}