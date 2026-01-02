package com.taskmanager.dao;

import com.taskmanager.model.Task;
import com.taskmanager.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public void addTask(Task task) throws Exception {
        String sql = "INSERT INTO tasks (user_id, title, category, deadline, priority) VALUES (?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, task.getUserId());
            ps.setString(2, task.getTitle());
            ps.setString(3, task.getCategory());
            ps.setDate(4, task.getDeadline());
            ps.setInt(5, task.getPriority());
            ps.executeUpdate();
        }
    }

    public List<Task> getTasksByUser(int userId) throws Exception {
        List<Task> list = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task t = new Task();
                t.setTaskId(rs.getInt("task_id"));
                t.setTitle(rs.getString("title"));
                t.setCategory(rs.getString("category"));
                t.setDeadline(rs.getDate("deadline"));
                t.setPriority(rs.getInt("priority"));
                list.add(t);
            }
        }
        return list;
    }
    
    public List<Task> searchTasks(int userId, String keyword) throws Exception {
        List<Task> list = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE user_id=? AND title LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task t = new Task();
                t.setTaskId(rs.getInt("task_id"));
                t.setTitle(rs.getString("title"));
                t.setCategory(rs.getString("category"));
                t.setDeadline(rs.getDate("deadline"));
                t.setPriority(rs.getInt("priority"));
                list.add(t);
            }
        }
        return list;
    }

    public List<Task> sortByDeadline(int userId) throws Exception {
        List<Task> list = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE user_id=? ORDER BY deadline";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task t = new Task();
                t.setTaskId(rs.getInt("task_id"));
                t.setTitle(rs.getString("title"));
                t.setCategory(rs.getString("category"));
                t.setDeadline(rs.getDate("deadline"));
                t.setPriority(rs.getInt("priority"));
                list.add(t);
            }
        }
        return list;
    }


    public void deleteTask(int taskId) throws Exception {
        String sql = "DELETE FROM tasks WHERE task_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, taskId);
            ps.executeUpdate();
        }
    }
}
