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
     * Liste des opérateurs logiques.
     */
    public static String[] operateursLogiques = {
        "<",
        "<=",
        ">",
        ">="
    };

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
        // Cas assignation + opérations
        switch (split[1]) {
            case ":=":
            case "::=":
                n = creerNoeudAssignation(split);
                break;
        }

        if (n == null) // Traitement des autres cas
        {
            switch (split[0]) {
                case "if":
                    n = creerNoeudCondition(line);
                    break;
            }
        }

        if (n == null) // Traitement des opérateurs logiques
        {
            for (String operateur : operateursLogiques) {
                if (line.contains(operateur)) {
                    n = creerNoeudOperateur(line, operateur);
                }
            }
        }
        
        if (n == null) // Traitement des symboles * et / d'abord (pour la priorité)
        {
            
        }

        if (n == null) { // Erreur de syntaxe
            throw new SyntaxErrorException();
        }
        return n;
    }

    public static Noeud creerNoeudAssignation(String[] split) throws SyntaxErrorException {
        // Gestion des exceptions
        if (split.length < 3) {
            throw new SyntaxErrorException();
        }

        // Création du Noeud
        Noeud n = new Noeud(split[1]);
        // Ajout des du premier fils (= nom de la variable)
        n.ajouterFils(new Noeud(split[0]));
        // Ajout du deuxième fils, correspond à l'expression qui suit (= reste du tableau)
        String expr = split[2];
        for (int i = 3; i < split.length; i++) {
            expr += " " + split[i];
        }
        n.ajouterFils(genererNoeud(expr));
        // Le Noeud peut être retourné
        return n;
    }

    public static Noeud creerNoeudCondition(String ligne) throws SyntaxErrorException {
        // Gestion des exceptions
        if (!ligne.contains("then")) {
            throw new SyntaxErrorException();
        }

        // Split avec then
        String[] splitThen = ligne.split("then");
        // Création de la condition
        String condition = (splitThen[0].substring(3, splitThen[0].length())).trim();

        // Gestion des instructions
        String instructionThen = (splitThen[1]).trim();
        String instructionElse = "";
        // On gère maintenant si y'a une instruction else ou non
        if (ligne.contains("else")) {
            String[] splitElse = splitThen[1].split("else");
            instructionThen = (splitElse[0]).trim();
            instructionElse = (splitElse[1]).trim();
        }

        // Création du Noeud
        Noeud n = new Noeud("if");

        n.ajouterFils(genererNoeud(condition));
        n.ajouterFils(genererNoeud(instructionThen));

        if (!instructionElse.equals("")) {
            n.ajouterFils(genererNoeud(instructionElse));
        }

        // Le Noeud peut être retourné
        return n;
    }

    public static Noeud creerNoeudOperateur(String line, String operateur) throws SyntaxErrorException {
        // Gestion des exceptions
        if (line.split(" ").length < 3) {
            throw new SyntaxErrorException();
        }

        // Split
        String[] split = line.split(operateur);
        String partieGauche = (split[0]).trim();
        String partieDroite = (split[1]).trim();

        // Création du Noeud
        Noeud n = new Noeud(operateur);
        n.ajouterFils(genererNoeud(partieGauche));
        n.ajouterFils(genererNoeud(partieDroite));

        // Le Noeud peut être retourné
        return n;
    }
}
