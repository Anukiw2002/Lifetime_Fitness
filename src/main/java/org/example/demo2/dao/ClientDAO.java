package org.example.demo2.dao;

import org.example.demo2.model.Client;
import org.example.demo2.util.DBConnection;
import java.sql.*;

public class ClientDAO {
    private final DBConnection dbConnection;

    public ClientDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Add this method to your ClientDAO class:
    public Client findById(Long userId) throws SQLException {
        String query = "SELECT * FROM clients WHERE user_id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);  // Using setLong instead of setInt
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Client client = new Client();
                    client.setUserId(resultSet.getLong("user_id"));  // Using getLong
                    // Set other fields...
                    return client;
                }
            }
        }
        return null;
    }

    public Client getById(Long clientId) throws SQLException {
        String sql = "SELECT * FROM clients WHERE user_id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, clientId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Client client = new Client();
                client.setUserId(rs.getLong("user_id"));
                client.setName(rs.getString("name"));
                client.setClientPhone(rs.getString("client_phone"));
                // Set other fields...
                return client;
            }
        }
        return null;
    }
}
