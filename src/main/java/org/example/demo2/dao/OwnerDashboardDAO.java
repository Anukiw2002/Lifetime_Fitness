package org.example.demo2.dao;

import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
