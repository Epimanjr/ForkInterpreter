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
        Noeud nGauche = racine.getFils().get(0);
        String valeurDeVariable = trouverValeur(nGauche);

        // ON TROUVE LE NOM DE LA VARIABLE DANS LE NOEUD DROITE
        Noeud nDroite = racine.getFils().get(1);
        String nomDeVariable = nDroite.getValeur();

	// ON ASSOCIE DANS LA MÉMOIRE, LA VALEUR À LA VARIABLE
        Memoire.ajouter(nomDeVariable,valeurDeVariable);
    }

    private static String trouverValeur(Noeud n) {
        String v = null;
        // SI LA VALEUR DU NOEUD EST UN ENTIER OU UN BOOLEEN, ON RETOURNERA CETTE VALEUR
        if(estEntierOuBooleen(n.getValeur())) {
            v = n.getValeur();
        } else {
            String[] symbolesAcceptables = {"+", "-"};
            // SI LA VALEUR DANS LE NOEUD EST UN SYMBOLE ACCEPTÉ DANS UNE ASSIGNATION
            if(Arrays.asList(symbolesAcceptables).contains(n.getValeur())) {
                
            } else {
                System.out.println("Erreur de syntaxe");
            }
        }
        return v;
    }
    
    public static boolean estEntierOuBooleen(String s) {
        return true;
    }


}
