package arbre;

import exception.SyntaxErrorException;

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
        
        switch(racine.getValeur()) {
            case ":=":
                System.out.println("    --> ASSIGNATION");
            default:
                System.out.println("    --> PAS ENCORE IMPLÉMENTÉ");
        }
        
        System.out.println("*** FIN INTERPRETATION ***");
    }
    
}
