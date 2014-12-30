/*
 * Classe qui gère la mémoire.
 */
package interpreter;

import java.util.HashMap;

public class Memoire {
    
    /**
     * Mémoire.
     */
    private static HashMap<String, String> memoire = new HashMap<>();
    
    /**
     * Mémoire du programme.
     * Constructeur privé, car on en a besoin que d'une seule.
     */
    private Memoire() {
        
    }
    
    /**
     * Méthode qui permet de récupérer la mémoire.
     * 
     * @return la mémoire. (unique)
     */
    public static HashMap<String, String> getMemoire() {
        return Memoire.memoire;
    }

    public static void afficherEtatMemoire() {
        System.out.println(Memoire.memoire + "\n");
    }
    
    public static void ajouter(String nom, String valeur) {
        Memoire.memoire.put(nom, valeur);
    }
    
}
