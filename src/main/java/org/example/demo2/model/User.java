package org.example.demo2.model;

import java.sql.Timestamp;

public class User {
    private String username;
    private String email;
    private String hashedPassword;
    private String resetToken;
    private Timestamp tokenExpiry;

    public User(String username, String email, String hashedPassword) {
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    public User(String username, String email, String hashedPassword, String resetToken, Timestamp tokenExpiry) {
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.resetToken = resetToken;
        this.tokenExpiry = tokenExpiry;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getResetToken() {
        return resetToken;
    }

    public Timestamp getTokenExpiry() {
        return tokenExpiry;
    }
}
