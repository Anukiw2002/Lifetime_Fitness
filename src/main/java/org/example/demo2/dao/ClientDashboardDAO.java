package org.example.demo2.dao;

import org.example.demo2.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDashboardDAO {
    public int getActiveMembers() throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM client_membership WHERE is_cancelled= false";
        int count = -1;
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                count =  rs.getInt("count");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

}
