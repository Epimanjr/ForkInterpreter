/*
 * Test de l'assignation
 */
package tests.creationarbre;

import static tests.creationarbre.TestArbre.afficherArbreAvecCommande;

public class TestLet {
    
    public static void main(String[] args) {
        // Let
        afficherArbreAvecCommande("a := 3");
        afficherArbreAvecCommande("let a in a := 5 end");
        afficherArbreAvecCommande("let b be a in if true then return a end");
    }
    
}
