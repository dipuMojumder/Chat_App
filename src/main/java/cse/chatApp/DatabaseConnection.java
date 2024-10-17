package cse.chatApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/chatAppDB"; // Database URL
    private static final String USER = "root"; // Your MySQL username
    private static final String PASSWORD = "53@md.jakIR@53mL"; // Replace with your actual password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Connection successful!");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
