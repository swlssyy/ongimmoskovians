package com.mycompany.hellskitchen;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdminPasswordDialog extends JDialog {
    private boolean isAuthenticated = false;
    private JPasswordField passwordField;

    public AdminPasswordDialog(JFrame parent) {
        super(parent, "Admin Authentication", true);
        setSize(350, 180);
        setLocationRelativeTo(parent);
        setUndecorated(true);
        setLayout(null);

        // Fre8Bit color scheme
        Color bgColor = Color.decode("#1A0036");      // Deep violet
        Color textColor = Color.WHITE;                // White text
        Color titleColor = Color.WHITE;               // Also white for visibility
        Color submitColor = Color.decode("#FF5C7A");  // Pinkish red
        Color cancelColor = Color.decode("#5DBBFF");  // Sky blue
        Color inputBg = new Color(255, 255, 255, 30); // Transparent white

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(bgColor);
        panel.setBounds(0, 0, 350, 180);
        add(panel);

        JLabel titleLabel = new JLabel("Please Input 'CONFIRM' to Continue");
        titleLabel.setBounds(0, 15, 350, 30);
        titleLabel.setForeground(titleColor);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        panel.add(titleLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 60, 250, 30);
        passwordField.setBackground(inputBg);
        passwordField.setForeground(textColor);
        passwordField.setCaretColor(textColor);
        passwordField.setBorder(BorderFactory.createLineBorder(cancelColor));
        panel.add(passwordField);

        JButton submit = new JButton("Submit");
        submit.setBounds(60, 110, 100, 30);
        submit.setBackground(submitColor);
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);
        submit.setBorderPainted(false);
        panel.add(submit);

        JButton cancel = new JButton("Cancel");
        cancel.setBounds(190, 110, 100, 30);
        cancel.setBackground(cancelColor);
        cancel.setForeground(Color.WHITE);
        cancel.setFocusPainted(false);
        cancel.setBorderPainted(false);
        panel.add(cancel);

        submit.addActionListener(e -> {
            String input = new String(passwordField.getPassword());
            if (input.equals(users.adminPassword)) {
                isAuthenticated = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect input.", "Access Denied", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancel.addActionListener(e -> dispose());
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }
}
