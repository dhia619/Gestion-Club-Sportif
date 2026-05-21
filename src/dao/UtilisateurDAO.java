package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import model.Utilisateur;

public class UtilisateurDAO {
    
    public Utilisateur findById(int id) {
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

    public Utilisateur findByLogin(String login) {
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

    public boolean create(Utilisateur utilisateur) {
        Connection connection = DatabaseConnection.getConnection();
        boolean success = false;
        try {
            String query = "INSERT INTO utilisateurs (nom, prenom, date_naissance, telephone, adresse, poids, login, mot_de_passe, role, first_login) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            statement.setBoolean(10, utilisateur.getFirstLogin());
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

    public ArrayList<Utilisateur> findAll() {
        Connection connection = DatabaseConnection.getConnection();
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
        try {
            String query = "SELECT * FROM utilisateurs where role='MEMBRE'";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Utilisateur utilisateur = mapResultSetToUtilisateur(resultSet);
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching utilisateurs: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
        return utilisateurs;
    }

    public boolean update(Utilisateur utilisateur){
        Connection connection = DatabaseConnection.getConnection();
        boolean success = false;
        String query = """
            UPDATE utilisateurs 
            SET nom = ?, 
            prenom = ?, 
            date_naissance = ?,
            telephone = ?, 
            adresse = ?, 
            poids = ?, 
            login = ?, 
            mot_de_passe = ?, 
            first_login = ?
            WHERE id = ?
        """;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, utilisateur.getNom());
            statement.setString(2, utilisateur.getPrenom());
            statement.setDate(3, Date.valueOf(utilisateur.getDateNaissance()));
            statement.setString(4, utilisateur.getTelephone());
            statement.setString(5, utilisateur.getAdresse());
            statement.setDouble(6, utilisateur.getPoids());
            statement.setString(7, utilisateur.getLogin());
            statement.setString(8, utilisateur.getMotDePasse());
            statement.setBoolean(9, utilisateur.getFirstLogin());
            statement.setInt(10, utilisateur.getId());
            int rowsUpdated = statement.executeUpdate();
            success = rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error updating utilisateur: " + e.getMessage());
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
            String query = "DELETE FROM utilisateurs WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            success = rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting utilisateur: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
        return success;
    }

    public Utilisateur findByRememberMeToken(String token) {
        Connection connection = DatabaseConnection.getConnection();
        Utilisateur utilisateur = null;
        try {
            String query = "SELECT * FROM utilisateurs WHERE remember_me_token = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                utilisateur = mapResultSetToUtilisateur(resultSet);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching utilisateur by remember me token: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
        return utilisateur;
    }

    public boolean updateRememberMeToken(int userId, String token) {
        Connection connection = DatabaseConnection.getConnection();
        boolean success = false;
        try {
            String query = "UPDATE utilisateurs SET remember_me_token = ? where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, token);
            statement.setInt(2, userId);
            int rowsUpdated = statement.executeUpdate();
            success = rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error updating utilisateur: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
        return success;
    }

    public boolean clearRememberMeToken(int userId) {
        return updateRememberMeToken(userId, null);
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
            resultSet.getString("role"),
            resultSet.getBoolean("first_login")
        );
    }
}
