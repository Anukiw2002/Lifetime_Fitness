package org.example.demo2.dao;

import org.example.demo2.model.User;
import org.example.demo2.util.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

public class UserDAO implements IUserDAO {

    @Override
    public void registerUser(User user) throws SQLException {
        String sql = "INSERT INTO users (full_name, username, email, hashed_password, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getHashedPassword());
            stmt.setString(5, user.getRole());

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

            stmt.setString(1, token);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
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

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
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

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
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

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("hashed_password");

                    if (verifyPassword(plainPassword, hashedPassword)) {
                        // Fetch reset_token and token_expiry
                        String resetToken = rs.getString("reset_token");
                        Timestamp tokenExpiry = rs.getTimestamp("token_expiry");

                        // Debugging logs
                        System.out.println("reset_token: " + resetToken);
                        System.out.println("token_expiry: " + tokenExpiry);

                        // Return the User object
                        return new User(
                                rs.getInt("id"),
                                rs.getString("full_name"),
                                rs.getString("username"),
                                rs.getString("email"),
                                hashedPassword,
                                resetToken,
                                tokenExpiry,
                                rs.getString("role")
                        );
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
    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Fetch reset_token
                    String resetToken = rs.getString("reset_token");
                    System.out.println("reset_token: " + resetToken);

                    // Fetch token_expiry as String and convert to Timestamp
                    String tokenExpiryString = rs.getString("token_expiry");
                    Timestamp tokenExpiry = null;
                    if (tokenExpiryString != null) {
                        try {
                            tokenExpiry = Timestamp.valueOf(tokenExpiryString);
                        } catch (IllegalArgumentException e) {
                            System.err.println("Error converting token_expiry: " + tokenExpiryString);
                            e.printStackTrace();
                        }
                    }

                    // Return the User object
                    return new User(
                            rs.getInt("id"),
                            rs.getString("full_name"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("hashed_password"),
                            resetToken,
                            tokenExpiry,
                            rs.getString("role")
                    );
                }
                return null;
            }

        } catch (SQLException e) {
            System.err.println("getUserByEmail: Error retrieving user by email '" + email + "'");
            e.printStackTrace();
            throw e;
        }
    }

    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT id, full_name, username, email, hashed_password, reset_token, token_expiry, role FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Create and return a User object with all data
                    User user = new User(
                            rs.getInt("id"),
                            rs.getString("full_name"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("hashed_password"),
                            rs.getString("reset_token"),
                            rs.getTimestamp("token_expiry"),
                            rs.getString("role")
                    );
                    return user;
                }
                return null;
            }
        } catch (SQLException e) {
            System.err.println("getUserById: Error retrieving user with ID " + userId);
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    @Override
    public void updatePassword(String email, String newPassword) throws SQLException {
        String sql = "UPDATE users SET hashed_password = ?, reset_token = NULL, token_expiry = NULL WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            stmt.setString(1, hashedPassword);
            stmt.setString(2, email);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("updatePassword: Password updated for email '" + email + "'. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println("updatePassword: Error updating password for email '" + email + "'");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public User getUserByResetToken(String token) throws SQLException {
        String sql = "SELECT id, full_name, username, email, hashed_password, reset_token, token_expiry, role FROM users WHERE reset_token = ? AND token_expiry > NOW()";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, token);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Debugging logs
                    System.out.println("Fetched user for reset_token: " + token);

                    // Return the User object
                    return new User(
                            rs.getInt("id"),
                            rs.getString("full_name"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("hashed_password"),
                            rs.getString("reset_token"),
                            rs.getTimestamp("token_expiry"),
                            rs.getString("role")
                    );
                }
                System.out.println("No user found with valid token: " + token);
                return null;
            }

        } catch (SQLException e) {
            System.err.println("getUserByResetToken: Error fetching user for reset token '" + token + "'");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void setResetToken(String email, String token, Timestamp expiry) throws SQLException {
        String sql = "UPDATE users SET reset_token = ?, token_expiry = ? WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (token == null) {
                stmt.setNull(1, java.sql.Types.VARCHAR);
                stmt.setNull(2, java.sql.Types.TIMESTAMP);
            } else {
                stmt.setString(1, token);
                stmt.setTimestamp(2, expiry);
            }
            stmt.setString(3, email);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("setResetToken: Token set for email '" + email + "'. Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println("setResetToken: Error setting reset token for email '" + email + "'");
            e.printStackTrace();
            throw e;
        }
    }
}