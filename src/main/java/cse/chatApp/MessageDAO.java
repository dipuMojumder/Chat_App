package cse.chatApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// MessageDAO class to handle database operations for messages
public class MessageDAO {

    // Fetch messages between two users
    public static List<Message> getMessagesBetweenUsers(String user1, String user2) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT sender, receiver, content FROM messages WHERE "
                + "(sender = ? AND receiver = ?) OR (sender = ? AND receiver = ?) ORDER BY timestamp ASC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user1);
            statement.setString(2, user2);
            statement.setString(3, user2);
            statement.setString(4, user1);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String sender = rs.getString("sender");
                String receiver = rs.getString("receiver");
                String content = rs.getString("content");
                messages.add(new Message(sender, receiver, content));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public void sendMessage(String sender, String receiver, String content) {
        String sql = "INSERT INTO messages (sender, receiver, content, timestamp) VALUES (?, ?, ?, NOW())";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, sender);
            statement.setString(2, receiver);
            statement.setString(3, content);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
