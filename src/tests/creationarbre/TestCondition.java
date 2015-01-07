/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.creationarbre;

import static tests.creationarbre.TestArbre.afficherArbreAvecCommande;

/**
 *
 * @author blaise
 */
public class TestCondition {
    
    public static void main(String[] args) {
        afficherArbreAvecCommande("if 2 + 3 > 4 then a else 2 + 3");
        
        afficherArbreAvecCommande("if a > b then a := a + 1 else b := b - 1");
        
        afficherArbreAvecCommande("if a > b and a > 10 then a := a + 1 else b := b - 1");
    }
}
