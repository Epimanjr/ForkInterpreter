/*
 * Classe qui gère la mémoire.
 */
package memoire;

import arbre.InterpreterArbre;
import java.util.ArrayList;
import java.util.HashMap;

public class Memoire {

    /**
     * Permet de gérer les valeurs temporaires lors des commandes "let".
     */
    public static ArrayList<ValeurTemporaire> memoireLet = new ArrayList<>();

    /**
     * Map associant à chaque nom de variable, une valeur.
     */
    private static HashMap<String, String> memoire = new HashMap<>();

    /**
     * Mémoire du programme. Constructeur privé, car on en a besoin que d'une
     * seule.
     */
    public Memoire() {

    }

    /**
     * Méthode qui permet de récupérer la mémoire.
     * @return la mémoire. (unique)
     */
    public static HashMap<String, String> getMemoire() {
        return Memoire.memoire;
    }

    /**
     * Affiche l'état de la mémoire.
     */
    public static void afficherEtatMemoire() {
        System.out.println(Memoire.memoire + "\n");
    }

    /**
     * Méthode qui ajoute une variable avec sa valeur en mémoire
     * @param nom Nom de la variable
     * @param valeur Valeur de la variable
     */
    public static void ajouter(String nom, String valeur) {
        boolean vtemp = false;
        // On recherche d'abord dans les variables temporaires
        // On modifie si on trouve
        if (!Memoire.memoireLet.isEmpty()) {
            for (int i = Memoire.memoireLet.size() - 1; i >= 0; i--) {
                if (Memoire.memoireLet.get(i).getNom().equals(nom)) {
                    Memoire.memoireLet.get(i).setValeur(valeur);
                    vtemp = true;
                }
            }
        }

        // Si on arrive ici, il faut modifier dans la mémoire globale
        if (vtemp == false) {
            Memoire.memoire.put(nom,valeur);
        }
    }

}
