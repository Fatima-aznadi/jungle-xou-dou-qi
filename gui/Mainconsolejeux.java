package com.jeux.jungle.gui;
import java.sql.SQLException;

import java.util.Scanner;
import com.jeux.jungle.bo.Plataujeu;
import com.jeux.jungle.bo.Historique_partie;
import com.jeux.jungle.bll.Connectinscrijoueur;
import com.jeux.jungle.bll.JoueurException;

public class Mainconsolejeux {
    // Codes couleurs ANSI
    static final String RED = "\u001B[31m";
    static final String BLUE = "\u001B[34m";
    static final String RESET = "\u001B[0m";
  
    static final String CYAN = "\u001B[36m";
    static final String YELLOW = "\u001B[33m";
    static final String GREEN = "\u001B[32m";
    static int joueurCourant = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-------------------------------");
        System.out.println(RED + "Bienvenue dans XOU DOU QI" + RESET);
        System.out.println("-------------------------------");
        System.out.println();
        System.out.println(BLUE+"VOULEZ VOUS: "+ RESET);
        System.out.println("1. JOUER");
        System.out.println("2. Voir l'historique des joueurs");
        System.out.println("3. aide");
        System.out.println("-------------------------------");
        System.out.print(YELLOW+"Choix : "+ RESET);

        int choix = scanner.nextInt();
        scanner.nextLine(); // consommer le retour à la ligne

        if (choix == 2) {
            Historique_partie.afficher();
        }else if(choix==3){
        	 System.out.println(RED+"-------------------------------"+RESET);
        	System.out.println("Le Xou Dou Qi ou Doushou Qi ou le jeu de la jungle est aussi appelé les échecs des animaux. Il est\r\n"
        			+ "originaire de Chine. Le plateau de jeu est de neuf cases sur sept, de part et d’autre, au centre, deux lacs;\r\n"
        			+ "de chaque côté un sanctuaire et trois cases piègeChaque joueur dispose de huit pièces hiérarchisées (animaux), le vainqueur est celui qui occupe le\r\n"
        			+ "sanctuaire adverse, les prises sont réalisées par substitution, une pièce ne peut prendre qu'une pièce\r\n"
        			+ "ennemie d’hiérarchie égale ou inférieure.\r\n"
        			+ "Hiérarchie des pièces :\r\n"
        			+ "1. ELEPHANT\r\n"
        			+ "2. LION\r\n"
        			+ "3. TIGRE\r\n"
        			+ "4. PANTHERE\r\n"
        			+ "5. CHIEN\r\n"
        			+ "6. LOUP\r\n"
        			+ "7. CHAT\r\n"
        			+ "8. RAT\r\n"
        			+ "Chaque pièce peut capturer une pièce de rang inférieur ou égal mais jamais une pièce d’un rang\r\n"
        			+ "supérieur, sauf le Rat qui peut capturer l’éléphant. Le Lion et le Tigre peuvent sauter par-dessus la rivière\r\n"
        			+ "(sauf si un rat nageant se trouve sur sa trajectoire) dans le sens de la largeur comme dans la longueur.\r\n"
        			+ "Le rat est le seul à pouvoir se déplacer dans l’eau. Les pièces se déplacent toutes d’une case\r\n"
        			+ "horizontalement ou verticalement mais ne peuvent pénétrer dans leur propre sanctuaire. Les prises sont\r\n"
        			+ "réalisées par substitution (les diagonales sont interdites aussi bien pour le déplacement que pour la prise).\r\n"
        			+ "Les Pièges sont des cases où les pièces ennemies se retrouvent tout en bas de la hiérarchie, elles ne\r\n"
        			+ "peuvent prendre aucune pièce et peuvent être prise par toutes les pièces adverses. Le rat ne peut prendre\r\n"
        			+ "en sortant de la rivière. Le premier à atteindre le sanctuaire ennemi gagne. Chaque joueur dispose de\r\n"
        			+ "huit pièces (Eléphant, Lion, Tigre, Panthère, Chien, Loup, Chat, Rat).");
        
        }else if (choix == 1) {
        	
        	    String[] nomsJoueurs = new String[2];
        	    int[] idsJoueurs = new int[2];  // déclaration et initialisation du tableau d'IDs

        	    int i = 1;
        	    while (i <= 2) {
        	        try {
        	            System.out.println("-------------------------------");
        	            System.out.println(CYAN+"Les informations du joueur " + i+RESET);
        	            System.out.println("-------------------------------");
        	            System.out.print("NOM : ");
        	            String NOM = scanner.nextLine();
        	            System.out.print("Mot de passe : ");
        	            String password = scanner.nextLine();

        	            System.out.println(BLUE+"\n--- MENU JOUEUR ---"+RESET);
        	            System.out.println("1. Créer un compte");
        	            System.out.println("2. Se connecter");
        	            System.out.print("Choix : ");
        	            int choi = scanner.nextInt();
        	            scanner.nextLine(); // consommer le retour à la ligne

        	            if (choi == 1) {
        	                Connectinscrijoueur.creerCompte(NOM, password);
        	                System.out.println("Compte créé avec succès !");
        	            } else if (choi == 2) {
        	                Connectinscrijoueur.connectercompte(NOM, password);
        	                System.out.println("Connexion réussie !");
        	            } else {
        	                System.out.println("Veuillez entrer 1 ou 2.");
        	                continue; // redemander pour ce joueur
        	            }

        	            nomsJoueurs[i - 1] = NOM;

        	            // **Récupérer l'ID du joueur juste après la création ou connexion**
        	            int joueurId = Connectinscrijoueur.getJoueurId(NOM);
        	            idsJoueurs[i - 1] = joueurId;

        	            i++; // passer au joueur suivant uniquement si tout OK

        	        } catch (JoueurException e) {
        	            System.err.println("Erreur : " + e.getMessage());
        	            // on reste sur le même joueur pour réessayer
        	        } catch (Exception e) {
        	            System.err.println("Une erreur inattendue est survenue : " + e.getMessage());
        	            e.printStackTrace();
        	            break;
        	        }
        	    }

        	    if (i == 3) {
        	        Plataujeu.nomJoueur1 = nomsJoueurs[0];
        	        Plataujeu.nomJoueur2 = nomsJoueurs[1];
        	        Plataujeu.initializeBoard();

        	        Scanner sc = new Scanner(System.in);

        	        System.out.println(RED+"=== JEU DE JUNGLE ==="+RESET);
        	        System.out.println("Commandes: entrez 4 nombres (ligne colonne ligne colonne)");
        	        System.out.println("Exemple: 1 1 2 1 pour déplacer de (1,1) à (2,1)");

        	        while (!Plataujeu.finPartie()) {
        	            Plataujeu.afficherplat();
        	            System.out.println((joueurCourant == 1 ? RED : BLUE) +
        	                    "Joueur " + joueurCourant + " à vous de jouer:" + RESET);

        	            boolean coupValide = false;
        	            while (!coupValide) {
        	                try {
        	                    System.out.print("> ");
        	                    String input = sc.nextLine();
        	                    String[] coords = input.split(" ");

        	                    if (coords.length != 4) {
        	                        System.out.println("Format incorrect. Entrez 4 nombres.");
        	                        continue;
        	                    }

        	                    int srcL = Integer.parseInt(coords[0]) - 1;
        	                    int srcC = Integer.parseInt(coords[1]) - 1;
        	                    int dstL = Integer.parseInt(coords[2]) - 1;
        	                    int dstC = Integer.parseInt(coords[3]) - 1;

        	                    coupValide = Plataujeu.move(srcL, srcC, dstL, dstC);
        	                    if (!coupValide) {
        	                        System.out.println("Coup invalide! Réessayez.");
        	                    }
        	                } catch (NumberFormatException e) {
        	                    System.out.println("Entrez uniquement des nombres!");
        	                }
        	            }

        	            joueurCourant = (joueurCourant == 1) ? 2 : 1;
        	        }

        	        Plataujeu.afficherplat();

        	        int gagnant = (joueurCourant == 1) ? 2 : 1;

        	        System.out.println((gagnant == 1 ? RED : BLUE) +
        	                "Joueur " + gagnant + " a gagné!" + RESET);

        	        int gagnantId = idsJoueurs[gagnant - 1]; // récupère l'ID du gagnant

        	        try {
        	            Historique_partie.enregistrerPartie(idsJoueurs[0], idsJoueurs[1], gagnantId);
        	        } catch (SQLException e) {
        	            System.err.println("Erreur lors de l'enregistrement de la partie : " + e.getMessage());
        	        }

        	        sc.close();
        	    }
        	}
scanner.close();
    }
}
