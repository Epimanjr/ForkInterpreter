package arbre;

import exception.SyntaxErrorException;
import interpreter.Memoire;
import interpreter.MemoiresLet;
import java.util.ArrayList;
import java.util.Arrays;
import org.omg.CORBA.MARSHAL;

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
                res = interpreterCondition(n);
                break;
            case "while":
                System.out.println("    --> BOUCLE");
                res = interpreterBoucle(n);
                break;
            case "return":
                System.out.println("    --> RETURN");
                res = interpreterReturn(n);
                break;
            case ";":
                System.out.println("    --> POINT-VIRGULE");
                res = interpreterPointVirgule(n);
                break;
            case "let":
                System.out.println("    --> LET");
                res = interpreterLet(n);
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

    private static String interpreterCondition(Noeud n) {
        String res = "";
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
                res = interpreterNoeud(nVrai);
            }

            // SI C'EST FAUX
            if (valeurCondition.equals("false")) {
                // ON INTERPRETE LE NOEUD FAUX S'IL EXISTE
                if (n.getFils().size() == 3) {
                    Noeud nFaux = n.getFils().get(2);
                    res = interpreterNoeud(nFaux);
                }
            }
        } else {
            System.out.println("Problème : Une condition doit être une valeur booléenne !");
        }
        return res;
    }

    private static String interpreterBoucle(Noeud n) {
        String res = "";
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
            res = interpreterNoeud(nVrai);
            // ON INTERPRETE A NOUVEAU LA BOUCLE (récursivité)
            res = res + interpreterBoucle(n);
        } else {
            System.out.println("Fin de boucle !");
        }
        return res;
    }

    private static String interpreterReturn(Noeud n) {
        String res = "";
        // ON INTERPRETE LE NOEUD RETURN
        Noeud nReturn = n.getFils().get(0);
        res = trouverValeur(nReturn);
        return res;
    }

    private static String interpreterPointVirgule(Noeud n) {
        String res = "";
        // ON INTERPRETE LE NOEUD GAUCHE
        Noeud nGauche = n.getFils().get(0);
        res = interpreterNoeud(nGauche);

        // ON INTERPRETE LE NOEUD DROITE
        Noeud nDroite = n.getFils().get(1);
        res = res + "\n" + interpreterNoeud(nDroite);
        return res;
    }

    private static String interpreterLet(Noeud n) {
        String res = "";
        MemoiresLet.nouvelleMemoireLet();
        if (n.getFils().size() == 3) {
            // CAS DE LA DÉCLARATION : let variable in com end
        } else {
            // CAS DE L'ALIASING : let variable1 be variable2 in com end
        }
        MemoiresLet.supprimerMemoireLetCourante();
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
