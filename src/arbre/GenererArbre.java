/*
 * Méthode qui génère l'arbre syntaxique
 */
package arbre;

import exception.SyntaxErrorException;

/**
 *
 * @author Maxime BLAISE
 */
public class GenererArbre {

    /**
     * Méthode qui génère l'AST en fonction de la ligne saisie par
     * l'utilisateur.
     *
     * @param cmd La commande
     * @return L'arbre généré.
     * @throws exception.SyntaxErrorException Erreur de syntaxe
     */
    public static Arbre genererArbreSyntaxique(String cmd) throws SyntaxErrorException {
        return new Arbre(genererNoeud(cmd));
    }

    /**
     * Méthode qui génère juste un Noeud. Gère la récursivité.
     *
     * @param line
     * @return le Noeud
     * @throws exception.SyntaxErrorException Erreur de syntaxe
     */
    public static Noeud genererNoeud(String line) throws SyntaxErrorException {
        Noeud n = null;
        
        // On split la chaîne
        String[] split = line.split(" ");
        
        // Cas simple
        if (split.length == 1) {
            return new Noeud(line);
        }
        // Cas assignation
        switch (split[1]) {
            case ":=":
            case "::=":
                n = creerNoeudAssignation(split);
                break;
        }

        return n;
    }

    public static Noeud creerNoeudAssignation(String[] split) throws SyntaxErrorException {
        // Gestion des exceptions
        if(split.length < 3) {
            throw new SyntaxErrorException();
        }
        
        // Création du Noeud
        Noeud n = new Noeud(split[1]);
        // Ajout des du premier fils (= nom de la variable)
        n.ajouterFils(new Noeud(split[0]));
        // Ajout du deuxième fils, correspond à l'expression qui suit (= reste du tableau)
        String expr = "";
        for(int i=2;i<split.length;i++) {
            expr += split[i];
        }
        n.ajouterFils(new Noeud(expr));
        // Le Noeud peut être retourné
        return n;
    }
}
