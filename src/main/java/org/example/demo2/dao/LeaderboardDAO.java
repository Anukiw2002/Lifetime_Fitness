package org.example.demo2.dao;

import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LeaderboardDAO {
    public List<LeaderBoardUser> getWeightLossLeaderboard() throws SQLException {
        List<LeaderBoardUser> list = new ArrayList<>();
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
                LeaderBoardUser user = new LeaderBoardUser();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setStartingWeight(rs.getDouble("body_weight"));
                user.setCurrentWeight(rs.getDouble("weight"));
                user.setWeightLoss(rs.getDouble("weight_loss"));
                user.setRank(rs.getInt("rank"))
                list.add(user);
            }
        }
        return list;
    }

}
