package cse.chatApp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {
    // Icon paths
    private static final String PEOPLE_ICON = "icon/message.png";
    private static final String PEOPLE_ACTIVE_ICON = "icon/message_selected.png";
    private static final String GROUPS_ICON = "icon/group.png";
    private static final String GROUPS_ACTIVE_ICON = "icon/group_selected.png";
    private static final String INBOX_ICON = "icon/box.png";
    private static final String INBOX_ACTIVE_ICON = "icon/box_selected.png";


    private static JFrame mainFrame;
    private static JPanel mainPanel;
    private static JPanel contentPanel;
    private static JButton currentButton;

//    initializing userDAO class
    private static UserDAO userDAO = new UserDAO();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
        });
    }

    public static void createAndShowMainGUI() {
        mainFrame = new JFrame("Chat Application");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setResizable(true);

        mainPanel = new JPanel(new BorderLayout());

        JPanel menuPanel = createMenuPanel(userDAO);
        JScrollPane scrollPane = createScrollableMenuPane(menuPanel);
        mainPanel.add(scrollPane, BorderLayout.WEST);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.RED);
                                                                                        // right panel created;
        JPanel rightPanel = createMainPanel();
        rightPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(rightPanel);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        mainFrame.add(mainPanel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }



    private static JPanel conversationPanel;

    private static JPanel createMenuPanel(UserDAO userDAO) {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Horizontal alignment
        menuPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        menuPanel.setBackground(new Color(2, 48, 71));

        JButton peopleButton = createMenuButton(PEOPLE_ICON, PEOPLE_ACTIVE_ICON, "People");
        JButton groupsButton = createMenuButton(GROUPS_ICON, GROUPS_ACTIVE_ICON, "Groups");
        JButton inboxButton = createMenuButton(INBOX_ICON, INBOX_ACTIVE_ICON, "Inbox");

        menuPanel.add(peopleButton);
        menuPanel.add(groupsButton);
        menuPanel.add(inboxButton);

        menuPanel.setPreferredSize(new Dimension(180, 50)); // Adjust the height to fit your needs

        // Create a user panel to display users
        JPanel userPanel = new JPanel();
        fetchAndDisplayUsers(userPanel, userDAO); // Fetch and display users here

        // Add the user panel beneath the menu panel
        menuPanel.add(userPanel);

        return menuPanel;
    }

    private static JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        conversationPanel = new JPanel(); // Conversation panel
        conversationPanel.setLayout(new BoxLayout(conversationPanel, BoxLayout.Y_AXIS));
        conversationPanel.setBackground(Color.WHITE);
        mainPanel.add(conversationPanel, BorderLayout.CENTER); // Conversation on the right
        return mainPanel;
    }


    private static JPanel createConversationPanel() {
        // Create the conversation panel with BorderLayout
        JPanel panel = new JPanel(new BorderLayout());

        // Create the message display area (TextArea for showing messages)
        JTextArea messageDisplayArea = new JTextArea();
        messageDisplayArea.setEditable(false);  // Messages should not be editable
        JScrollPane scrollPane = new JScrollPane(messageDisplayArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create a text field for inputting new messages
        JTextField messageInputField = new JTextField();

        // Create a send button to send messages
        JButton sendButton = new JButton("Send");

        // Bottom panel for text input and send button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(messageInputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        // Add the bottom panel to the conversation panel
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;  // Return the initialized conversation panel
    }


    //created messages input panel to input messages
    private static JPanel createMessageInputPanel(String currentUser, String clickedUser) {
        JPanel inputPanel = new JPanel(new BorderLayout());

        JTextArea messageInput = new JTextArea(3, 30); // 3 rows, 30 columns
        JButton sendButton = new JButton("Send");

        sendButton.addActionListener(e -> {
            String content = messageInput.getText().trim();
            if (!content.isEmpty()) {
                MessageDAO messageDAO = new MessageDAO();
                messageDAO.sendMessage(currentUser, clickedUser, content); // Store message in the DB
                messageInput.setText(""); // Clear input field
                loadConversation(currentUser, clickedUser); // Reload conversation to show new message
            }
        });

        inputPanel.add(new JScrollPane(messageInput), BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        return inputPanel;
    }



    // Method to fetch and display previous messages in the conversation panel
    private static void loadConversation(String currentUser, String clickedUser) {

        // Add message area
        JPanel messageArea = new JPanel();
        messageArea.setLayout(new BoxLayout(messageArea, BoxLayout.Y_AXIS)); // Vertically stacked messages

        MessageDAO messageDAO = new MessageDAO();
        List<Message> messages = messageDAO.getMessagesBetweenUsers(currentUser, clickedUser);

        for (Message message : messages) {
            JLabel messageLabel;
            if (message.getSender().equals(currentUser)) {
                messageLabel = new JLabel("You: " + message.getContent());
                messageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            } else {
                messageLabel = new JLabel(message.getSender() + ": " + message.getContent());
                messageLabel.setHorizontalAlignment(SwingConstants.LEFT);
            }
            messageArea.add(messageLabel);
            System.out.println(message.getContent());
        }

        conversationPanel.add(new JScrollPane(messageArea), BorderLayout.CENTER); // Add message area to the conversation panel
        conversationPanel.add(createMessageInputPanel(currentUser, clickedUser), BorderLayout.SOUTH); // Add message input panel

        conversationPanel.revalidate();
        conversationPanel.repaint();
    }




    // Updated fetchAndDisplayUsers method
    private static void fetchAndDisplayUsers(JPanel userPanel, UserDAO userDAO) {
        userPanel.removeAll(); // Clear the panel before adding new users

        List<String> users = userDAO.getAllUsers(); // Assuming userDAO is already instantiated

        // Set the layout for the userPanel
        userPanel.setLayout(new GridLayout(users.size(), 1)); // Change to one column layout

        for (String username : users) {
            JPanel userEntry = new JPanel(new FlowLayout(FlowLayout.LEFT));
            userEntry.setPreferredSize(new Dimension(170, 60)); // Set a size for each user entry
            userEntry.setOpaque(false); // Make the panel transparent

            // Placeholder icon for the user (you can replace it with actual user avatars)
//            JLabel userIcon = new JLabel(new ImageIcon(Main.class.getResource("/path/to/icon.png"))); // Update the path to your icon
//            userIcon.setPreferredSize(new Dimension(40, 40)); // Set size for the icon

            JLabel userLabel = new JLabel(username);
            userLabel.setPreferredSize(new Dimension(150, 40)); // Adjust as needed
            userLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            userLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 10, 0, new Color(230, 230, 250)));

            // Add mouse listener for click events
            userLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String clickedUser = username;
                    System.out.println(clickedUser);
//                    LoginWindow amarNamDe = new LoginWindow();
                    String currentUser = LoginWindow.getUserName().getText();
                    loadConversation(currentUser, clickedUser); // Load conversation when a user is clicked
                }
            });

            userEntry.add(userLabel);
            userPanel.add(userEntry);
        }

        userPanel.revalidate();
        userPanel.repaint();
    }


    private static JScrollPane createScrollableMenuPane(JPanel menuPanel) {
        JScrollPane scrollPane = new JScrollPane(menuPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(210, 400));

        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createEmptyButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createEmptyButton();
            }

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.GRAY;
                this.trackColor = Color.LIGHT_GRAY;
            }

            private JButton createEmptyButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setFocusable(false);
                return button;
            }
        });

        return scrollPane;
    }

    private static JButton createMenuButton(String iconPath, String activeIconPath, String menuType) {
        JButton button = new JButton();
        button.setIcon(loadIcon(iconPath));
        button.setPreferredSize(new Dimension(50, 50));
        button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentButton != null) {
                    currentButton.setIcon(loadIcon(currentButton.getName()));
                    currentButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                }

                currentButton = button;

                button.setIcon(loadIcon(activeIconPath));
                button.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLUE));

                contentPanel.removeAll();
                contentPanel.revalidate();
                contentPanel.repaint();

                System.out.println(menuType + " menu item clicked");
            }
        });

        button.setName(iconPath);
        return button;
    }

    private static ImageIcon loadIcon(String path) {
        return new ImageIcon(Main.class.getResource("/" + path));
    }
}