/*
 * Test de l'assignation
 */
package tests.creationarbre;

import arbre.Arbre;
import arbre.GenererArbre;
import exception.SyntaxErrorException;

public class TestAssignation {
    
    public static void main(String[] args) {
        afficherArbreAvecCommande("a := 3");
        afficherArbreAvecCommande("a :=");
        afficherArbreAvecCommande("a := 2 + 3");
    }
    
    public static void afficherArbreAvecCommande(String commande) {
        System.out.println("Lancement de la commande : " + commande);
        try {
            Arbre a = GenererArbre.genererArbreSyntaxique(commande);
            a.afficherArbre();
        } catch (SyntaxErrorException ex) {
            System.out.println("Erreur de syntaxe.");
        }
    }
}
