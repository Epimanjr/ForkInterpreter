/*
 * Test de l'assignation
 */
package test.interpretationarbre;

import interpreter.Memoire;
import static tests.creationarbre.TestArbre.afficherEtInterpreterArbre;

public class TestAssignation {
    
    public static void main(String[] args) {
        
        // Assignations simples
        afficherEtInterpreterArbre("a := 3");
        Memoire.afficherEtatMemoire();
        
        
        /*
        afficherArbreAvecCommande("a :=");
        
        // Assignation avec opérateurs
        afficherArbreAvecCommande("a := 2 < 3");
        afficherArbreAvecCommande("a := 2 < 3 <= 4");
        
        // Assignation avec calculs
        afficherArbreAvecCommande("a := 2 + 3");
        afficherArbreAvecCommande("a := 2 + 3 * 5");
        afficherArbreAvecCommande("a := 2 * 3 + 5");
        
        afficherArbreAvecCommande("a := 2 * 3 + a := 2 * 3 + 5 + 5");*/
    }
    
    
}
