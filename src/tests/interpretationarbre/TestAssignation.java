/*
 * Test de l'assignation
 */
package tests.interpretationarbre;

import memoire.Memoire;
import static tests.creationarbre.TestArbre.afficherEtInterpreterArbre;

public class TestAssignation {

    public static void main(String[] args) {

        /*afficherEtInterpreterArbre("a := 3");
        Memoire.afficherEtatMemoire();

        afficherEtInterpreterArbre("b := 1 + 3 + 4");
        Memoire.afficherEtatMemoire();

        afficherEtInterpreterArbre("if a < 10 then c := 1 ; return a");
        Memoire.afficherEtatMemoire();

        afficherEtInterpreterArbre("if a < 10 then return a ; c := 3");
        Memoire.afficherEtatMemoire();

        afficherEtInterpreterArbre("while a < 10 do a := a + 1 ; return a");
        Memoire.afficherEtatMemoire();
        
        afficherEtInterpreterArbre("a := 10");
        Memoire.afficherEtatMemoire();
        
        afficherEtInterpreterArbre("let a in a := 5 ; return a end");
        Memoire.afficherEtatMemoire();
        
        afficherEtInterpreterArbre("return a");
        Memoire.afficherEtatMemoire();*/
        
        afficherEtInterpreterArbre("a := 10");
        Memoire.afficherEtatMemoire();
        
        afficherEtInterpreterArbre("let b be a in b := b + 5 ; return b end");
        Memoire.afficherEtatMemoire();
        
        afficherEtInterpreterArbre("return a");
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
