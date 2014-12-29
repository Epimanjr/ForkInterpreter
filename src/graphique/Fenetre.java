/*
 * Ceci est la fenêtre principale de l'Interface Graphique.
 * Api utilisé : Java FX.
 */
package graphique;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Maxime
 */
public class Fenetre extends Application {

    
    
    public static String titre = "Projet original de Java 2014";

    @Override
    public void start(Stage primaryStage) {
        Group root = new MainGroup();

        primaryStage.setTitle(titre);
        
        Scene scene = new Scene(root, Config.largeur, Config.hauteur);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Méthode principale qui permet de lancer l'interface graphique.
     *
     * @param args les arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    class MainGroup extends Group {
        
        public MainGroup() {
            
        }
    }
}
