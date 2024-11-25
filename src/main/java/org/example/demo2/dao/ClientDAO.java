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
            String sql = "SELECT * FROM clients WHERE phone_number = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, phoneNumber);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Client client = new Client();
                    client.setClientId(rs.getLong("client_id"));
                    client.setPhoneNumber(rs.getString("phone_number"));
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