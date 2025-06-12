package com.jeux.jungle.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class connexionbd {
    private static final String DB_URL = "jdbc:h2:~/jungle_db";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public static void initDatabase() {
        String createJoueurTable = "CREATE TABLE IF NOT EXISTS JOUEUR (" +
                "ID INT AUTO_INCREMENT PRIMARY KEY," +
                "NOM VARCHAR(50) UNIQUE NOT NULL," +
                "PASSWORD VARCHAR(50) NOT NULL" +  // ✅ Correction ici
                ")";

        String createPartieTable = "CREATE TABLE IF NOT EXISTS HISTORIQUE_PARTIE (" +
                "ID INT AUTO_INCREMENT PRIMARY KEY," +
                "JOUEUR1_ID INT NOT NULL," +
                "JOUEUR2_ID INT NOT NULL," +
                "GAGNANT_ID INT," +
                "DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (JOUEUR1_ID) REFERENCES JOUEUR(ID)," +
                "FOREIGN KEY (JOUEUR2_ID) REFERENCES JOUEUR(ID)," +
                "FOREIGN KEY (GAGNANT_ID) REFERENCES JOUEUR(ID)" +
                ")";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createJoueurTable);
            stmt.execute(createPartieTable);
            System.out.println("Base de données initialisée !");
        } catch (SQLException e) {
            System.err.println("Erreur d'initialisation :");
            e.printStackTrace();
        }
    }
}
