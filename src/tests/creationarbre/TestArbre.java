/*
 * Classe dans laquelle on va tester si la gestion des arbres fonctionne.
 */
package tests.creationarbre;

import arbre.Arbre;
import arbre.GenererArbre;
import arbre.Noeud;
import exception.SyntaxErrorException;
import java.util.Scanner;

public class TestArbre {

    /**
     * Méthode principale
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        // Lecture au clavier
        try (Scanner sc = new Scanner(System.in)) {
            // Lecture au clavier
            String line = lectureClavier(sc);
            while(!line.equals("exit")) {
                afficherArbreAvecCommande(line);
                
                // Demande d'une nouvelle ligne
                line = lectureClavier(sc);
            }
        }
    }
    
    public static void afficherArbreAvecCommande(String commande) {
        System.out.println("Lancement de la commande : " + commande);
        try {
            Arbre a = GenererArbre.genererArbreSyntaxique(commande);
            a.afficherArbre();
        } catch (SyntaxErrorException ex) {
            System.out.println("Erreur de syntaxe.\n");
        }
    }
    
    public static void afficherEtInterpreterArbre(String commande) {
        System.out.println("Lancement de la commande : " + commande);
        try {
            Arbre a = GenererArbre.genererArbreSyntaxique(commande);
            a.afficherArbre();
            System.out.println(a.interpreterArbre());
        } catch (SyntaxErrorException ex) {
            System.out.println("Erreur de syntaxe.\n");
        }
    }
    
    public static void interpreterArbre(String commande) {
        try {
            Arbre a = GenererArbre.genererArbreSyntaxique(commande);
            a.interpreterArbre();
        } catch (SyntaxErrorException ex) {
            System.out.println("Erreur de syntaxe.\n");
        }
    }

    private static String lectureClavier(Scanner sc) {
        System.out.print("Commande : ");
        return sc.nextLine();
    }
    
}
