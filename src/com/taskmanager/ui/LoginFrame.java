package com.taskmanager.ui;

import javax.swing.*;
import java.awt.*;
import com.taskmanager.dao.UserDAO;


public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginFrame() {
        setTitle("Smart Task Manager - Login");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Login", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Register");

        panel.add(btnLogin);
        panel.add(btnRegister);

        add(panel, BorderLayout.CENTER);

        // Login button action
        btnLogin.addActionListener(e -> login());

        // Register button action
        btnRegister.addActionListener(e -> {
            new RegisterFrame();
            dispose();
        });

        setVisible(true);
    }

    private void login() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        try {
            UserDAO dao = new UserDAO();
            int userId = dao.login(username, password);

            if (userId != -1) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                new DashboardFrame(userId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!");
        }
    }
    
    

    public static void main(String[] args) {
        new LoginFrame();
    }
}
