package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Utilisateur;

public class UtilisateurDAO {
    
    public Utilisateur getUtilisateurById(int id) {
        Connection connection = DatabaseConnection.getConnection();
        Utilisateur utilisateur = null;
        try {
            String query = "SELECT * FROM utilisateurs WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                utilisateur = mapResultSetToUtilisateur(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching utilisateur: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
        return utilisateur;
    }

    public Utilisateur getUtilisateurByLogin(String login) {
        Connection connection = DatabaseConnection.getConnection();
        Utilisateur utilisateur = null;
        try {
            String query = "SELECT * FROM utilisateurs WHERE login = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                utilisateur = mapResultSetToUtilisateur(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching utilisateur: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
        return utilisateur;
    }

    public boolean addUtilisateur(Utilisateur utilisateur) {
        Connection connection = DatabaseConnection.getConnection();
        boolean success = false;
        try {
            String query = "INSERT INTO utilisateurs (nom, prenom, date_naissance, telephone, adresse, poids, login, mot_de_passe, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, utilisateur.getNom());
            statement.setString(2, utilisateur.getPrenom());
            statement.setDate(3, java.sql.Date.valueOf(utilisateur.getDateNaissance()));
            statement.setString(4, utilisateur.getTelephone());
            statement.setString(5, utilisateur.getAdresse());
            statement.setDouble(6, utilisateur.getPoids());
            statement.setString(7, utilisateur.getLogin());
            statement.setString(8, utilisateur.getMotDePasse());
            statement.setString(9, utilisateur.getRole());
            int rowsInserted = statement.executeUpdate();
            success = rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Error adding utilisateur: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
        return success;
    }

    private Utilisateur mapResultSetToUtilisateur(ResultSet resultSet) throws SQLException {
        return new Utilisateur(
            resultSet.getInt("id"),
            resultSet.getString("nom"),
            resultSet.getString("prenom"),
            resultSet.getDate("date_naissance") != null ? resultSet.getDate("date_naissance").toLocalDate() : null,
            resultSet.getString("telephone"),
            resultSet.getString("adresse"),
            resultSet.getDouble("poids"),
            resultSet.getString("login"),
            resultSet.getString("mot_de_passe"),
            resultSet.getString("role")
        );
    }
}
