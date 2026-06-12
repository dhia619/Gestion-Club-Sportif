package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import model.Activite;
import model.ActiviteRow;
import model.Inscription;
import model.MembreActifRow;
import model.enums.StatutInscription;
import model.Utilisateur;

public class InscriptionDAO {

    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private ActiviteDAO activiteDAO = new ActiviteDAO();

    public boolean create(Inscription inscription) {

        String query = """
            INSERT INTO inscriptions (
                membre_id,
                activite_id,
                date_inscription,
                statut
            )
            VALUES (?, ?, ?, ?)
        """;

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
        ){
            statement.setInt(1, inscription.getMembre().getId());
            statement.setInt(2, inscription.getActivite().getId());
            statement.setString(3, inscription.getDateInscription().toString());
            statement.setString(4, inscription.getStatut().name());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Inscription findById(int inscriptionId) {
        
        try ( 
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM inscriptions WHERE id = ?");
        ) {
            statement.setInt(1, inscriptionId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                return mapResultSetToInscription(result);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Inscription> findAll() {

        ArrayList<Inscription> inscriptions = new ArrayList<>();

        try ( 
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM inscriptions");
        ) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                inscriptions.add(mapResultSetToInscription(result));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inscriptions;
    }

    public ArrayList<Inscription> findByMembre(int membreId) {

        ArrayList<Inscription> inscriptions = new ArrayList<>();

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM inscriptions WHERE membre_id = ?");
        ) {

            statement.setInt(1, membreId);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                inscriptions.add(mapResultSetToInscription(result));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inscriptions;
    }

    public ArrayList<Inscription> findByActivite(int activiteId){
        ArrayList<Inscription> inscriptions = new ArrayList<>();

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM inscriptions WHERE activite_id = ?");
        ) {

            statement.setInt(1, activiteId);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                inscriptions.add(mapResultSetToInscription(result));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inscriptions;
    }

    public Inscription findByMembreAndActivite(int membreId, int activiteId) {
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM inscriptions WHERE membre_id = ? AND activite_id = ?");
        ){
            statement.setInt(1, membreId);
            statement.setInt(2, activiteId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return mapResultSetToInscription(result);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    } 

    public boolean updateStatut(int inscriptionId, StatutInscription statut) {
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE inscriptions SET statut = ? WHERE id = ?");
        ) {
            statement.setString(1, statut.name());
            statement.setInt(2, inscriptionId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int inscriptionId) {

        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM inscriptions WHERE id = ?");
        ) {
            statement.setInt(1, inscriptionId);
            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Inscription mapResultSetToInscription(ResultSet resultSet) throws SQLException {
        Utilisateur membre = utilisateurDAO.findById(resultSet.getInt("membre_id"));
        Activite activite = activiteDAO.findById(resultSet.getInt("activite_id"));
        return new Inscription(
            resultSet.getInt("id"),
            membre,
            activite,
            LocalDateTime.parse(resultSet.getString("date_inscription")),
            StatutInscription.valueOf(resultSet.getString("statut"))
        );
    }

    public ArrayList<MembreActifRow> findMembresPlusActifs(int n) {
        ArrayList<MembreActifRow> rows = new ArrayList<>();
        String sql;
        if (n >= 0) {
            sql = """
                SELECT u.id, count(u.id) as nb_inscriptions
                FROM inscriptions i, utilisateurs u
                WHERE statut = "ACCEPTEE" and i.membre_id = u.id
                GROUP BY u.id
                ORDER BY nb_inscriptions DESC
                LIMIT ?;
            """;
        }
        else {
            sql = """
                SELECT u.id, count(u.id) as nb_inscriptions
                FROM inscriptions i, utilisateurs u
                WHERE statut = "ACCEPTEE" and i.membre_id = u.id
                GROUP BY u.id
                ORDER BY nb_inscriptions DESC;
            """;
        }
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            if (n >= 0) statement.setInt(1, n);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                rows.add(mapResultSetToMembreActifRow(result));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    private MembreActifRow mapResultSetToMembreActifRow(ResultSet resultSet) throws SQLException {
        Utilisateur membre = utilisateurDAO.findById(resultSet.getInt("id"));
        return new MembreActifRow(
            membre,
            resultSet.getInt("nb_inscriptions")
        );
    }
    
    public int getNbInscriptionsAccepteParActivite(int activiteId) {
        String sql = """
            SELECT COUNT(*) AS total
            FROM inscriptions
            WHERE statut = 'ACCEPTEE'
            AND activite_id = ?;
        """;
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {

            statement.setInt(1, activiteId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countInscriptions() {
        String sql = "SELECT COUNT(*) FROM inscriptions";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countMembreInscriptions(int membreId) {
        String sql = "SELECT COUNT(*) FROM inscriptions WHERE membre_id = ?";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, membreId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    } 

    public int countPendingInscriptions() {
        String sql = "SELECT COUNT(*) FROM inscriptions WHERE statut = 'EN_ATTENTE'";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countMembrePendingInscriptions(int membreId) {
        String sql = "SELECT COUNT(*) FROM inscriptions WHERE membre_id = ? AND statut = 'EN_ATTENTE'";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, membreId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countMembreAcceptedInscriptions(int membreId) {
        String sql = "SELECT COUNT(*) FROM inscriptions WHERE membre_id = ? AND statut = 'ACCEPTEE'";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, membreId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countMembreRejectedInscriptions(int membreId) {
        String sql = "SELECT COUNT(*) FROM inscriptions WHERE membre_id = ? AND statut = 'REFUSEE'";
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setInt(1, membreId);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<ActiviteRow> findActivitesPopulaires(int n) {
        String sql;
        if (n>=0) {
            sql = """                
                SELECT activite_id, COUNT(activite_id) as total
                FROM inscriptions
                GROUP BY activite_id
                ORDER BY COUNT(activite_id) DESC
                LIMIT ?;
            """;
        }
        else {
            sql = """                
                SELECT activite_id, COUNT(activite_id) as total
                FROM inscriptions
                GROUP BY activite_id
                ORDER BY COUNT(activite_id) DESC;
            """;
        }
        ArrayList<ActiviteRow> activitesPopulaires = new ArrayList<ActiviteRow>();
        try (
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            if (n>=0) statement.setInt(1, n);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Activite activite = activiteDAO.findById(result.getInt("activite_id"));
                if (activite != null) {
                    activitesPopulaires.add(
                        new ActiviteRow(activite, result.getInt("total"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activitesPopulaires;
    }
}