package org.example.demo2.dao;

import org.example.demo2.model.LeaderBoard;
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
                "(ur.body_weight - ue.weight) AS weight_loss " +
                "FROM user_reports ur " +
                "JOIN (SELECT email, weight FROM user_exercises WHERE (email, exercise_date) IN " +
                "(SELECT email, MAX(exercise_date) FROM user_exercises GROUP BY email)) ue " +
                "ON ur.email = ue.email " +
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
                        "    WHERE workout_date >= CURRENT_DATE - INTERVAL '7 days' " + // optional filter
                        "    GROUP BY user_id, grp " +
                        "), " +
                        "latest_streaks AS ( " +
                        "    SELECT user_id, MAX(streak) AS current_streak " +
                        "    FROM streaks " +
                        "    GROUP BY user_id " +
                        "), " +
                        "final_result AS ( " +
                        "    SELECT u.full_name, COALESCE(ls.current_streak, 0) AS streak, " +
                        "           RANK() OVER (ORDER BY COALESCE(ls.current_streak, 0) DESC) AS rank " +
                        "    FROM users u " +
                        "    LEFT JOIN latest_streaks ls ON u.id = ls.user_id " +
                        "    WHERE u.id IN (SELECT DISTINCT user_id FROM client_workouts) " + // Only users in client_workouts
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
                list.add(user);
            }
        }
        return list;
    }
}
