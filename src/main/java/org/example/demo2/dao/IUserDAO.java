package org.example.demo2.dao;

import org.example.demo2.model.User;
import java.sql.SQLException;
import java.sql.Timestamp;

public interface IUserDAO {


    void registerUser(User user) throws SQLException;


    boolean emailExists(String email) throws SQLException;


    boolean usernameExists(String username) throws SQLException;


    User authenticateUser(String username, String plainPassword) throws SQLException;


    void setResetToken(String email, String token, Timestamp expiry) throws SQLException;


    User getUserByResetToken(String token) throws SQLException;


    User getUserByEmail(String email) throws SQLException;


    void updatePassword(String email, String newPassword) throws SQLException;


    boolean verifyPassword(String plainPassword, String hashedPassword);


    boolean validatePasswordResetToken(String token) throws SQLException;

}
