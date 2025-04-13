package org.example.demo2.dao;

import org.example.demo2.model.Review;
import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewDAO {

    public boolean insertReview(int rating, String review, int userId) {
        String sql = "INSERT INTO reviews (rating, review, userId) VALUES (?,?,?)";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1,rating);
            pstmt.setString(2,review);
            pstmt.setInt(3,userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected >0;
        }

        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Review viewUserReview(int userId) {
        String sql = "SELECT * FROM reviews WHERE userId = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){

            pstmt.setInt(1,userId);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    Review review = new Review();
                    review.setRating(rs.getInt("rating"));
                    review.setReview(rs.getString("review"));
                    review.setCreatedAt(rs.getDate("createdAt"));
                    return review;
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
}
