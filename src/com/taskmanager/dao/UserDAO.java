package com.taskmanager.dao;

import com.taskmanager.util.DBConnection;
import com.taskmanager.util.PasswordUtil;
import java.sql.*;

public class UserDAO {

    public boolean register(String username, String password) throws Exception {
        String sql = "INSERT INTO users (username, password_hash) VALUES (?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, PasswordUtil.hashPassword(password));
            ps.executeUpdate();
            return true;
        }
    }

    public int login(String username, String password) throws Exception {
        String sql = "SELECT user_id, password_hash FROM users WHERE username=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (rs.getString("password_hash")
                        .equals(PasswordUtil.hashPassword(password))) {
                    return rs.getInt("user_id");
                }
            }
        }
        return -1;
    }
}
