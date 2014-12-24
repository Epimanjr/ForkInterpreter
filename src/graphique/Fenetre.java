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

    /**
     * Largeur de la fenêtre.
     */
    public static int largeur = 400;

    /**
     * Hauteur de la fenêtre.
     */
    public static int hauteur = 300;
    
    public static String titre = "Projet original de Java 2014";

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();

        primaryStage.setTitle(titre);
        primaryStage.setScene(new Scene(root, largeur, hauteur));
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
}
