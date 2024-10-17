package cse.chatApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Assuming you have a method to get the database connection
    private Connection getConnection() {
        try {
            return DatabaseConnection.getConnection(); // Modify if your connection method is different
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Return null if unable to get the connection
        }
    }

    public boolean registerUser(String firstName, String lastName, String email, String password, String userName, String gender, String phone) {
        String sql = "INSERT INTO users (first_name, last_name, userName, phone, email, password, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, userName);
            statement.setString(4, phone);
            statement.setString(5, email);
            statement.setString(6, password);
            statement.setString(7, gender);
            return statement.executeUpdate() > 0; // returns true if a row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // New method to fetch all usernames from the database
    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        String sql = "SELECT userName FROM users"; // Adjust the column name as necessary

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                users.add(resultSet.getString("userName")); // Adjust column name as per your database
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean validateUser(String userName, String password) {
        String sql = "SELECT * FROM users WHERE userName = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // returns true if a user was found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}