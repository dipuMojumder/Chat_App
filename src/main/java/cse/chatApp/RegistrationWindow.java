package cse.chatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegistrationWindow extends JFrame {
    private JTextField firstNameField, lastNameField, userNameField, phoneField, emailField;
    private JPasswordField passwordField;
    private JComboBox<String> genderComboBox;
    private JButton signUpButton, backButton;

    public RegistrationWindow() {
        setTitle("Chat Application - Sign Up");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with solid background color
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(2, 48, 71));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        // Top panel for back button and title
        JPanel topPanel = new JPanel(new BorderLayout(20, 20));
        topPanel.setOpaque(false);
        backButton = createStyledButton("← Back", new Color(100, 100, 100));
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        addFormField(formPanel, gbc, "First Name", firstNameField = createStyledTextField());
        addFormField(formPanel, gbc, "Last Name", lastNameField = createStyledTextField());
        addFormField(formPanel, gbc, "Gender", genderComboBox = createStyledComboBox(new String[]{"Male", "Female", "Other"}));
        addFormField(formPanel, gbc, "User ID", userNameField = createStyledTextField());
        addFormField(formPanel, gbc, "Phone", phoneField = createStyledTextField());
        addFormField(formPanel, gbc, "Email", emailField = createStyledTextField());
        addFormField(formPanel, gbc, "Password", passwordField = createStyledPasswordField());

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel for the signup button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        bottomPanel.setOpaque(false);
        signUpButton = createStyledButton("SIGN UP", new Color(65, 105, 225));
        signUpButton.setForeground(Color.WHITE);
        bottomPanel.add(signUpButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        signUpButton.addActionListener(e -> handleSignUp());
        backButton.addActionListener(e -> openLoginWindow());
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 18));
        field.setPreferredSize(new Dimension(250, 35));
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // No border
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Arial", Font.PLAIN, 18));
        field.setPreferredSize(new Dimension(250, 35));
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // No border
        return field;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 18));
        comboBox.setPreferredSize(new Dimension(250, 35));
        comboBox.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // No border
        return comboBox;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void handleSignUp() {
        // Handle registration logic here (validation, saving data, etc.)
        // Assuming successful registration for demonstration
        showRegistrationSuccessDialog();
    }

    private void showRegistrationSuccessDialog() {
        JDialog dialog = new JDialog(this, "Registration Successful", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel messagePanel = new JPanel();
        messagePanel.setBackground(new Color(2, 48, 71));
        messagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel messageLabel = new JLabel("Registration successful! Please log in.");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        messageLabel.setForeground(Color.WHITE);
        messagePanel.add(messageLabel);

        JButton okButton = createStyledButton("OK", new Color(65, 105, 225));
        okButton.addActionListener(e -> {
            dialog.dispose();
            openLoginWindow();
        });

        dialog.add(messagePanel, BorderLayout.CENTER);
        dialog.add(okButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void openLoginWindow() {
        new LoginWindow(); // Create a new instance of the LoginWindow
        dispose(); // Close the registration window
    }
}