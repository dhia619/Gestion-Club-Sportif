package view.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.Utilisateur;
import util.Lang;
import util.LanguageHandler;
import util.UIConstants;

public abstract class DashboardLayoutPanel extends JPanel {

    protected JPanel contentPanel;
    protected JPanel sidebar;
    protected CardLayout cardLayout;

    protected JButton logoutButton;
    protected JButton notificationButton;
    protected JComboBox<String> languageBox;
    protected JLabel titleLabel;

    protected boolean hasNotifications;
    protected String notificationImagePath;

    public DashboardLayoutPanel(String title) {
        setLayout(new BorderLayout());

        add(createTopBar(title), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createTopBar(String title) {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(0, 60));
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        titleLabel = new CustomLabel(
            title,
            UIConstants.navy,
            UIConstants.titleFont,
            Font.PLAIN,
            22
        );

        logoutButton = new CustomButton(Lang.get("disconnect"), UIConstants.terracotta);

        if (this.hasNotifications) notificationImagePath = "notification.png";
        else notificationImagePath = "no_notification.png";

        ImageIcon notificationImage = new ImageIcon("./resources/images/"+notificationImagePath);
        notificationButton = new CustomButton(Color.WHITE, notificationImage);
        notificationButton.setToolTipText(Lang.get("notifications"));

        notificationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(contentPanel, "notifications");
            }
        });
        
        String[] languages = {"English", "Français", "العربية"};
        languageBox = new JComboBox<>(languages);
        languageBox.setPreferredSize(new Dimension(90, 35));
        languageBox.setToolTipText(Lang.get("language"));

        languageBox.setSelectedItem(switch (LanguageHandler.getLocale()) {
            case "en" -> "English";
            case "fr" -> "Français";
            case "ar" -> "العربية";
            default -> "Français";
        });

        ImageIcon languageIcon = new ImageIcon("./resources/images/worldwide.png");

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);

        rightPanel.add(new JLabel(languageIcon));
        rightPanel.add(languageBox);
        rightPanel.add(notificationButton);
        rightPanel.add(logoutButton);

        topBar.add(titleLabel, BorderLayout.CENTER);
        topBar.add(rightPanel, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createSidebar() {
        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(UIConstants.primaryBackgroundColor);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        return sidebar;
    }

    public JButton addMenuButton(String text, String cardName) {
        JButton button = new CustomButton(text, UIConstants.menuButtonBackgroundColor);

        button.setMaximumSize(new Dimension(190, 45));
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, cardName);
            }
        });

        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(10));

        return button;
    }

    public void addPage(String name, JPanel panel) {
        contentPanel.add(panel, name);
    }

    public JPanel createBasePage() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UIConstants.secondaryBackgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.add(Box.createVerticalStrut(25));

        return panel;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JComboBox<String> getLanguageBox() {
        return languageBox;
    }
    
    public JButton getNotificationButton() {
        return notificationButton;
    }

    public void setHasNotifications(boolean hasNotifications) {
        this.hasNotifications = hasNotifications;

        String imagePath = hasNotifications
                ? "./resources/images/notification.png"
                : "./resources/images/no_notification.png";

        notificationButton.setIcon(new ImageIcon(imagePath));
    }

    public CardLayout getCardLayout(){
        return cardLayout;
    }

    public abstract Utilisateur getUtilisateur();

    public abstract void refreshUIText();
}