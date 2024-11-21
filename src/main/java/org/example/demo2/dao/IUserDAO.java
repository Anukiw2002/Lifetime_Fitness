package org.example.demo2.dao;

import org.example.demo2.model.User;
import java.sql.SQLException;
import java.sql.Timestamp;

public interface IUserDAO {

    // Register a new user with additional fields (updated to include full name)
    void registerUser(User user) throws SQLException;

    // Check if an email already exists
    boolean emailExists(String email) throws SQLException;

    // Check if a username already exists
    boolean usernameExists(String username) throws SQLException;

    // Authenticate user based on username and password
    User authenticateUser(String username, String plainPassword) throws SQLException;

    // Set a reset token and expiry for password reset functionality
    void setResetToken(String email, String token, Timestamp expiry) throws SQLException;

    // Retrieve a user by reset token
    User getUserByResetToken(String token) throws SQLException;

    // Retrieve a user by email (updated to include full name in the returned User object)
    User getUserByEmail(String email) throws SQLException;

    // Update a user's password
    void updatePassword(String email, String newPassword) throws SQLException;

    // Verify if the plain password matches the hashed password in the database
    boolean verifyPassword(String plainPassword, String hashedPassword);

    // Validate if the provided reset token is still valid (hasn't expired)
    boolean validatePasswordResetToken(String token) throws SQLException;

}
