package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.Notification;
import model.Utilisateur;
import service.NotificationService;
import view.components.NotificationPanel;

public class NotificationController {
    
    private NotificationPanel view;
    private NotificationService notificationService = new NotificationService();

    public NotificationController(Utilisateur utilisateur) {
        this.view = new NotificationPanel(notificationService.findUserNotifications(utilisateur.getId()));

        this.view.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Notification> notifications = notificationService.findUserNotifications(utilisateur.getId());
                refresh(notifications);
            }
        });

        this.view.getMarkAllAsReadButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Notification> notifications = notificationService.findUserNotifications(utilisateur.getId());
                notificationService.markNotificationsAsRead(notifications);
                notifications = notificationService.findUserNotifications(utilisateur.getId());
                refresh(notifications);
            }
        });
    }

    public void refresh(ArrayList<Notification> notifications) {
        this.view.loadNotifications(notifications);
    }

    public NotificationPanel getView() {
        return this.view;
    }

}
