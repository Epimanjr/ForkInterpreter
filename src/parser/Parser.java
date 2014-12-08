/*
 * On génère un parser à chaque commande entré par l'utilisateur.
 * Le but : 
 *      -> Spliter la chaîne correctement
 *      -> Et générer l'arbre syntaxique correspondant.
 */
package parser;

/**
 *
 * @author Maxime BLAISE
 */
public class Parser {
    
    // Attribut qui gère l'arbre syntaxique
    
    
    /**
     * Constructeur privé.
     * Car on a besoin que d'un seul parser, donc on utilise le SingleTon.
     */
    private Parser() {
        
    }
    
    /**
     * Unique instance du parser.
     */
    private static final Parser instance = new Parser();
    
    /**
     * Méthode qui permet de récupérer le parser à partir d'une autre classe.
     * 
     * @return le parser.
     */
    public static Parser getParser() {
        return Parser.instance;
    }
    
    /**
     * Méthode qui génère l'AST en fonction de la ligne saisie par l'utilisateur.
     * 
     * @param ligne ligne lu au clavier.
     */
    public static void genererArbreSyntaxique(String ligne) {
        
    }
}
