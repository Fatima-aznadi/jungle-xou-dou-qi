package com.jeux.jungle.bo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import com.jeux.jungle.dao.connexionbd;

public class Historique_partie {

    public static void enregistrerPartie(int joueur1Id, int joueur2Id, Integer gagnantId) throws SQLException {
        String sql = "INSERT INTO HISTORIQUE_PARTIE (JOUEUR1_ID, JOUEUR2_ID, GAGNANT_ID) VALUES (?, ?, ?)";

        try (Connection conn = connexionbd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, joueur1Id);
            stmt.setInt(2, joueur2Id);

            if (gagnantId != null) {
                stmt.setInt(3, gagnantId);
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER); // égalité ou abandon
            }

            stmt.executeUpdate();
            System.out.println("Partie enregistrée avec succès.");
        }
    }

    public static void afficher() {
        String sql = "SELECT h.ID, j1.NOM AS joueur1, j2.NOM AS joueur2, g.NOM AS gagnant, h.DATE " +
                     "FROM HISTORIQUE_PARTIE h " +
                     "JOIN JOUEUR j1 ON h.JOUEUR1_ID = j1.ID " +
                     "JOIN JOUEUR j2 ON h.JOUEUR2_ID = j2.ID " +
                     "LEFT JOIN JOUEUR g ON h.GAGNANT_ID = g.ID " +
                     "ORDER BY h.DATE DESC";

        try (Connection conn = connexionbd.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n=== HISTORIQUE DES PARTIES ===");
            System.out.println("ID | Joueur 1 vs Joueur 2 | Vainqueur | Date");
            System.out.println("---------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("ID");
                String joueur1 = rs.getString("joueur1");
                String joueur2 = rs.getString("joueur2");
                String gagnant = rs.getString("gagnant");
                String date = rs.getString("DATE");

                String resultat = (gagnant != null) ? gagnant : "Égalité";
                System.out.printf("%d | %s vs %s | %s | %s%n",
                        id, joueur1, joueur2, resultat, date);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'historique :");
            e.printStackTrace();
        }
    

    // Méthode pour insérer un joueur
   
    }

   
}
