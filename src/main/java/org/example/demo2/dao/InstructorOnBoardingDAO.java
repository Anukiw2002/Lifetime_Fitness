package org.example.demo2.dao;

import org.example.demo2.model.Certificate;
import org.example.demo2.model.Instructor;
import org.example.demo2.model.User;
import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstructorOnBoardingDAO {
    public boolean addInstructor(User user) {
        String sql = "INSERT INTO users (full_name, username, email, hashed_password, role) VALUES (?,?,?,?,?) RETURNING id";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getHashedPassword());
            pstmt.setString(5, user.getRole());


            var rs = pstmt.executeQuery();
            if (rs.next()) {
                int newUserId = rs.getInt("id");

                return setInstructorStatus(newUserId, "in-progress");
            }
            return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean setInstructorStatus(int userId, String onBoardingStatus){
        String sql= "INSERT INTO instructors (userId, onboardingStatus) VALUES (?,?)";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, onBoardingStatus);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getOnBoardingStatus(int userId) throws SQLException{
        String status = "completed";
        String sql = "SELECT onBoardingStatus FROM instructors where userId = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1,userId);

            try(ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("onboardingStatus");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean resetPassword(String password, int userId) {
        String sql="UPDATE users SET hashed_password = ? WHERE id = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1,password);
            pstmt.setInt(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected>0;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public Instructor getExistingData(int userId) {
        String sql = "SELECT full_name, username, email FROM users WHERE id = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){

            pstmt.setInt(1,userId);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    Instructor instructor = new Instructor();
                    instructor.setFirstName(rs.getString("full_name"));
                    instructor.setSurname(rs.getString("username"));
                    instructor.setEmail(rs.getString("email"));
                    return instructor;
                }
                else{
                    return null;
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean setBasicDetails(String firstName, String surname, String email, int userId, String phoneNumber, String emergencyContactName, String emergencyContactRelationship, String emergencyContactNumber, String houseNumber, String city, String streetName, String nic, String dateOfBirth) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBConnection.getConnection();

            String sqlUser = "UPDATE users SET full_name = ?, username = ?, email = ? WHERE id = ?";
            pstmt = con.prepareStatement(sqlUser);
            pstmt.setString(1, firstName);
            pstmt.setString(2, surname);
            pstmt.setString(3, email);
            pstmt.setInt(4, userId);
            int userRowsAffected = pstmt.executeUpdate();
            pstmt.close();


            String sqlInstructor = "UPDATE instructors SET phoneNumber = ?, emergencyContactName = ?, emergencyContactRelationship = ?, emergencyContactNumber = ?, houseNumber = ?, streetName = ?, city = ?, nic = ?, dateOfBirth = CAST(? AS DATE) WHERE userId = ?";

            pstmt = con.prepareStatement(sqlInstructor);
            pstmt.setString(1, phoneNumber);
            pstmt.setString(2, emergencyContactName);
            pstmt.setString(3, emergencyContactRelationship);
            pstmt.setString(4, emergencyContactNumber);
            pstmt.setString(5, houseNumber);
            pstmt.setString(6, streetName);
            pstmt.setString(7, city);
            pstmt.setString(8, nic);
            pstmt.setString(9, dateOfBirth);
            pstmt.setInt(10, userId);

            int instructorRowsAffected = pstmt.executeUpdate();


            return userRowsAffected > 0 && instructorRowsAffected > 0;
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
            con = DBConnection.getConnection();

            String sql = "UPDATE instructors SET profilePicture = ? WHERE userId = ?";
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

    public boolean updateCertificateDetails(String certificationName, String certificationProvider, int userId) {
        String sql = "UPDATE instructor_certificates SET certificationName = ?, certificationProvider WHERE userId = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1, certificationName);
            pstmt.setString(2,certificationProvider);
            pstmt.setInt(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected>0;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateInstructorDetailsWithProfilePicture(int userId, String firstName, String surname, String dateOfBirth, String emergencyContactRelationship,
                                                         String email, String phoneNumber, String houseNumber, String streetName,
                                                         String city, String emergencyContactName, String emergencyContactNumber,
                                                         byte[] profilePicture, String nic) {
        boolean detailsUpdated = setBasicDetails(firstName, surname, email, userId, phoneNumber, emergencyContactName, emergencyContactRelationship, emergencyContactNumber, houseNumber, city, streetName, nic,
                dateOfBirth);


        // If profile picture is provided, update it
        if (profilePicture != null && profilePicture.length > 0) {
            boolean pictureUpdated = updateProfilePicture(userId, profilePicture);
            return detailsUpdated && pictureUpdated;
        }

        return detailsUpdated;
    }

    public boolean insertCertificates(int userId, String certificationName, String certificationProvider) {
        String sql = "INSERT INTO instructor_Certificates (userId, certificationName, certificationProvider) VALUES (?,?,?)";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1,userId);
            pstmt.setString(2,certificationName);
            pstmt.setString(3,certificationProvider);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected >0;
        }

        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCertificates(int userId) {
        String sql = "DELETE FROM instructor_certificates WHERE userId = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            return true; // Return true even if no rows affected, as this is not an error
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changeStatus(int userId){
        String sql = "UPDATE instructors SET isActive = 'TRUE', onboardingStatus = 'completed' WHERE userId = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setInt(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected>0;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public Instructor getInstructorById(int userId) {
        String sql = "SELECT u.full_name, u.username, u.email, i.* FROM users u JOIN instructors i ON u.id = i.userId WHERE i.userId = ?";
        String certSql = "SELECT certificationName, certificationProvider FROM instructor_certificates WHERE userId = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Instructor instructor = new Instructor();
                    int instructorUserId = rs.getInt("userId");

                    instructor.setUserId(instructorUserId);
                    instructor.setFirstName(rs.getString("full_name"));
                    instructor.setSurname(rs.getString("username"));
                    instructor.setEmail(rs.getString("email"));
                    instructor.setIsActive(rs.getBoolean("isActive"));

                    // Add all other fields from the instructor table
                    instructor.setPhoneNumber(rs.getString("phoneNumber"));
                    instructor.setEmergencyContactName(rs.getString("emergencyContactName"));
                    instructor.setEmergencyContactRelationship(rs.getString("emergencyContactRelationship"));
                    instructor.setEmergencyContactNumber(rs.getString("emergencyContactNumber"));
                    instructor.setHouseNumber(rs.getString("houseNumber"));
                    instructor.setStreetName(rs.getString("streetName"));
                    instructor.setCity(rs.getString("city"));
                    instructor.setNic(rs.getString("nic"));
                    instructor.setDateOfBirth(rs.getString("dateOfBirth"));

                    byte[] profilePicture = rs.getBytes("profilePicture");
                    if (profilePicture != null) {
                        instructor.setProfilePicture(profilePicture);
                    }

                    try (PreparedStatement certStmt = con.prepareStatement(certSql)) {
                        certStmt.setInt(1, instructorUserId);
                        try (ResultSet certRs = certStmt.executeQuery()) {
                            List<Certificate> certificates = new ArrayList<>();
                            while (certRs.next()) {
                                Certificate cert = new Certificate();
                                cert.setCertificationName(certRs.getString("certificationName"));
                                cert.setCertificationProvider(certRs.getString("certificationProvider"));
                                certificates.add(cert);
                            }
                            instructor.setCertificates(certificates);
                        }
                    }
                    return instructor;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Instructor> getAllInstructors() {
        List<Instructor> instructors = new ArrayList<>();
        String sql = "SELECT u.full_name, u.username, u.email, i.* FROM users u JOIN instructors i ON u.id = i.userId";
        String certSql = "SELECT certificationName, certificationProvider FROM instructor_certificates WHERE userId = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Instructor instructor = new Instructor();
                int userId = rs.getInt("userId");

                instructor.setUserId(userId);
                instructor.setFirstName(rs.getString("full_name"));
                instructor.setSurname(rs.getString("username"));
                instructor.setEmail(rs.getString("email"));
                instructor.setIsActive(rs.getBoolean("isActive"));

                byte[] profilePicture = rs.getBytes("profilePicture");
                if (profilePicture != null) {
                    instructor.setProfilePicture(profilePicture);
                }

                try (PreparedStatement certStmt = con.prepareStatement(certSql)) {
                    certStmt.setInt(1, userId);
                    try (ResultSet certRs = certStmt.executeQuery()) {
                        List<Certificate> certificates = new ArrayList<>();
                        while (certRs.next()) {
                            Certificate cert = new Certificate();
                            cert.setCertificationName(certRs.getString("certificationName"));
                            cert.setCertificationProvider(certRs.getString("certificationProvider"));
                            certificates.add(cert);
                        }
                        instructor.setCertificates(certificates);
                    }
                }
                instructors.add(instructor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instructors;
    }

    public boolean changeIsActiveStatus(int userId) {
        String selectSql = "SELECT isActive FROM instructors WHERE userId = ?";
        String updateSql = "UPDATE instructors SET isActive = ? WHERE userId = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement selectStmt = con.prepareStatement(selectSql)) {

            selectStmt.setInt(1, userId);
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    boolean currentStatus = rs.getBoolean("isActive");
                    boolean newStatus = !currentStatus;

                    try (PreparedStatement updateStmt = con.prepareStatement(updateSql)) {
                        updateStmt.setBoolean(1, newStatus);
                        updateStmt.setInt(2, userId);
                        int rowsAffected = updateStmt.executeUpdate();
                        return rowsAffected > 0;
                    }
                } else {
                    // No user found
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
