package com.taskmanager.ui;

import com.taskmanager.dao.TaskDAO;
import com.taskmanager.model.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class DashboardFrame extends JFrame {

    private int userId;
    private JTable table;
    private DefaultTableModel model;
    private TaskDAO dao = new TaskDAO();

    // 🔹 Make them class-level
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnSort;

    public DashboardFrame(int userId) {
        this.userId = userId;

        setTitle("Smart Task Manager - Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 🔹 Initialize search components
        txtSearch = new JTextField(15);
        btnSearch = new JButton("Search");
        btnSort = new JButton("Sort by Deadline");

        model = new DefaultTableModel(
                new String[]{"ID", "Title", "Category", "Deadline", "Priority"}, 0);

        table = new JTable(model);
        loadTasks();

        JButton btnAdd = new JButton("Add Task");
        JButton btnDelete = new JButton("Delete Task");
        JButton btnExport = new JButton("Export CSV");

        JPanel top = new JPanel();
        top.add(new JLabel("Search:"));
        top.add(txtSearch);
        top.add(btnSearch);
        top.add(btnSort);
        top.add(btnAdd);
        top.add(btnDelete);
        top.add(btnExport);


        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 🔹 Button actions (INSIDE constructor)
        btnAdd.addActionListener(e -> addTask());
        btnDelete.addActionListener(e -> deleteTask());
        
        btnSearch.addActionListener(e -> searchTasks());
        btnSort.addActionListener(e -> sortTasks());

        setVisible(true);
        
        btnExport.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save Tasks as CSV");
            chooser.setSelectedFile(new java.io.File("tasks.csv"));

            int choice = chooser.showSaveDialog(this);
            if (choice == JFileChooser.APPROVE_OPTION) {
                try {
                    List<Task> tasks = dao.getTasksByUser(userId);
                    com.taskmanager.util.CSVExporter.exportTasks(
                        tasks,
                        chooser.getSelectedFile().getAbsolutePath()
                    );
                    JOptionPane.showMessageDialog(this, "CSV exported successfully!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Export failed!");
                }
            }
        });


    }

    private void loadTasks() {
        model.setRowCount(0);
        try {
            List<Task> tasks = dao.getTasksByUser(userId);
            for (Task t : tasks) {
                model.addRow(new Object[]{
                        t.getTaskId(),
                        t.getTitle(),
                        t.getCategory(),
                        t.getDeadline(),
                        t.getPriority()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addTask() {
        JTextField title = new JTextField();
        JTextField category = new JTextField();
        JTextField deadline = new JTextField("2026-01-31");
        JTextField priority = new JTextField();

        Object[] fields = {
                "Title:", title,
                "Category:", category,
                "Deadline (yyyy-mm-dd):", deadline,
                "Priority (1-5):", priority
        };

        int option = JOptionPane.showConfirmDialog(
                this, fields, "Add Task", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                Task t = new Task();
                t.setUserId(userId);
                t.setTitle(title.getText());
                t.setCategory(category.getText());
                t.setDeadline(Date.valueOf(deadline.getText()));
                t.setPriority(Integer.parseInt(priority.getText()));

                dao.addTask(t);
                loadTasks();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        }
    }

    private void searchTasks() {
        try {
            model.setRowCount(0);
            List<Task> tasks = dao.searchTasks(userId, txtSearch.getText());
            for (Task t : tasks) {
                model.addRow(new Object[]{
                        t.getTaskId(),
                        t.getTitle(),
                        t.getCategory(),
                        t.getDeadline(),
                        t.getPriority()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sortTasks() {
        try {
            model.setRowCount(0);
            List<Task> tasks = dao.sortByDeadline(userId);
            for (Task t : tasks) {
                model.addRow(new Object[]{
                        t.getTaskId(),
                        t.getTitle(),
                        t.getCategory(),
                        t.getDeadline(),
                        t.getPriority()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteTask() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a task first!");
            return;
        }

        int taskId = (int) model.getValueAt(row, 0);
        try {
            dao.deleteTask(taskId);
            loadTasks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
