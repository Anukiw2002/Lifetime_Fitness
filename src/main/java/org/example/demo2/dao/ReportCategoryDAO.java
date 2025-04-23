package org.example.demo2.dao;

import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReportCategoryDAO {
    public Map<String, Integer> getPlanType(){
        Map<String, Integer> revenueByType = new LinkedHashMap<>();
        String SQL = "SELECT SPLIT_PART(plan_name, ' - ', 1 ) AS plan_type, " +
                "COUNT(*) AS count " +
                "FROM orders " +
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

}
