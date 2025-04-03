package com.example.jwtapp.model;

import java.io.File;

public class Post {
    private String description;
    private String imageBase64;
    private String email;

    public Post(String description, String content, String user) {
        this.description = description;
        this.imageBase64 = content;
        this.email = user;
    }

    public String getTitle() { return description; }
    public String getImageBase64() { return imageBase64; }
    public String getEmail() { return email; }
}
