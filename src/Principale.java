
import memoire.Memoire;
import java.util.Scanner;
import arbre.*;
import exception.SyntaxErrorException;

/**
 *
 * @author Maxime
 */
public class Principale {
    
    public static void main(String[] args) throws SyntaxErrorException {
        // Permet la lecture au clavier
        Scanner sc = new Scanner(System.in);
        
        String cmd;
        while(true) {
            // Lecture de la commande
            System.out.print(">>> ");
            cmd = sc.nextLine();
            
            if(cmd.equals("EXIT")) {
                // Fermeture du programme
                System.exit(1);
            }
            
            Arbre a = GenererArbre.genererArbreSyntaxique(cmd);
            System.out.println(a.interpreterArbre());
            Memoire.afficherEtatMemoire();
            
            
            
        }
    }
}
