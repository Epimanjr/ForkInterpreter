/*
 * Test de l'assignation
 */
package test.interpretationarbre;

import interpreter.Memoire;
import static tests.creationarbre.TestArbre.afficherEtInterpreterArbre;

public class TestAssignation {

    public static void main(String[] args) {

        afficherEtInterpreterArbre("a := 3");
        Memoire.afficherEtatMemoire();

        afficherEtInterpreterArbre("b := 2 + 3 + 11");
        Memoire.afficherEtatMemoire();
        
    }

}
