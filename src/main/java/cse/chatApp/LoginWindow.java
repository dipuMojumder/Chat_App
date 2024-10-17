package cse.chatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginWindow extends JFrame {
    private JTextField userText;
    private JPasswordField passText;
    private JComboBox<String> roleComboBox;
    private JPanel leftPanel, rightPanel;
    private JLabel logoLabel, welcomeLabel;
    private JButton loginButton, signUpButton;

    public LoginWindow() {
        setTitle("Chat App Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        createPanels();
        createLoginComponents();

        setVisible(true);
    }

    private void createPanels() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(2, 48, 71)); // Set your desired color here

        gbc.gridx = 0;
        gbc.weightx = 0.25; // 25% of the total width
        add(leftPanel, gbc);

        rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.weightx = 0.75; // 75% of the total width
        add(rightPanel, gbc);
    }

    private void createLoginComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel brandLabel = new JLabel("JU CSE CHAT");
        brandLabel.setFont(new Font("Arial", Font.BOLD, 40));
        brandLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        leftPanel.add(brandLabel, gbc);

        JLabel sloganLabel = new JLabel("Your Message, Our Responsibility");
        sloganLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        sloganLabel.setForeground(Color.WHITE);
        gbc.gridy = 2;
        leftPanel.add(sloganLabel, gbc);

        // Right panel components
        welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(welcomeLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;

        userText = createStyledTextField("Username");
        gbc.gridy = 1;
        rightPanel.add(userText, gbc);

        passText = createStyledPasswordField("Password");
        gbc.gridy = 2;
        rightPanel.add(passText, gbc);

        loginButton = createStyledButton("Login", new Color(65, 105, 225));
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel label = new JLabel("                             Or,");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        rightPanel.add(label, gbc);
        gbc.gridx = 1;

        signUpButton = createStyledButton("Create Account", new Color(80, 112, 211));
        gbc.gridx = 0;
        gbc.gridy = 6;
        rightPanel.add(signUpButton, gbc);

        // Event listeners
        loginButton.addActionListener(e -> handleLogin());
        signUpButton.addActionListener(e -> openSignUpWindow());
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField textField = new JTextField(20);
        textField.setPreferredSize(new Dimension(300, 50));
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        addPlaceholderStyle(textField, placeholder);
        return textField;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(300, 50));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        addPlaceholderStyle(passwordField, placeholder);
        return passwordField;
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setPreferredSize(new Dimension(325, 50));
        comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 60));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(2, 48, 71)); // Change to your desired button color
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }


    private void addPlaceholderStyle(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }

    private void handleLogin() {
        String username = userText.getText();
        String password = new String(passText.getPassword());

        // Add validation logic for login credentials here if needed

        // Open Main chat window
        dispose(); // Close the Login window
        Main.createAndShowMainGUI(); // Open the Main chat window
    }



    private void openSignUpWindow() {
        new RegistrationWindow();
        dispose();
    }
}