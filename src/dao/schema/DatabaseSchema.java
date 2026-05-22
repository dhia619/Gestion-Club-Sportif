package dao.schema;

public class DatabaseSchema {

   public static final String CREATE_UTILISATEURS = """           
        CREATE TABLE IF NOT EXISTS utilisateurs ( 
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nom TEXT NOT NULL,
            prenom TEXT NOT NULL,
            date_naissance DATE,
            telephone TEXT,
            adresse TEXT,
            poids REAL,
            login TEXT UNIQUE NOT NULL,
            mot_de_passe TEXT NOT NULL,
            role TEXT NOT NULL,
            remember_me_token TEXT,
            first_login  BOOLEAN
        );
    """;

    public static final String CREATE_ACTIVITES ="""
        CREATE TABLE IF NOT EXISTS activites (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nom TEXT NOT NULL,
            description TEXT,
            capacite_max INTEGER NOT NULL,
            horaire TEXT NOT NULL
        );""";

    public static final String CREATE_INSCRITPTIONS = """
        CREATE TABLE IF NOT EXISTS inscriptions (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            membre_id INTEGER NOT NULL,
            activite_id INTEGER NOT NULL,
            date_inscription TEXT NOT NULL,
            statut TEXT NOT NULL,
            FOREIGN KEY (membre_id) REFERENCES utilisateurs(id) ON UPDATE CASCADE ON DELETE CASCADE,
            FOREIGN KEY (activite_id) REFERENCES activites(id) ON UPDATE CASCADE ON DELETE CASCADE
        );    
    """;    
    public static final String CREATE_NOTIFICATIONS = """
        CREATE TABLE IF NOT EXISTS notifications(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            utilisateur_id INTEGER NOT NULL,
            message TEXT NOT NULL,
            lu BOOLEAN,
            date_creation TEXT NOT NULL,
            FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id)
        );
    """;

}
