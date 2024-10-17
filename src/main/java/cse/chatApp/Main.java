package cse.chatApp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        JPanel menuPanel = createMenuPanel();
        JScrollPane scrollPane = createScrollableMenuPane(menuPanel);
        mainPanel.add(scrollPane, BorderLayout.WEST);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Select an item from the menu");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(welcomeLabel);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        mainFrame.add(mainPanel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private static JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        menuPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        menuPanel.setBackground(new Color(230, 230, 250));

        JButton peopleButton = createMenuButton(PEOPLE_ICON, PEOPLE_ACTIVE_ICON, "People");
        JButton groupsButton = createMenuButton(GROUPS_ICON, GROUPS_ACTIVE_ICON, "Groups");
        JButton inboxButton = createMenuButton(INBOX_ICON, INBOX_ACTIVE_ICON, "Inbox");

        menuPanel.add(peopleButton);
        menuPanel.add(groupsButton);
        menuPanel.add(inboxButton);

        menuPanel.setPreferredSize(new Dimension(210, 600));

        return menuPanel;
    }

    private static JScrollPane createScrollableMenuPane(JPanel menuPanel) {
        JScrollPane scrollPane = new JScrollPane(menuPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(210, 600));

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
                contentPanel.add(createContent(menuType));
                contentPanel.revalidate();
                contentPanel.repaint();

                System.out.println(menuType + " menu item clicked");
            }
        });

        button.setName(iconPath);
        return button;
    }

    private static JPanel createContent(String menuType) {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if ("People".equals(menuType)) {
            List<String> users = userDAO.getAllUsers(); // Fetch users from database
            for (String username : users) {
                content.add(new JLabel(username)); // Display each username
            }
        } else if ("Groups".equals(menuType)) {
            content.add(new JLabel("Group 1"));
            content.add(new JLabel("Group 2"));
            content.add(new JLabel("Group 3"));
        } else if ("Inbox".equals(menuType)) {
            content.add(new JLabel("Inbox Item 1"));
            content.add(new JLabel("Inbox Item 2"));
            content.add(new JLabel("Inbox Item 3"));
        }

        return content;
    }

    private static ImageIcon loadIcon(String path) {
        return new ImageIcon(Main.class.getResource("/" + path));
    }
}