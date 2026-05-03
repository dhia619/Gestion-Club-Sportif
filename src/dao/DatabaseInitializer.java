package dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

import dao.schema.DatabaseSchema;

public class DatabaseInitializer {

    public static void init() {

        Connection conn = DatabaseConnection.getConnection();
        try{
            Statement stmt = conn.createStatement();
    
            stmt.execute(DatabaseSchema.CREATE_UTILISATEURS);
            stmt.execute(DatabaseSchema.CREATE_ACTIVITES);
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }
}