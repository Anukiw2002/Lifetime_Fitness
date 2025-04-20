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
            String sql = "SELECT cd.id, cd.user_id, cd.phone_number, CONCAT(cd.house_no, ' ', cd.street_name, ', ', cd.city) AS address, cd.date_of_birth, " +
                    "cd.emergency_contact_name, cd.emergency_contact_number, CONCAT(u.full_name, ' ', u.username) AS name, u.email, cd.profile_picture " +
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

                    byte[] profilePicture = rs.getBytes("profile_picture");
                    if (profilePicture != null) {
                        client.setProfilePicture(profilePicture);
                    }

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
            String sql = "SELECT cd.id, cd.user_id, cd.phone_number, CONCAT(cd.house_no, ', ', cd.street_name, ', ', cd.city) AS address, cd.house_no, cd.street_name, cd.city, cd.gender, cd.date_of_birth, " +
                    "cd.emergency_contact_name, cd.emergency_contact_number, CONCAT(u.full_name, ' ', u.username) AS name, u.full_name, u.username, u.email, cd.profile_picture " +
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
                    client.setHouseNo(rs.getString("house_no"));
                    client.setStreetName(rs.getString("street_name"));
                    client.setCity(rs.getString("city"));
                    client.setDateOfBirth(rs.getString("date_of_birth"));
                    client.setEmergencyContactName(rs.getString("emergency_contact_name"));
                    client.setEmergencyContactNumber(rs.getString("emergency_contact_number"));
                    client.setName(rs.getString("name"));
                    client.setEmail(rs.getString("email"));
                    client.setGender(rs.getString("gender"));
                    client.setFirstName(rs.getString("full_name"));
                    client.setUsername(rs.getString("username"));

                    byte[] profilePicture = rs.getBytes("profile_picture");
                    if (profilePicture != null) {
                        client.setProfilePicture(profilePicture);
                    }

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

    public boolean updateClientDetails(int userId, String name, String username, String dateOfBirth, String gender, String emailAddress, String phoneNumber, String houseNo, String streetName, String city, String emergencyContactName, String emergencyContactNumber) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = dbConnection.getConnection();

            String sqlUser = "UPDATE users SET full_name = ?, username = ?, email = ? WHERE id = ?";
            pstmt = con.prepareStatement(sqlUser);
            pstmt.setString(1, name);
            pstmt.setString(2, username);
            pstmt.setString(3, emailAddress);
            pstmt.setInt(4, userId);
            int userRowsAffected = pstmt.executeUpdate();
            pstmt.close();


            String sqlClient = "UPDATE client_details SET phone_number = ?, date_of_birth = CAST(? AS DATE), gender = ?, " +
                    "house_no = ?, street_name = ?, city = ?, " +
                    "emergency_contact_name = ?, emergency_contact_number = ? " +
                    "WHERE user_id = ?";
            pstmt = con.prepareStatement(sqlClient);
            pstmt.setString(1, phoneNumber);
            pstmt.setString(2, dateOfBirth);
            pstmt.setString(3, gender);
            pstmt.setString(4, houseNo);
            pstmt.setString(5, streetName);
            pstmt.setString(6, city);
            pstmt.setString(7, emergencyContactName);
            pstmt.setString(8, emergencyContactNumber);
            pstmt.setInt(9, userId);
            int clientRowsAffected = pstmt.executeUpdate();


            return userRowsAffected > 0 && clientRowsAffected > 0;
        } catch(SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateProfilePicture(int userId, byte[] profilePicture) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = dbConnection.getConnection();

            String sql = "UPDATE client_details SET profile_picture = ? WHERE user_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setBytes(1, profilePicture);
            pstmt.setInt(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch(SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateClientDetailsWithProfilePicture(int userId, String name, String username, String dateOfBirth, String gender,
                                                         String emailAddress, String phoneNumber, String houseNo, String streetName,
                                                         String city, String emergencyContactName, String emergencyContactNumber,
                                                         byte[] profilePicture) {
        boolean detailsUpdated = updateClientDetails(userId, name, username, dateOfBirth, gender, emailAddress, phoneNumber,
                houseNo, streetName, city, emergencyContactName, emergencyContactNumber);

        // If profile picture is provided, update it
        if (profilePicture != null && profilePicture.length > 0) {
            boolean pictureUpdated = updateProfilePicture(userId, profilePicture);
            return detailsUpdated && pictureUpdated;
        }

        return detailsUpdated;
    }
}