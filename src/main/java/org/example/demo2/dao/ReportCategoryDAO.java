package org.example.demo2.dao;

import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReportCategoryDAO {
    public Map<String, Integer> getPlanType(){
        Map<String, Integer> revenueByType = new LinkedHashMap<>();
        String SQL = "SELECT SPLIT_PART(plan_name, ' - ', 1 ) AS plan_type, " +
                "COUNT(*) AS count " +
                "FROM orders " +
                "WHERE " +
                "EXTRACT(YEAR FROM created_at) = EXTRACT(YEAR FROM CURRENT_DATE) " +
                "GROUP BY " +
                "plan_type " +
                "ORDER BY " +
                "count DESC";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(SQL);
            ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                String type = rs.getString("plan_type");
                int amount = rs.getInt("count");
                revenueByType.put(type, amount);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return revenueByType;
    }

    public Map<String, Integer> getMembersCount(){
        Map<String, Integer> userCount = new HashMap<>();
        String SQL = "SELECT TO_CHAR(created_at, 'YYYY-MM') AS month, " +
                "COUNT(*) AS count " +
                "FROM users " +
                "GROUP BY " +
                "month " +
                "ORDER BY month";

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQL);
        ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                String month = rs.getString("month");
                int count = rs.getInt("count");
                userCount.put(month,count);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return userCount;
    }

    public Map<String, Integer> getPlanCount(){
        Map<String, Integer> plan_count = new HashMap<>();
        String SQL = "SELECT plan_name, COUNT(*) AS total " +
                "FROM orders " +
                "GROUP BY plan_name";

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(SQL);
        ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                String plan_name = rs.getString("plan_name");
                int total = rs.getInt("total");
                plan_count.put(plan_name, total);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return plan_count;
    }

}
