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
        //return new Arbre(genererNoeud(cmd));
        System.out.println("\n*** INTERPRETATION DE L'AST ***");

        Noeud racine = ast.getRacine();

        switch (racine.getValeur()) {
            case ":=":
                System.out.println("    --> ASSIGNATION");
                interpreterAssignation(racine);
                break;
            default:
                System.out.println("    --> PAS ENCORE IMPLÉMENTÉ");
                break;
        }

        System.out.println("*** FIN INTERPRETATION ***");
    }

    private static void interpreterAssignation(Noeud racine) {
        // ON TROUVE LA VALEUR DANS LE NOEUD GAUCHE
        Noeud nGauche = racine.getFils().get(1);
        String valeurDeVariable = trouverValeur(nGauche);

        // ON TROUVE LE NOM DE LA VARIABLE DANS LE NOEUD DROITE
        Noeud nDroite = racine.getFils().get(0);
        String nomDeVariable = nDroite.getValeur();

        // ON ASSOCIE DANS LA MÉMOIRE, LA VALEUR À LA VARIABLE
        Memoire.ajouter(nomDeVariable, valeurDeVariable);
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
        try {
            Integer.parseInt(s);
            res = true;
        } catch (NumberFormatException nfe) {
            switch(s){
                case "true":
                    res = true;
                    break;
                case "false":
                    res = true;
                default:
                    res = false;
            }
        }
        return res;
    }

}
