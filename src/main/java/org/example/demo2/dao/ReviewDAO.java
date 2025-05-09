package org.example.demo2.dao;

import org.example.demo2.model.Review;
import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ReviewDAO {

    public boolean insertReview(int rating, String review, int userId) {
        String sql = "INSERT INTO reviews (rating, review, userId) VALUES (?,?,?)";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)) {

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

    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.rating, r.review, r.createdAt, CONCAT(u.full_name, ' ', u.username) AS name, cd.profile_picture FROM reviews r INNER JOIN users u ON r.userId = u.id LEFT JOIN client_details cd ON u.id = cd.user_id ORDER BY r.createdAt DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Review review = new Review();
                review.setRating(rs.getInt("rating"));
                review.setReview(rs.getString("review"));
                review.setCreatedAt(rs.getDate("createdAt"));
                review.setName(rs.getString("name"));

                byte[] profilePicture = rs.getBytes("profile_picture");
                if (profilePicture != null) {
                    review.setProfilePicture(profilePicture);
                }

                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public boolean updateReview(int rating, String review, int userId ) {
        String sql = "UPDATE reviews SET rating = ? , review = ? , createdAt = CURRENT_TIMESTAMP WHERE userId = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setInt(1, rating);
            pstmt.setString(2,review);
            pstmt.setInt(3, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected>0;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteReview(int userId){
        String sql = "DELETE FROM reviews where userId = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)){

            pstmt.setInt(1,userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected>0;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean reviewExists(int userId) {
        String sql = "SELECT COUNT(*) FROM reviews WHERE userId = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
