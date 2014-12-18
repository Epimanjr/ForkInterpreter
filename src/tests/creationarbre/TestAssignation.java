/*
 * Test de l'assignation
 */
package tests.creationarbre;

import arbre.Arbre;
import arbre.GenererArbre;

public class TestAssignation {
    
    public static void main(String[] args) {
        Arbre a = GenererArbre.genererArbreSyntaxique("a := 3");
        a.afficherArbre();
    }
}
