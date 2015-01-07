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
     * Mémoire parent
     */
    private static Memoire memoireEnfant = new Memoire();

    /**
     * Mémoire du programme. Constructeur privé, car on en a besoin que d'une
     * seule.
     */
    public Memoire() {

    }

    public Memoire(Memoire m) {
        this.memoireEnfant = m;
    }

    /**
     * Méthode qui permet de récupérer la mémoire.
     *
     * @return la mémoire. (unique)
     */
    public static HashMap<String, String> getMemoire() {
        return Memoire.memoire;
    }

    public static HashMap<String, String> getMemoireCourante() {
        if (MemoiresLet.MemoireLetCourante == 0) {
            return Memoire.memoire;
        } else {
            return MemoiresLet.getMemoireLetCourante();
        }
    }

    public static void afficherEtatMemoire() {
        System.out.println(Memoire.memoire + "\n");
    }

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

    public Memoire memoire() {
        return this;
    }

    public static Memoire getMemoireEnfant() {
        return Memoire.memoireEnfant;
    }

}
