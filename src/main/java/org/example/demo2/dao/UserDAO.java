package org.example.demo2.dao;

import org.example.demo2.model.User;
import org.example.demo2.util.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserDAO implements IUserDAO {

    @Override
    public void registerUser(User user) throws SQLException {
        String sql = "INSERT INTO users (full_name, username, email, hashed_password) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.err.println("registerUser: Unable to establish a database connection.");
                throw new SQLException("Database connection is null.");
            }

            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getHashedPassword());

            int rowsAffected = stmt.executeUpdate();
            System.out.println("registerUser: User registered successfully. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println("registerUser: Error inserting user with username '" + user.getUsername() + "'");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean validatePasswordResetToken(String token) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE reset_token = ? AND token_expiry > NOW()";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.err.println("validatePasswordResetToken: Unable to establish a database connection.");
                throw new SQLException("Database connection is null.");
            }

            stmt.setString(1, token);
            try (ResultSet rs = stmt.executeQuery()) {
                boolean exists = rs.next();
                System.out.println("validatePasswordResetToken: Token " + (exists ? "exists" : "does not exist"));
                return exists;
            }

        } catch (SQLException e) {
            System.err.println("validatePasswordResetToken: Error validating token '" + token + "'");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.err.println("emailExists: Unable to establish a database connection.");
                throw new SQLException("Database connection is null.");
            }

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                boolean exists = rs.next();
                System.out.println("emailExists: Email " + (exists ? "already exists" : "does not exist"));
                return exists;
            }

        } catch (SQLException e) {
            System.err.println("emailExists: Error checking email existence for '" + email + "'");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.err.println("usernameExists: Unable to establish a database connection.");
                throw new SQLException("Database connection is null.");
            }

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                boolean exists = rs.next();
                System.out.println("usernameExists: Username " + (exists ? "already exists" : "does not exist"));
                return exists;
            }

        } catch (SQLException e) {
            System.err.println("usernameExists: Error checking username existence for '" + username + "'");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public User authenticateUser(String username, String plainPassword) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.err.println("authenticateUser: Unable to establish a database connection.");
                throw new SQLException("Database connection is null.");
            }

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("hashed_password");
                    if (verifyPassword(plainPassword, hashedPassword)) {
                        System.out.println("authenticateUser: Authentication successful for username '" + username + "'");
                        return new User(
                                rs.getString("full_name"),
                                rs.getString("username"),
                                rs.getString("email"),
                                hashedPassword,
                                rs.getString("reset_token"),
                                rs.getTimestamp("token_expiry")
                        );
                    } else {
                        System.out.println("authenticateUser: Authentication failed for username '" + username + "'");
                    }
                }
                return null;
            }

        } catch (SQLException e) {
            System.err.println("authenticateUser: Error authenticating user with username '" + username + "'");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void setResetToken(String email, String token, Timestamp expiry) throws SQLException {
        String sql = "UPDATE users SET reset_token = ?, token_expiry = ? WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.err.println("setResetToken: Unable to establish a database connection.");
                throw new SQLException("Database connection is null.");
            }

            stmt.setString(1, token);
            stmt.setTimestamp(2, expiry);
            stmt.setString(3, email);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("setResetToken: Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println("setResetToken: Error setting reset token for email '" + email + "'");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void updatePassword(String email, String newPassword) throws SQLException {
        String sql = "UPDATE users SET hashed_password = ?, reset_token = NULL, token_expiry = NULL WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.err.println("updatePassword: Unable to establish a database connection.");
                throw new SQLException("Database connection is null.");
            }

            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            stmt.setString(1, hashedPassword);
            stmt.setString(2, email);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("updatePassword: Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println("updatePassword: Error updating password for email '" + email + "'");
            e.printStackTrace();
            throw e;
        }
    }
    @Override
    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.err.println("getUserByEmail: Unable to establish a database connection.");
                throw new SQLException("Database connection is null.");
            }

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("getUserByEmail: User found with email '" + email + "'");
                    return new User(
                            rs.getString("full_name"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("hashed_password"),
                            rs.getString("reset_token"),
                            rs.getTimestamp("token_expiry")
                    );
                } else {
                    System.out.println("getUserByEmail: No user found with email '" + email + "'");
                }
            }

        } catch (SQLException e) {
            System.err.println("getUserByEmail: Error retrieving user by email '" + email + "'");
            e.printStackTrace();
            throw e;
        }

        return null;
    }

    @Override
    public User getUserByResetToken(String token) throws SQLException{
        String sql = "SELECT * FROM users WHERE reset_token = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (conn == null) {
                System.err.println("getUserByResetToken: Unable to establish a database connection.");
                throw new SQLException("Database connection is null.");
            }
            stmt.setString(1, token);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("full_name"),  // Fetch full name
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("hashed_password"),
                            rs.getString("reset_token"),
                            rs.getTimestamp("token_expiry")
                    );
                }
                return null;
            }
        }
    }


    @Override
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
