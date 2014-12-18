/*
 * Classe qui gère la mémoire.
 */
package interpreter;

import java.util.HashMap;

public class Memoire {
    
    /**
     * Mémoire.
     */
    private static final HashMap<String, String> memoire = new HashMap<>();
    
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
   
}
