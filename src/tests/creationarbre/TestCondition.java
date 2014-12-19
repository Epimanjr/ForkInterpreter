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
    }
}
