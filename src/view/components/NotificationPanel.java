package view.components;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import model.Notification;
import util.Lang;
import util.UIConstants;

public class NotificationPanel extends JPanel {

    private ActionsPanel actionsPanel;
    private JPanel notificationsContainer;
    private JPanel mainContainer;

    private JLabel titleLabel;
    private JButton refreshButton;
    private JButton markAllAsReadButton;

    private ArrayList<Notification> notifications;

    public NotificationPanel(ArrayList<Notification> notifications) {

        this.notifications = notifications;

        setLayout(new BorderLayout());

        titleLabel = new CustomLabel(Lang.get("notifications"), UIConstants.tableFont, 25);
        titleLabel.setBackground(UIConstants.secondaryBackgroundColor);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);
        actionsPanel = new ActionsPanel();
        refreshButton = new CustomButton(Lang.get("button.refresh"), UIConstants.menuButtonBackgroundColor);
        markAllAsReadButton = new CustomButton(Lang.get("button.mark.all.as.read"), UIConstants.sunflowerYellow);
        actionsPanel.addComponent(refreshButton);
        actionsPanel.addComponent(markAllAsReadButton);

        notificationsContainer = new JPanel();
        notificationsContainer.setLayout(new BoxLayout(notificationsContainer, BoxLayout.Y_AXIS));
        notificationsContainer.setBackground(UIConstants.secondaryBackgroundColor);

        JScrollPane scroll = new JScrollPane(notificationsContainer);
        scroll.setBorder(null);

        mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(UIConstants.secondaryBackgroundColor);
        mainContainer.add(actionsPanel, BorderLayout.NORTH);
        mainContainer.add(scroll, BorderLayout.CENTER);

        add(mainContainer, BorderLayout.CENTER);

        loadNotifications(notifications);
    }

    public void loadNotifications(ArrayList<Notification> notifications) {
        notificationsContainer.removeAll();
        if (notifications.isEmpty()) {
            JLabel empty = new CustomLabel(Lang.get("no.notifications"), 20);
            empty.setForeground(UIConstants.secondaryTextColor);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            notificationsContainer.add(Box.createVerticalStrut(20));
            notificationsContainer.add(empty);
        } else {
            for (Notification n : notifications.reversed()) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(n.getLu() ? Color.WHITE : UIConstants.lightBlue);
                card.setOpaque(true);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(n.getLu() ? Color.LIGHT_GRAY : UIConstants.belizeBlue),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)
                ));
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

                card.add(new CustomLabel(n.getMessage(), UIConstants.terracotta, UIConstants.tableFont, Font.BOLD, 18), BorderLayout.CENTER);

                JPanel bottom = new JPanel(new BorderLayout());
                bottom.setOpaque(false);
                
                String date = n.getDateCreation().toString().split("T")[0];
                String heure = n.getDateCreation().toString().split("T")[1].substring(0, 5);
                bottom.add(new CustomLabel(
                    date + " " + heure,
                    UIConstants.belizeBlue, UIConstants.tableFont, Font.BOLD, 14
                ), BorderLayout.WEST);

                JLabel badge = new JLabel(n.getLu() ? Lang.get("read") : Lang.get("unread"));
                badge.setFont(new Font(UIConstants.tableFont, Font.BOLD, 12));
                badge.setForeground(n.getLu() ? UIConstants.concreteGrey : UIConstants.belizeBlue);
                bottom.add(badge, BorderLayout.EAST);

                card.add(bottom, BorderLayout.SOUTH);

                notificationsContainer.add(card);
                notificationsContainer.add(Box.createVerticalStrut(5));
            }

            revalidate();
            repaint();
        }
    }

    public JButton getRefreshButton() {
        return this.refreshButton;
    }

    public JButton getMarkAllAsReadButton() {
        return this.markAllAsReadButton;
    }

    public void refreshUIText() {
        titleLabel.setText(Lang.get("notifications"));
        refreshButton.setText(Lang.get("button.refresh"));
        markAllAsReadButton.setText(Lang.get("button.mark.all.as.read"));
        loadNotifications(notifications);
    }
    
}