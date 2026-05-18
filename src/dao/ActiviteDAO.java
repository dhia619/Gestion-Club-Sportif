package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import model.Activite;

public class ActiviteDAO {
    
    public Activite findById(int id) {
        Connection connection = DatabaseConnection.getConnection();
        Activite activite = null;
        try {
            String query = "SELECT * FROM activites WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                activite = mapResultSetToActivite(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching activite: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
        return activite;
    }

    public boolean create(Activite activite) {
        Connection connection = DatabaseConnection.getConnection();
        boolean success = false;
        try {
            String query = "INSERT INTO activites (nom, description, capacite_max, horaire) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, activite.getNom());
            statement.setString(2, activite.getDescription());
            statement.setInt(3, activite.getCapaciteMax());
            statement.setString(4, activite.getHoraire().toString());

            int rowsInserted = statement.executeUpdate();
            success = rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Error adding activite: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
        return success;
    }

    public ArrayList<Activite> findAll() {
        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Activite> activites = new ArrayList<>();
        try {
            String query = "SELECT * FROM activites";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Activite activite = mapResultSetToActivite(resultSet);
                activites.add(activite);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching activites: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
        return activites;
    }

    public boolean update(Activite activite){
        Connection connection = DatabaseConnection.getConnection();
        boolean success = false;
        try {
            String query = "UPDATE activites SET nom = ?, description = ?, capacite_max = ?, horaire = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, activite.getNom());
            statement.setString(2, activite.getDescription());
            statement.setInt(3, activite.getCapaciteMax());
            statement.setString(4, activite.getHoraire().toString());
            statement.setInt(5, activite.getId());
            int rowsUpdated = statement.executeUpdate();
            success = rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error updating activite: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
        return success;
    }

    public boolean delete(int id) {
        Connection connection = DatabaseConnection.getConnection();
        boolean success = false;
        try {
            String query = "DELETE FROM activites WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            success = rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting activite: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
        return success;
    }

    private Activite mapResultSetToActivite(ResultSet resultSet) throws SQLException {
        return new Activite(
            resultSet.getInt("id"),
            resultSet.getString("nom"),
            resultSet.getString("description"),
            resultSet.getInt("capacite_max"),
            resultSet.getObject("horaire", LocalDateTime.class)
        );
    }
}
