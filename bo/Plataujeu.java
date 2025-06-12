package com.jeux.jungle.bo;

import java.util.Scanner;

public class Plataujeu {
	 public static String nomJoueur1;
	    public static String nomJoueur2;
    static final int L = 9;
    static final int C = 7;
    static String[][] plat;

    // Codes couleurs ANSI
    static final String RED = "\u001B[31m";
    static final String BLUE = "\u001B[34m";
    static final String CYAN = "\u001B[36m";
    static final String YELLOW = "\u001B[33m";
    static final String GREEN = "\u001B[32m";
    static final String RESET = "\u001B[0m";

    static int joueurCourant = 1;

    public static void initializeBoard() {
        plat = new String[L][C];
        
        // Initialisation vide
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < C; j++) {
                plat[i][j] = "    ";
            }
        }

        // Rivières
        for (int i = 3; i <= 5; i++) {
            plat[i][1] = plat[i][2] = plat[i][4] = plat[i][5] = "EAU ";
        }

        // Pièges
        plat[0][2] = plat[0][4] = plat[1][3] = "pig ";
        plat[8][2] = plat[8][4] = plat[7][3] = "pig ";

        // Tanières (sanctuaires)
        plat[0][3] = "sa1 ";
        plat[8][3] = "sa2 ";

        // Pièces Joueur 1 (en haut)
        plat[0][0] = "L1  "; // Lion
        plat[0][6] = "T1  "; // Tigre
        plat[1][1] = "C1  "; // Chien
        plat[1][5] = "CH1 "; // Chat
        plat[2][0] = "R1  "; // Rat
        plat[2][2] = "P1  "; // Panthère
        plat[2][4] = "Lo1  "; // Lion
        plat[2][6] = "E1  "; // Eléphant

        // Pièces Joueur 2 (en bas)
        plat[6][0] = "E2  ";
        plat[6][2] = "Lo2  ";
        plat[6][4] = "P2  ";
        plat[6][6] = "R2  ";
        plat[7][1] = "CH2 ";
        plat[7][5] = "C2  ";
        plat[8][0] = "T2  ";
        plat[8][6] = "L2  ";
    }

    public static void afficherplat() {
        System.out.println();
        System.out.println("       Plateau de Jungle - Joueur " + joueurCourant);
        System.out.print("    ");

        // Affichage en-tête colonnes
        for (int col = 1; col <= C; col++) {
            System.out.print(String.format("  %d   ", col));
        }
        System.out.println();

        // Ligne de séparation du haut
        System.out.print("   ");
        for (int col = 0; col < C; col++) {
            System.out.print("*******");
        }
        System.out.println();

        for (int i = 0; i < L; i++) {
            // Affichage numéro de ligne avec espace fixe
            System.out.print(String.format(" %d |", i + 1));

            for (int j = 0; j < C; j++) {
                String contenu = plat[i][j].trim();

                String afficher;
                if (contenu.endsWith("1")) {
                    afficher = RED + String.format("%-4s", contenu) + RESET;
                } else if (contenu.endsWith("2")) {
                    afficher = BLUE + String.format("%-4s", contenu) + RESET;
                } else if (contenu.equalsIgnoreCase("EAU")) {
                    afficher = CYAN + "EAU " + RESET;
                } else if (contenu.equalsIgnoreCase("pig")) {
                    afficher = YELLOW + "PIG " + RESET;
                } else if (contenu.equalsIgnoreCase("sa1") || contenu.equalsIgnoreCase("sa2")) {
                    afficher = GREEN + "TAN " + RESET;
                } else {
                    afficher = "    "; // 4 espaces pour aligner
                }

                System.out.print(" " + afficher + " |");
            }
            System.out.println();

            // Ligne de séparation entre les lignes
            System.out.print("   ");
            for (int col = 0; col < C; col++) {
                System.out.print("*******");
            }
            System.out.println();
        }
    }


    public static int getHierarchie(char type) {
        switch (type) {
            case 'E': return 8;  // Eléphant
            case 'L': return 7;  // Lion
            case 'T': return 6;  // Tigre
            case 'P': return 5;  // Panthère
            case 'C': return 4;  // Chien
            case 'H': return 3;  // Chat
            case 'R': return 1;  // Rat
            default: return 0;
        }
    }

    public static boolean estDansPlateau(int l, int c) {
        return l >= 0 && l < L && c >= 0 && c < C;
    }

    public static boolean estDansEau(int l, int c) {
        return (l >= 3 && l <= 5 && (c == 1 || c == 2 || c == 4 || c == 5));
    }

    public static boolean estDansPiege(int l, int c, int joueur) {
        if (joueur == 1)
            return (l == 8 && (c == 2 || c == 4)) || (l == 7 && c == 3);
        else
            return (l == 0 && (c == 2 || c == 4)) || (l == 1 && c == 3);
    }

    public static boolean estTaniereAdverse(int l, int c, int joueur) {
        if (joueur == 1) return l == 8 && c == 3;
        else return l == 0 && c == 3;
    }

    public static boolean peutSauter(int srcL, int srcC, int dstL, int dstC) {
        String piece = plat[srcL][srcC].trim();
        if (piece.isEmpty()) return false;
        char type = piece.charAt(0);
        if (type != 'L' && type != 'T') return false;

        if (srcL != dstL && srcC != dstC) return false;

        int dist = Math.abs(dstL - srcL + dstC - srcC);
        if (dist <= 1) return false;

        if (srcL == dstL) {
            int minC = Math.min(srcC, dstC);
            int maxC = Math.max(srcC, dstC);
            for (int c = minC + 1; c < maxC; c++) {
                if (!estDansEau(srcL, c)) return false;
                if (plat[srcL][c].trim().startsWith("R")) return false;
            }
            return true;
        } else {
            int minL = Math.min(srcL, dstL);
            int maxL = Math.max(srcL, dstL);
            for (int l = minL + 1; l < maxL; l++) {
                if (!estDansEau(l, srcC)) return false;
                if (plat[l][srcC].trim().startsWith("R")) return false;
            }
            return true;
        }
    }

    public static boolean move(int srcL, int srcC, int dstL, int dstC) {
        // Vérifications de base
        if (!estDansPlateau(srcL, srcC) || !estDansPlateau(dstL, dstC)) {
            System.out.println("Position hors plateau!");
            return false;
        }

        String srcPiece = plat[srcL][srcC].trim();
        String dstPiece = plat[dstL][dstC].trim();

        if (srcPiece.isEmpty()) {
            System.out.println("Pas de pièce à déplacer!");
            return false;
        }

        // Vérification joueur
        int joueurPiece = Character.getNumericValue(srcPiece.charAt(srcPiece.length() - 1));
        if (joueurPiece != joueurCourant) {
            System.out.println("Ce n'est pas votre pièce!");
            return false;
        }

        // Vérification tanière adverse (victoire)
        if (estTaniereAdverse(dstL, dstC, joueurCourant)) {
            plat[dstL][dstC] = srcPiece;
            plat[srcL][srcC] = "    ";
            return true;
        }

        // Règles spéciales pour le rat
        if (srcPiece.startsWith("R")) {
            // Rat ne peut pas capturer en sortant de l'eau
            if (estDansEau(srcL, srcC) && !estDansEau(dstL, dstC) && !dstPiece.isEmpty()) {
                System.out.println("Rat ne peut capturer en sortant de l'eau!");
                return false;
            }
            
            // Rat peut traverser l'eau
            if (estDansEau(dstL, dstC)) {
                plat[dstL][dstC] = plat[srcL][srcC];
                plat[srcL][srcC] = "    ";
                joueurCourant = 3 - joueurCourant;
                return true;
            }
        }

        // Déplacement normal
        if (!peutSauter(srcL, srcC, dstL, dstC)) {
            if (!((Math.abs(dstL - srcL) == 1 && dstC == srcC) || 
                 (dstL == srcL && Math.abs(dstC - srcC) == 1))) {
                System.out.println("Déplacement non adjacent!");
                return false;
            }
        }

        // Capture
        if (!dstPiece.isEmpty()) {
            int joueurDst = Character.getNumericValue(dstPiece.charAt(dstPiece.length() - 1));
            if (joueurDst == joueurCourant) {
                System.out.println("Vous ne pouvez pas capturer vos propres pièces!");
                return false;
            }

            // Vérification hiérarchie
            int hSrc = getHierarchie(srcPiece.charAt(0));
            int hDst = getHierarchie(dstPiece.charAt(0));

            // Cas spécial rat vs éléphant
            if (srcPiece.startsWith("R") && dstPiece.startsWith("E")) {
                // Rat peut capturer éléphant
            } 
            else if (estDansPiege(dstL, dstC, 3 - joueurCourant)) {
                hDst = 0; // Pièce dans piège adverse peut être capturée
            }
            else if (hSrc <= hDst) {
                System.out.println("Pièce trop faible pour capturer!");
                return false;
            }
        }

        // Exécution du mouvement
        plat[dstL][dstC] = plat[srcL][srcC];
        plat[srcL][srcC] = "    ";
        joueurCourant = 3 - joueurCourant;
        return true;
    }

    public static boolean finPartie() {
        // Vérifie si un joueur a atteint la tanière adverse
        if (plat[0][3].trim().endsWith("2")) return true;
        if (plat[8][3].trim().endsWith("1")) return true;
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        initializeBoard();

        System.out.println("=== JEU DE JUNGLE ===");
        System.out.println("Commandes: entrez 4 nombres (ligne colonne ligne colonne)");
        System.out.println("Exemple: 1 1 2 1 pour déplacer de (1,1) à (2,1)");

        while (!finPartie()) {
            afficherplat();
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
                    
                    coupValide = move(srcL, srcC, dstL, dstC);
                    if (!coupValide) {
                        System.out.println("Coup invalide! Réessayez.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrez uniquement des nombres!");
                }
            }
        }
        
        afficherplat();
        System.out.println((joueurCourant == 1 ? BLUE : RED) + 
                         "Joueur " + (3 - joueurCourant) + " a gagné!" + RESET);
        sc.close();
    }
}