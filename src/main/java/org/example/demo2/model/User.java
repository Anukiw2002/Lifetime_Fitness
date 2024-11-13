package org.example.demo2.model;

import java.sql.Timestamp;

public class User {
    private String fullName;
    private String username;
    private String email;
    private String hashedPassword;
    private String resetToken;
    private Timestamp tokenExpiry;

    // Constructor with fullName, username, email, and hashedPassword
    public User(String fullName, String username, String email, String hashedPassword) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }
    public User(String fullName, String username, String email, String hashedPassword, Timestamp tokenExpiry) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.tokenExpiry = tokenExpiry;
    }

    // Constructor with fullName, username, email, hashedPassword, resetToken, and tokenExpiry
    public User(String fullName, String username, String email, String hashedPassword, String resetToken, Timestamp tokenExpiry) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.resetToken = resetToken;
        this.tokenExpiry = tokenExpiry;
    }

    // Getters and setters for all fields
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

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

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Timestamp getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(Timestamp tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }
}
