package com.example.jwtapp.model;

public class PostResponse {
    private String message;
    private String name;
    private String description;
    private String email;
    private String imageBase64;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public String getMessage() {
        return message;
    }
}
