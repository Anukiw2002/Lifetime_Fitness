package org.example.demo2.dao;

import org.example.demo2.model.Client;
import org.example.demo2.util.DBConnection;
import java.sql.*;

public class ClientDAO {
    private final DBConnection dbConnection;

    public ClientDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Client findByPhoneNumber(String clientPhone) throws SQLException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            String sql = "SELECT * FROM client_details WHERE client_phone = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, clientPhone);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Client client = new Client();
                    client.setId(rs.getLong("id"));  // Updated from setClientId to setId
                    client.setUserId(rs.getInt("user_id"));
                    client.setClientPhone(rs.getString("client_phone"));  // Updated method name
                    client.setAddress(rs.getString("address"));
                    client.setDateOfBirth(rs.getDate("date_of_birth"));
                    client.setEmergencyContactName(rs.getString("emergency_contact_name"));
                    client.setEmergencyContactNumber(rs.getString("emergency_contact_number"));
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
