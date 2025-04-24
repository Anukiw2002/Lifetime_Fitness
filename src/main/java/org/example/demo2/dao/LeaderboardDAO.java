package org.example.demo2.dao;

import org.example.demo2.model.LeaderBoard;
import org.example.demo2.model.LeaderBoardEntry;
import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardDAO {

    public List<LeaderBoard> getWeightLossLeaderboard() throws SQLException {
        List<LeaderBoard> list = new ArrayList<>();
        String sql = "SELECT RANK() OVER (ORDER BY (ur.body_weight - ue.weight) DESC) AS rank, " +
                "ur.name, ur.email, ur.body_weight, ue.weight, " +
                "(ur.body_weight - ue.weight) AS weight_loss, cd.profile_picture " +
                "FROM user_reports ur " +
                "JOIN (SELECT email, weight FROM user_exercises WHERE (email, exercise_date) IN " +
                "(SELECT email, MAX(exercise_date) FROM user_exercises GROUP BY email)) ue " +
                "ON ur.email = ue.email " +
                "JOIN users u ON ur.email = u.email " +
                "JOIN client_details cd ON u.id = cd.user_id " +
                "WHERE (ur.body_weight - ue.weight) > 0 " +
                "ORDER BY rank";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                LeaderBoard user = new LeaderBoard();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setStartingWeight(rs.getDouble("body_weight"));
                user.setCurrentWeight(rs.getDouble("weight"));
                user.setWeightLoss(rs.getDouble("weight_loss"));
                user.setRank(rs.getInt("rank"));

                byte[] profilePicture = rs.getBytes("profile_picture");
                if (profilePicture != null) {
                    user.setProfilePicture(profilePicture);
                }
                list.add(user);
            }
        }
        return list;
    }

    public List<LeaderBoard> getStreakLeaderboard() throws SQLException {
        List<LeaderBoard> list = new ArrayList<>();
        String sql =
                "WITH ranked_dates AS ( " +
                        "    SELECT user_id, DATE(created_at) AS workout_date, " +
                        "           ROW_NUMBER() OVER (PARTITION BY user_id ORDER BY DATE(created_at)) AS row_num " +
                        "    FROM client_workouts " +
                        "    GROUP BY user_id, DATE(created_at) " +
                        "), " +
                        "grouped_dates AS ( " +
                        "    SELECT user_id, workout_date, row_num, " +
                        "           workout_date - INTERVAL '1 day' * row_num AS grp " +
                        "    FROM ranked_dates " +
                        "), " +
                        "streaks AS ( " +
                        "    SELECT user_id, COUNT(*) AS streak " +
                        "    FROM grouped_dates " +
                        "    WHERE workout_date >= CURRENT_DATE - INTERVAL '7 days' " +
                        "    GROUP BY user_id, grp " +
                        "), " +
                        "latest_streaks AS ( " +
                        "    SELECT user_id, MAX(streak) AS current_streak " +
                        "    FROM streaks " +
                        "    GROUP BY user_id " +
                        "), " +
                        "final_result AS ( " +
                        "    SELECT u.full_name, COALESCE(ls.current_streak, 0) AS streak, " +
                        "           RANK() OVER (ORDER BY COALESCE(ls.current_streak, 0) DESC) AS rank, " +
                        "           cd.profile_picture " +
                        "    FROM users u " +
                        "    LEFT JOIN latest_streaks ls ON u.id = ls.user_id " +
                        "    LEFT JOIN client_details cd ON u.id = cd.user_id " +
                        "    WHERE u.id IN (SELECT DISTINCT user_id FROM client_workouts) " +
                        ") " +
                        "SELECT * FROM final_result ORDER BY rank LIMIT 10";



        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                LeaderBoard user = new LeaderBoard();
                user.setName(rs.getString("full_name"));
                user.setStreak(rs.getInt("streak"));
                user.setRank(rs.getInt("rank"));

                byte[] profilePicture = rs.getBytes("profile_picture");
                if (profilePicture != null) {
                    user.setProfilePicture(profilePicture);
                }

                list.add(user);
            }
        }
        return list;
    }

    public int getUserByPhone(String phoneNumber) throws SQLException {
        int userId = -1;
        try (Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT user_id FROM client_details WHERE phone_number = ?")){
            ps.setString(1, phoneNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("user_id");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public String getFullNameByUserId(int userId) throws SQLException {
        String fullName = null;
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT full_name FROM users WHERE id = ?")){
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                fullName = rs.getString("full_name");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return fullName;
    }

    public boolean insertIntoLeaderBoard(int userId, String fullName, String category, double amount){
        boolean inserted = false;
        String sql = "INSERT INTO leaderboard (user_id, full_name, category, amount) VALUES (?,?,?,?)";

        try(Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1,userId);
            ps.setString(2, fullName);
            ps.setString(3, category);
            ps.setDouble(4, amount);

            inserted = ps.executeUpdate() > 0;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return inserted;

    }

    public  List<LeaderBoardEntry> getEntriesByExercise(String exerciseType){
        List<LeaderBoardEntry> entries = new ArrayList<>();
        String sql = "SELECT l.user_id, l.full_name, l.amount, cd.profile_picture FROM leaderboard l LEFT JOIN client_details cd ON l.user_id = cd.user_id WHERE category = ? ORDER BY amount DESC";

        try(Connection con = DBConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(sql)){
            stmt.setString(1,exerciseType);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                LeaderBoardEntry entry = new LeaderBoardEntry();
                entry.setFull_name(rs.getString("full_name"));
                entry.setAmount(rs.getInt("amount"));

                byte[] profilePicture = rs.getBytes("profile_picture");
                if (profilePicture != null) {
                    entry.setProfilePicture(profilePicture);
                }

                entries.add(entry);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return entries;
    }
}
