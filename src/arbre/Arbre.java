/*
 * On génère un parser à chaque commande entré par l'utilisateur.
 * Le but : 
 *      -> Spliter la chaîne correctement
 *      -> Et générer l'arbre syntaxique correspondant.
 */
package arbre;

/**
 *
 * @author Maxime BLAISE
 */
public class Arbre {

    private Noeud racine;

    /**
     * Constructeur vide.
     */
    public Arbre() {

    }

    /**
     * Construit un arbre à partir de la racine donnée en paramètre.
     *
     * @param racine La racine de l'arbre
     */
    public Arbre(Noeud racine) {
        this.racine = racine;
    }

    /**
     * Méthode qui génère l'AST en fonction de la ligne saisie par
     * l'utilisateur.
     *
     * @param cmd ligne lu au clavier.
     * @return L'arbre généré.
     */
    public static Arbre genererArbreSyntaxique(String cmd) {
        return new Arbre();
    }

    /**
     * Affichage de l'arbre en mode console.
     */
    public void afficherArbre() {
        // On appelle la méthode qui gère l'affichage d'un Noeud qui gère la récursivité.
        System.out.println("*** DEBUT DE L'ARBRE ***");
        afficherNoeud(this.racine, 0);
        System.out.println("*** FIN DE L'ARBRE ***");
    }

    /**
     * Méthode qui affiche un Noeud. Si c'est une feuille, on affiche la valeur
     * Sinon, appel récursif.
     *
     * @param noeud Le noeud à afficher
     * @param indent Pour l'intentation
     */
    public void afficherNoeud(Noeud noeud, int indent) {
        // Si c'est une feuille
        String indentation = "";
        for (int i = 0; i < indent; i++) {
            indentation += "\t";
        }
        System.out.println(indentation + noeud.getValeur());
        if (!noeud.estUneFeuille()) // On fait un appel récursif
        {
            for (Noeud n : noeud.getFils()) {
                afficherNoeud(n, indent + 1);
            }
        }

    }

    public Noeud getRacine() {
        return racine;
    }

    public void setRacine(Noeud racine) {
        this.racine = racine;
    }

}
