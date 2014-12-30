/*
 * Test de l'assignation
 */
package tests.creationarbre;

import static tests.creationarbre.TestArbre.afficherArbreAvecCommande;

public class TestReturn {

    public static void main(String[] args) {
        // Returns
        afficherArbreAvecCommande("a := 3");
        afficherArbreAvecCommande("return a := 3");
        afficherArbreAvecCommande("if a > 1 then return a");
        afficherArbreAvecCommande("while a < 3 then return a");
    }

}
