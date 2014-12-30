/*
 * Classe dans laquelle on va tester si la gestion des arbres fonctionne.
 */
package tests.creationarbre;

import arbre.Arbre;
import arbre.GenererArbre;
import arbre.Noeud;
import exception.SyntaxErrorException;

public class TestArbre {

    /**
     * Méthode principale
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        // Création d'un Noeud
        Noeud n = new Noeud("let");
        // Création de l'arbre avec ce Noeud
        Arbre a = new Arbre(n);
        // Affichage de cet arbre
        a.afficherArbre();

        // Ajout d'un fils
        Noeud fils = new Noeud("--");
        n.ajouterFils(fils);

        // Affichage des Noeuds
        System.out.println(a.getRacine());
        System.out.println(a.getRacine().getFils().get(0));
        
        // Affichage à nouveau de l'arbre
        a.afficherArbre();
    }
    
    public static void afficherArbreAvecCommande(String commande) {
        System.out.println("Lancement de la commande : " + commande);
        try {
            Arbre a = GenererArbre.genererArbreSyntaxique(commande);
            a.afficherArbre();
        } catch (SyntaxErrorException ex) {
            System.out.println("Erreur de syntaxe.\n");
        }
    }
    
    public static void afficherEtInterpreterArbre(String commande) {
        System.out.println("Lancement de la commande : " + commande);
        try {
            Arbre a = GenererArbre.genererArbreSyntaxique(commande);
            a.afficherArbre();
            System.out.println(a.interpreterArbre());
        } catch (SyntaxErrorException ex) {
            System.out.println("Erreur de syntaxe.\n");
        }
    }
    
    public static void interpreterArbre(String commande) {
        try {
            Arbre a = GenererArbre.genererArbreSyntaxique(commande);
            a.interpreterArbre();
        } catch (SyntaxErrorException ex) {
            System.out.println("Erreur de syntaxe.\n");
        }
    }
    
}
