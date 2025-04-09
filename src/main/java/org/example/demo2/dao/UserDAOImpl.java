package org.example.demo2.dao;

import org.example.demo2.model.User;
import org.example.demo2.util.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserDAOImpl implements IUserDAO {

    @Override
    public void registerUser(User user) throws SQLException {
        String sql = "INSERT INTO users (full_name, username, email, hashed_password, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getHashedPassword());
            stmt.setString(5, user.getRole()); // Include role
            stmt.executeUpdate();
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
                        return mapUserFromResultSet(rs);
                    }
                }
                return null;
            }
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
        }
    }

    @Override
    public User getUserByResetToken(String token) throws SQLException {
        String sql = "SELECT * FROM users WHERE reset_token = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUserFromResultSet(rs);
                }
                return null;
            }
        }
    }

    @Override
    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT id, full_name, username, email, hashed_password,reset_token,token_expiry, role FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapUserFromResultSet(rs);
                }
                return null; // Return null if no user is found
            }
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
        }
    }

    @Override
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    @Override
    public boolean validatePasswordResetToken(String token) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE reset_token = ? AND token_expiry > CURRENT_TIMESTAMP";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private User mapUserFromResultSet(ResultSet rs) throws SQLException {
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

        int userId = rs.getInt("id");

        // Return the User object
        return new User(
                userId,
                rs.getString("full_name"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("hashed_password"),
                resetToken,
                tokenExpiry,
                rs.getString("role")
        );
    }

}
