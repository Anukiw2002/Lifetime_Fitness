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
        String sql = "INSERT INTO users (username, email, hashed_password) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getHashedPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Log the exception with a logging framework
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
            // Log the exception with a logging framework
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
            // Log the exception with a logging framework
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
            // Log the exception with a logging framework
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
                        return new User(
                                rs.getString("username"),
                                rs.getString("email"),
                                hashedPassword,
                                rs.getString("reset_token"),
                                rs.getTimestamp("token_expiry")
                        );
                    }
                }
                return null;
            }
        } catch (SQLException e) {
            // Log the exception with a logging framework
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void setResetToken(String email, String token, Timestamp expiry) throws SQLException {
        String sql = "UPDATE users SET reset_token = ?, token_expiry = ? WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            stmt.setTimestamp(2, expiry);
            stmt.setString(3, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Log the exception with a logging framework
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public User getUserByResetToken(String token) throws SQLException {
        String sql = "SELECT * FROM users WHERE reset_token = ? AND token_expiry > NOW()";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("hashed_password"),
                            rs.getString("reset_token"),
                            rs.getTimestamp("token_expiry")
                    );
                }
                return null;
            }
        } catch (SQLException e) {
            // Log the exception with a logging framework
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
                    return new User(
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("hashed_password"),
                            rs.getString("reset_token"),
                            rs.getTimestamp("token_expiry")
                    );
                }
                return null;
            }
        } catch (SQLException e) {
            // Log the exception with a logging framework
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void updatePassword(String email, String newPassword) throws SQLException {
        String sql = "UPDATE users SET hashed_password = ?, reset_token = NULL, token_expiry = NULL WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            stmt.setString(1, hashedPassword);
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Log the exception with a logging framework
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}