/*
 * Classe pour gérer les "let"
 */
package memoire;

/**
 *
 * @author Maxime
 */
public class ValeurTemporaire {
    
    /**
     * Nom de la variable temporaire.
     */
    private String nom;
    
    /**
     * Valeur de cette variable.
     */
    private String valeur;

    /**
     * Construit une variable temporaire.
     * 
     * @param nom Nom de la variable temporaire.
     * @param valeur Valeur de cette variable.
     */
    public ValeurTemporaire(String nom, String valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
    
    
}
