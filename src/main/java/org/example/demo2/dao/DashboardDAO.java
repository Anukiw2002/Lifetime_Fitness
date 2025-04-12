package org.example.demo2.dao;

import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;



public class DashboardDAO {
    public int getWorkoutCountById(Integer user_id){
        int count = 0;
        String query = "SELECT COUNT(*) AS total FROM client_workouts WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                count = rs.getInt("total");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return count;
    }

    public int getWorkoutSteakById(Integer user_id){

        ArrayList<Object> workoutDates = new ArrayList<>();


        String query = "SELECT DISTINCT created_at FROM client_workouts WHERE user_id = ? ORDER BY created_at DESC";

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1,user_id);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                workoutDates.add(rs.getDate("workout_date").toLocalDate());
            }
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }

        int streak = 0;
        LocalDate today = LocalDate.now();

        for(int i = 0; i< workoutDates.size(); i++){
            LocalDate date = (LocalDate) workoutDates.get(i);

            if(i == 0){
                if (date.equals(today) || date.equals(today.minusDays(1))) {
                    streak++;
                } else {
                    break;
                }
            } else {
                LocalDate prevDate = (LocalDate) workoutDates.get(i - 1);
                if (prevDate.minusDays(1).equals(date)) {
                    streak++;
                } else {
                    break;
                }
            }
        }
        return streak;
    }
}
