/*
 * Ceci est la fenêtre principale de l'Interface Graphique.
 * Api utilisé : Java FX.
 */
package graphique;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
            // Label indicatif
            Label label = new Label("Saisir une commande ici");
            label.setPrefSize(Config.largeur - 10, 12);
            label.setTranslateX(5);
            label.setTranslateY(5);
            this.getChildren().add(label);
            
            // Zone de saisie des commandes
            TextField saisie = new TextField();
            saisie.setPrefSize(Config.largeur - 10, 12);
            saisie.setTranslateX(5);
            saisie.setTranslateY(25);
            this.getChildren().add(saisie);
            
            
        }
    }
}
