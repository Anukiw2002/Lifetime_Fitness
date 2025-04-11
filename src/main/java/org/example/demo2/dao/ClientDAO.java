package org.example.demo2.dao;

import org.example.demo2.model.Client;
import org.example.demo2.util.DBConnection;
import java.sql.*;

public class ClientDAO {
    private final DBConnection dbConnection;

    public ClientDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Client findByPhoneNumber(String phoneNumber) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "SELECT cd.id, cd.user_id, cd.phone_number, cd.address, cd.date_of_birth, " +
                    "cd.emergency_contact_name, cd.emergency_contact_number, CONCAT(u.full_name, ' ', u.username) AS name, u.email " +
                    "FROM client_details cd " +
                    "JOIN users u ON cd.user_id = u.id " +
                    "WHERE cd.phone_number = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, phoneNumber);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Client client = new Client();
                    client.setId(rs.getLong("id"));
                    client.setUserId(rs.getLong("user_id"));
                    client.setPhoneNumber(rs.getString("phone_number"));
                    client.setAddress(rs.getString("address"));
                    client.setDateOfBirth(rs.getString("date_of_birth"));
                    client.setEmergencyContactName(rs.getString("emergency_contact_name"));
                    client.setEmergencyContactNumber(rs.getString("emergency_contact_number"));
                    client.setName(rs.getString("name"));
                    client.setEmail(rs.getString("email"));
                    return client;
                }
                return null;
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public Client findByUserId(int userId) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "SELECT cd.id, cd.user_id, cd.phone_number, cd.address, cd.date_of_birth, " +
                    "cd.emergency_contact_name, cd.emergency_contact_number, CONCAT(u.full_name, ' ', u.username) AS name, u.email " +
                    "FROM client_details cd " +
                    "JOIN users u ON cd.user_id = u.id " +
                    "WHERE cd.user_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, userId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Client client = new Client();
                    client.setId(rs.getLong("id"));
                    client.setUserId(rs.getLong("user_id"));
                    client.setPhoneNumber(rs.getString("phone_number"));
                    client.setAddress(rs.getString("address"));
                    client.setDateOfBirth(rs.getString("date_of_birth"));
                    client.setEmergencyContactName(rs.getString("emergency_contact_name"));
                    client.setEmergencyContactNumber(rs.getString("emergency_contact_number"));
                    client.setName(rs.getString("name"));
                    client.setEmail(rs.getString("email"));
                    return client;
                }
                return null;
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}