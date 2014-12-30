/*
 * Test de l'assignation
 */
package tests.interpretationarbre;

import interpreter.Memoire;
import static tests.creationarbre.TestArbre.afficherEtInterpreterArbre;

public class TestAssignation {

    public static void main(String[] args) {

        
        afficherEtInterpreterArbre("a := 3");
        Memoire.afficherEtatMemoire();

        afficherEtInterpreterArbre("b := 1 + 3 + 4");
        Memoire.afficherEtatMemoire();
        
        afficherEtInterpreterArbre("if a > 1 then return a");
        Memoire.afficherEtatMemoire();
        
        /*
        afficherEtInterpreterArbre("if true then c := a + 1 else d := 2 - 1");
        Memoire.afficherEtatMemoire();
        
        afficherEtInterpreterArbre("e := 0");
        Memoire.afficherEtatMemoire();
        
        afficherEtInterpreterArbre("while e < 10 do e := e + 1 ");
        Memoire.afficherEtatMemoire();*/

    }

}
