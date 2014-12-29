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
    public static void interpreterArbreSyntaxique(Arbre ast) throws SyntaxErrorException {

        System.out.println("\n*** INTERPRETATION DE L'AST ***");

        // ON RECUPÈRE LE NOEUD RACINE
        Noeud racine = ast.getRacine();

        // ON INTERPRETE LE NOEUD RACINE
        interpreterNoeud(racine);

        System.out.println("*** FIN INTERPRETATION ***");
    }

    private static void interpreterNoeud(Noeud n) {
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
                break;
            default:
                System.out.println("    --> PAS ENCORE IMPLÉMENTÉ");
                break;
        }
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
                // ON INTERPRETE LE NOEUD FAUX
                Noeud nFaux = n.getFils().get(2);
                interpreterNoeud(nFaux);
            }
        } else {
            System.out.println("Problème : Une condition doit être une valeur booléenne !");
        }

    }

    private static String trouverValeur(Noeud n) {
        String v = null;
        String vNoeud = n.getValeur();
        // SI LA VALEUR DU NOEUD EST UN ENTIER OU UN BOOLEEN, ON RETOURNERA CETTE VALEUR
        if (estEntierOuBooleen(vNoeud)) {
            v = vNoeud;
        } else {
            String[] symbolesAcceptables = {"+", "-"};
            // SI LA VALEUR DANS LE NOEUD EST UN SYMBOLE ACCEPTÉ DANS UNE ASSIGNATION, ON CONTINUE (Récursivité)
            if (Arrays.asList(symbolesAcceptables).contains(vNoeud)) {
                // ON TROUVE LA VALEUR DANS LES NOEUDS (GAUCHE & DROITE)
                Noeud nGauche = n.getFils().get(1);
                Noeud nDroite = n.getFils().get(0);
                Integer i;
                // EN FONCTION DU SYMBOLE, ON EFFECTUE LA BONNE OPÉRATION
                switch (vNoeud) {
                    case "+":
                        i = Integer.parseInt(trouverValeur(nGauche)) + Integer.parseInt(trouverValeur(nDroite));
                        v = i.toString();
                        break;
                    case "-":
                        i = Integer.parseInt(trouverValeur(nGauche)) - Integer.parseInt(trouverValeur(nDroite));
                        v = i.toString();
                        break;
                    default:
                        System.out.println("Pas encore possible ...");
                        break;
                }
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

}
