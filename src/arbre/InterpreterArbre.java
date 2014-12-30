package arbre;

import exception.SyntaxErrorException;
import interpreter.Memoire;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Antoine NOSAL
 */
public class InterpreterArbre {

    /**
     * Méthode qui interprète un AST en paramètre.
     *
     * @param ast l'AST
     * @throws exception.SyntaxErrorException Erreur de syntaxe
     */
    public static String interpreterArbreSyntaxique(Arbre ast) throws SyntaxErrorException {

        String res = "";

        // ON RECUPÈRE LE NOEUD RACINE
        Noeud racine = ast.getRacine();

        // ON INTERPRETE LE NOEUD RACINE
        res = interpreterNoeud(racine);

        return res;
    }

    private static String interpreterNoeud(Noeud n) {
        String res = "";
        // ON SWITCH SUR LA VALEUR DU NOEUD POUR CHOISIR LA BONNE METHODE D'INTERPRETATION
        switch (n.getValeur()) {
            case ":=":
                System.out.println("    --> ASSIGNATION");
                interpreterAssignation(n);
                break;
            case "if":
                System.out.println("    --> CONDITION");
                interpreterCondition(n);
                break;
            case "while":
                System.out.println("    --> BOUCLE");
                interpreterBoucle(n);
                break;
            case "return":
                System.out.println("    ---> RETURN");
                res = interpreterReturn(n);
                break;
            default:
                System.out.println("    --> PAS ENCORE IMPLÉMENTÉ");
                break;
        }
        return res;
    }

    private static void interpreterAssignation(Noeud n) {
        // ON TROUVE LA VALEUR DANS LE NOEUD GAUCHE
        Noeud nGauche = n.getFils().get(1);
        String valeurDeVariable = trouverValeur(nGauche);

        // ON TROUVE LE NOM DE LA VARIABLE DANS LE NOEUD DROITE
        Noeud nDroite = n.getFils().get(0);
        String nomDeVariable = nDroite.getValeur();

        // ON ASSOCIE DANS LA MÉMOIRE, LA VALEUR À LA VARIABLE
        Memoire.ajouter(nomDeVariable, valeurDeVariable);
    }

    private static void interpreterCondition(Noeud n) {
        // ON TROUVE LA VALEUR DU NOEUD DE LA CONDITION
        Noeud nCondition = n.getFils().get(0);
        String valeurCondition = trouverValeur(nCondition);

        // SI LA MEMOIRE CONTIENT UNE VARIABLE DE CE NOM, ON RECUPERE SA VALEUR
        if (Memoire.getMemoire().containsKey(valeurCondition)) {
            valeurCondition = Memoire.getMemoire().get(valeurCondition);
        }

        // SI LA CONDITION EST BIEN, AU FINAL, TRUE OU FALSE
        if ((valeurCondition.equals("true")) || (valeurCondition.equals("false"))) {
            // SI C'EST VRAI
            if (valeurCondition.equals("true")) {
                // ON INTERPRETE LE NOEUD VRAI
                Noeud nVrai = n.getFils().get(1);
                interpreterNoeud(nVrai);
            }

            // SI C'EST FAUX
            if (valeurCondition.equals("false")) {
                // ON INTERPRETE LE NOEUD FAUX S'IL EXISTE
                if (n.getFils().size() == 3) {
                    Noeud nFaux = n.getFils().get(2);
                    interpreterNoeud(nFaux);
                }
            }
        } else {
            System.out.println("Problème : Une condition doit être une valeur booléenne !");
        }

    }

    private static void interpreterBoucle(Noeud n) {
        // ON TROUVE LA VALEUR DU NOEUD DE LA CONDITION DE LA BOUCLE
        Noeud nCondition = n.getFils().get(0);
        String valeurCondition = trouverValeur(nCondition);

        // SI LA MEMOIRE CONTIENT UNE VARIABLE DE CE NOM, ON RECUPERE SA VALEUR
        if (Memoire.getMemoire().containsKey(valeurCondition)) {
            valeurCondition = Memoire.getMemoire().get(valeurCondition);
        }

        // SI LA CONDITION DE LA BOUCLE EST BIEN, AU FINAL, TRUE
        if (valeurCondition.equals("true")) {
            // ON INTERPRETE LE NOEUD VRAI
            Noeud nVrai = n.getFils().get(1);
            interpreterNoeud(nVrai);
            // ON INTERPRETE A NOUVEAU LA BOUCLE (récursivité)
            interpreterBoucle(n);
        } else {
            System.out.println("Fin de boucle !");
        }

    }

    private static String interpreterReturn(Noeud n) {
        String res = "";
        // ON INTERPRETE LE NOEUD RETURN
        Noeud nReturn = n.getFils().get(0);
        res = trouverValeur(nReturn);        
        return res;
    }

    private static String trouverValeur(Noeud n) {
        String v = null;
        String vNoeud = n.getValeur();
        // SI LA VALEUR DU NOEUD EST UN ENTIER OU UN BOOLEEN, ON RETOURNERA CETTE VALEUR
        if (estEntierOuBooleen(vNoeud)) {
            v = vNoeud;
        } else if (estEnMemoire(vNoeud)) {
            v = valeurEnMemoire(vNoeud);
        } else {
            String[] symbolesAcceptables = {"+", "-", "*", "/", ">", "<", "="};
            // SI LA VALEUR DANS LE NOEUD EST UN SYMBOLE ACCEPTÉ, ON CONTINUE (récursivité)
            if (Arrays.asList(symbolesAcceptables).contains(vNoeud)) {
                // EN FONCTION DU SYMBOLE, ON EFFECTUE LA BONNE OPÉRATION
                v = faireOperation(n, vNoeud);
            } else {
                System.out.println("Erreur de syntaxe");
            }
        }
        return v;
    }

    public static boolean estEntierOuBooleen(String s) {
        boolean res = false;
        switch (s) {
            case "true":
                res = true;
                break;
            case "false":
                res = true;
                break;
            default:
                res = false;
                break;
        }
        try {
            Integer.parseInt(s);
            res = true;
        } catch (NumberFormatException nfe) {
        }
        return res;
    }

    private static boolean estEnMemoire(String s) {
        boolean res = false;
        res = Memoire.getMemoire().containsKey(s);
        return res;
    }

    private static String valeurEnMemoire(String s) {
        String res = "";
        res = Memoire.getMemoire().get(s);
        return res;
    }

    private static String faireOperation(Noeud n, String vNoeud) {
        Integer i;
        Boolean b;
        String res = "";

        // ON TROUVE LA VALEUR DANS LES NOEUDS (GAUCHE & DROITE)
        Noeud nGauche = n.getFils().get(1);
        Noeud nDroite = n.getFils().get(0);

        // ON SWITCH SUR LA VALEUR DU NOEUD POUR FAIRE LA BONNE OPERATION
        switch (vNoeud) {
            // ADDITION
            case "+":
                i = Integer.parseInt(trouverValeur(nDroite)) + Integer.parseInt(trouverValeur(nGauche));
                res = i.toString();
                break;
            // SOUSTRACTION
            case "-":
                i = Integer.parseInt(trouverValeur(nDroite)) - Integer.parseInt(trouverValeur(nGauche));
                res = i.toString();
                break;
            // MULTIPLICATION
            case "*":
                i = Integer.parseInt(trouverValeur(nDroite)) * Integer.parseInt(trouverValeur(nGauche));
                res = i.toString();
                break;
            // DIVISION
            case "/":
                i = Integer.parseInt(trouverValeur(nDroite)) / Integer.parseInt(trouverValeur(nGauche));
                res = i.toString();
                break;
            // SUPERIORITE STRICTE
            case ">":
                b = Integer.parseInt(trouverValeur(nDroite)) > Integer.parseInt(trouverValeur(nGauche));
                res = b.toString();
                break;
            // SUPERIORITE OU EGALITE
            case ">=":
                b = Integer.parseInt(trouverValeur(nDroite)) >= Integer.parseInt(trouverValeur(nGauche));
                res = b.toString();
                break;
            // INFERIORITE STRICTE
            case "<":
                b = Integer.parseInt(trouverValeur(nDroite)) < Integer.parseInt(trouverValeur(nGauche));
                res = b.toString();
                break;
            // INFERIORITE OU EGALITE
            case "<=":
                b = Integer.parseInt(trouverValeur(nDroite)) <= Integer.parseInt(trouverValeur(nGauche));
                res = b.toString();
                break;
            // EGALITE
            case "=":
                b = Integer.parseInt(trouverValeur(nDroite)) == Integer.parseInt(trouverValeur(nGauche));
                res = b.toString();
                break;
            default:
                System.out.println("Pas encore possible ...");
                break;
        }

        return res;
    }

}
