package org.example.demo2.dao;

import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class OwnerDashboardDAO {
    public int getAmount() {
        int count = 0 ;
        String sql = "SELECT " +
                "SUM(amount) AS total_amount " +
                "FROM orders " +
                "WHERE " +
                "EXTRACT(MONTH FROM created_at) = EXTRACT(MONTH FROM CURRENT_DATE) " +
                "AND EXTRACT(YEAR FROM created_at) = EXTRACT(YEAR FROM CURRENT_DATE)";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                count = rs.getInt("total_amount");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return count;
    }

    public Map<String, Integer> getRevenueForFourMonths(){
        Map<String, Integer> revenueMap = new LinkedHashMap<>();
        String sql = "SELECT TO_CHAR(created_at, 'YYYY-MM') AS month, SUM(amount) AS total_amount " +
                "FROM orders " +
                "WHERE created_at >= DATE_TRUNC('month', CURRENT_DATE - INTERVAL '3 months') " +
                "GROUP BY TO_CHAR(created_at, 'YYYY-MM')" +
                "ORDER BY month ";

        try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){
            while (rs.next()){
                String month = rs.getString("month");
                int amount = rs.getInt("total_amount");
                revenueMap.put(month, amount);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return revenueMap;
    }
}
