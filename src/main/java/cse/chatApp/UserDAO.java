package cse.chatApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean registerUser(String firstName, String lastName, String email, String password, String userName, String gender, String phone) {
        String sql = "INSERT INTO users (first_name, last_name, userName, phone, email, password, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
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

    public boolean validateUser(String userName, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // returns true if a user was found
        } catch (SQLException e) {
            System.out.printf(userName, password);
            e.printStackTrace();
            return false;
        }
    }
}