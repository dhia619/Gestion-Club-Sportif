package dao.schema;

public class DatabaseSchema {

   public static final String CREATE_UTILISATEURS = 
        "CREATE TABLE IF NOT EXISTS utilisateurs (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "nom TEXT NOT NULL," +
        "prenom TEXT NOT NULL," +
        "date_naissance DATE," +
        "telephone TEXT," +
        "adresse TEXT," +
        "poids REAL," +
        "login TEXT UNIQUE NOT NULL," +
        "mot_de_passe TEXT NOT NULL," +
        "role TEXT NOT NULL" +
        ");";

    public static final String CREATE_ACTIVITES =
        "CREATE TABLE IF NOT EXISTS activites (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "nom TEXT NOT NULL," +
        "description TEXT," +
        "capacite_max INTEGER NOT NULL," +
        "horaire TEXT NOT NULL" +
        ");";
}
