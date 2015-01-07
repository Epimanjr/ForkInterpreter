package memoire;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Maxime BLAISE
 */
public class Variable {
    
    private final SimpleStringProperty nom;
        private final SimpleStringProperty valeur;

    public Variable(String nom, String valeur) {
        this.nom = new SimpleStringProperty(nom);
        this.valeur =  new SimpleStringProperty(valeur);
    }

    public String getNom() {
        return nom.get();
    }

    public String getValeur() {
        return valeur.get();
    }
        
        

}
