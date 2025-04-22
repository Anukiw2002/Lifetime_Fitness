package org.example.demo2.model;

import java.sql.Timestamp;

public class User {
    private int user_id;
    private String fullName;
    private String username;
    private String email;
    private String hashedPassword;
    private String resetToken;
    private Timestamp tokenExpiry;
    private String role;

    // Constructor with fullName, username, email, hashedPassword, and role
    public User( String fullName, String username, String email, String hashedPassword, String role) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }

    // Constructor with fullName, username, email, hashedPassword, tokenExpiry, and role
    public User(int user_id,String fullName, String username, String email, String hashedPassword, Timestamp tokenExpiry, String role) {
        this.user_id = user_id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.tokenExpiry = tokenExpiry;
        this.role = role;
    }

    // Constructor with all fields: fullName, username, email, hashedPassword, resetToken, tokenExpiry, and role
    public User(int user_id, String fullName, String username, String email, String hashedPassword, String resetToken, Timestamp tokenExpiry, String role) {
        this.user_id = user_id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.resetToken = (resetToken != null) ? resetToken : ""; // Handle null resetToken
        this.tokenExpiry = tokenExpiry; // Allow tokenExpiry to be null
        this.role = role;
    }

    // Constructor that includes user_id along with other fields
    public User(int user_id, String fullName, String username, String email, String hashedPassword, String role) {
        this.user_id = user_id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }


    public int getUser_id() {return user_id;}

    public void setUser_id(int user_id) {this.user_id = user_id;}
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // ToString method for debugging
    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", resetToken='" + resetToken + '\'' +
                ", tokenExpiry=" + tokenExpiry +
                ", role='" + role + '\'' +
                '}';
    }
}
