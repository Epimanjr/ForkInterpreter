/*
 * Test de l'assignation
 */
package tests.creationarbre;

import arbre.Arbre;
import arbre.GenererArbre;
import exception.SyntaxErrorException;

public class TestAssignation {
    
    public static void main(String[] args) {
        Arbre a;
        try {
            a = GenererArbre.genererArbreSyntaxique("a := 3");
            a.afficherArbre();
        } catch (SyntaxErrorException ex) {
            System.out.println("Erreur de syntaxe.");
        }
        
        try {
            a = GenererArbre.genererArbreSyntaxique("a :=");
            a.afficherArbre();
        } catch (SyntaxErrorException ex) {
            System.out.println("Erreur de syntaxe.");
        }
    }
}
