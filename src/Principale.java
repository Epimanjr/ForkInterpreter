
import interpreter.Memoire;
import java.util.Scanner;
import static tests.creationarbre.TestArbre.interpreterArbre;

/**
 *
 * @author Maxime
 */
public class Principale {
    
    public static void main(String[] args) {
        // Permet la lecture au clavier
        Scanner sc = new Scanner(System.in);
        
        String cmd;
        while(true) {
            // Lecture de la commande
            System.out.print(">>> ");
            cmd = sc.nextLine();
            
            interpreterArbre(cmd);
            Memoire.afficherEtatMemoire();
            
            if(cmd.equals("EXIT")) {
                // Fermeture du programme
                System.exit(1);
            }
            
            
        }
    }
}
