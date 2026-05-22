package service;

import java.util.ArrayList;

import dao.NotificationDAO;
import model.Notification;

public class NotificationService {

    private NotificationDAO notificationDAO = new NotificationDAO();

    public ArrayList<Notification> findUserNotifications(int utilisateurId) {
        return notificationDAO.findUserNotfications(utilisateurId);
    }

    public boolean create(Notification notification) {
        return notificationDAO.create(notification);
    }

    public boolean markNotificationAsRead(int notificationId) {
        return notificationDAO.markNotificationAsRead(notificationId);
    }

    public void markNotificationsAsRead(ArrayList<Notification> notifications) {
        for (Notification notification : notifications) {
            if (!notification.getLu()) {
                markNotificationAsRead(notification.getId());
            }
        }
    }

}
