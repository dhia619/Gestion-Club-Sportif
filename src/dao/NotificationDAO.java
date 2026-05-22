package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import model.Notification;
import model.Utilisateur;

public class NotificationDAO {
    
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public boolean create(Notification notification) {
        String query = """
            INSERT INTO notifications (
                utilisateur_id,
                message,
                lu,
                date_creation
            )
            VALUES (?, ?, ?, ?)
        """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ){
            statement.setInt(1, notification.getUtilisateur().getId());
            statement.setString(2, notification.getMessage());
            statement.setBoolean(3, notification.getLu());
            statement.setString(4, notification.getDateCreation().toString());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean markNotificationAsRead(int notificationId) {
        String sql = "UPDATE notifications SET lu = true WHERE id = ?";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setInt(1, notificationId);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Notification> findUserNotfications(int utilisateurId) {
        ArrayList<Notification> notifications = new ArrayList<>();

        try ( 
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM notifications WHERE utilisateur_id = ?");
        ) {
            statement.setInt(1, utilisateurId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                notifications.add(mapResultSetToNotification(result));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notifications;
    }

    private Notification mapResultSetToNotification(ResultSet resultSet) throws SQLException {
        Utilisateur utilisateur = utilisateurDAO.findById(resultSet.getInt("utilisateur_id"));
        return new Notification(
            resultSet.getInt("id"),
            utilisateur,
            resultSet.getString("message"),
            resultSet.getBoolean("lu"),
            LocalDateTime.parse(resultSet.getString("date_creation"))
        );
    }

}
