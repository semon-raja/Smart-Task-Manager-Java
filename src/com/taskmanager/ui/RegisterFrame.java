package com.taskmanager.ui;

import javax.swing.*;
import java.awt.*;
import com.taskmanager.dao.UserDAO;

public class RegisterFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public RegisterFrame() {
        setTitle("Register");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        JButton btnRegister = new JButton("Register");
        JButton btnBack = new JButton("Back");

        panel.add(btnRegister);
        panel.add(btnBack);

        add(panel);

        btnRegister.addActionListener(e -> register());
        btnBack.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        setVisible(true);
    }

    private void register() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        try {
            UserDAO dao = new UserDAO();
            dao.register(username, password);
            JOptionPane.showMessageDialog(this, "Registration successful!");
            new LoginFrame();
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Username already exists!");
        }
    }
}
