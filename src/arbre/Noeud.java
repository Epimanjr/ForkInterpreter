/*
 * Class qui gère un Noeud.
 */
package arbre;

import java.util.ArrayList;

public class Noeud {

    /**
     * Valeur du Noeud.
     */
    private final String valeur;

    /**
     * La liste des Noeuds enfants.
     */
    private final ArrayList<Noeud> fils;

    /**
     * Le Noeud père.
     */
    private Noeud noeudPere;

    /**
     * Construit un Noeud à partir de sa valeur (donné en paramètre)
     *
     * @param valeur La valeur du Noeud.
     */
    public Noeud(String valeur) {
        this.valeur = valeur;
        this.fils = new ArrayList<>();
    }

    /**
     * Ajout d'un fils à la liste des Noeuds enfants.
     *
     * @param n un Noeud.
     */
    public void ajouterFils(Noeud n) {
        // Ajout du lien vers le père
        n.setNoeudPere(this);
        // Ajout du Noeud dans la liste.
        this.fils.add(n);
    }

    /**
     * Retourne vrai si et seulement si le Noeud (this) est une feuille.
     *
     * @return vrai ou faux.
     */
    public boolean estUneFeuille() {
        return this.fils.isEmpty();
    }

    /**
     * Récupère la valeur du Noeud.
     *
     * @return type String
     */
    public String getValeur() {
        return valeur;
    }

    /**
     * Récupère les fils du Noeud.
     *
     * @return une liste de Noeuds.
     */
    public ArrayList<Noeud> getFils() {
        return fils;
    }

    /**
     * Récupère le Noeud père.
     *
     * @return type Noeud.
     */
    public Noeud getNoeudPere() {
        return noeudPere;
    }

    /**
     * Permet de renseigner le Noeud père.
     *
     * @param noeudPere type Noeud.
     */
    public void setNoeudPere(Noeud noeudPere) {
        this.noeudPere = noeudPere;
    }

    /**
     * Affichage entier d'un Noeud.
     *
     * @return String
     */
    @Override
    public String toString() {
        String res = "Valeur : " + this.valeur + "\n";
        res += "Noeud père : ";
        if (this.noeudPere == null) {
            res += "Aucun.\n";
        } else {
            res += this.noeudPere.getValeur() + "\n";
        }
        res += "Noeud(s) fils : ";
        if (this.getFils().isEmpty()) {
            res += "Aucun !";
        } else {
            for (Noeud n : this.getFils()) {
                res += n.getValeur() + " ";
            }
        }
        return res;
    }

}
