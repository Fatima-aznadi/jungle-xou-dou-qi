package com.jeux.jungle.bll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jeux.jungle.dao.connexionbd;

public class Connectinscrijoueur {

    public static boolean connectercompte(String NOM, String PASSWORD) throws JoueurException {
        String sql = "SELECT * FROM Joueur WHERE NOM = ? AND PASSWORD = ?";
        try (Connection conn = connexionbd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, NOM);
            stmt.setString(2, PASSWORD);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                throw new JoueurException("Nom ou mot de passe incorrect.");
            }

        } catch (SQLException e) {
            throw new JoueurException("Erreur du connexion.");
        }
    }public static int getJoueurId(String NOM) throws JoueurException {
        String sql = "SELECT ID FROM Joueur WHERE NOM = ?";
        try (Connection conn = connexionbd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, NOM);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("ID");
            } else {
                throw new JoueurException("Joueur introuvable pour le nom: " + NOM);
            }
        } catch (SQLException e) {
            throw new JoueurException("Erreur lors de la récupération de l'ID joueur.");
        }
    }

    public static void creerCompte(String NOM,String PASSWORD) throws JoueurException{
    	 String sql = "INSERT INTO Joueur (NOM, PASSWORD) VALUES (?, ?)";
    	 try (Connection conn = connexionbd.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, NOM);
                stmt.setString(2, PASSWORD);
                int jou = stmt.executeUpdate();
                if (jou == 0) {
                    throw new JoueurException("erreur dans la creation de compte.");
                }

         } catch (SQLException e) {
             throw new JoueurException("Erreur du connexion.");
         }
    }
}
