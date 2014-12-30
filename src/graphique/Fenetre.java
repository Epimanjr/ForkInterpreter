/*
 * Ceci est la fenêtre principale de l'Interface Graphique.
 * Api utilisé : Java FX.
 */
package graphique;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
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
            label.setPrefSize(Config.largeur * 8 / 10, Config.hauteurLabel);
            label.setTranslateX(5);
            label.setTranslateY(5);
            this.getChildren().add(label);
            
            // Zone de saisie des commandes
            TextField saisie = new TextField();
            saisie.setPrefSize(Config.largeur * 8 / 10 - 5, Config.hauteurSaisie);
            saisie.setTranslateX(5);
            saisie.setTranslateY(5 + label.getTranslateY() + Config.hauteurLabel);
            this.getChildren().add(saisie);

            // Liste des commandes
            ListView listeCommandes = new ListView();
            listeCommandes.setTranslateX(5);
            listeCommandes.setTranslateY(20 + saisie.getTranslateY() + Config.hauteurSaisie);
            listeCommandes.setPrefSize(Config.largeur * 4 / 10 - 5, Config.hauteur - listeCommandes.getTranslateY() - 30);
            this.getChildren().add(listeCommandes);
            
            // L'affichage des résultats
            TextArea affichage = new TextArea();
            affichage.setTranslateX(10 + listeCommandes.getPrefWidth());
            affichage.setTranslateY(listeCommandes.getTranslateY());
            affichage.setPrefSize(listeCommandes.getPrefWidth(), listeCommandes.getPrefHeight());
            this.getChildren().add(affichage);
            
            // Affichage de la mémoire
            TableView memoire = new TableView();
            memoire.setTranslateX(15 + listeCommandes.getPrefWidth() * 2);
            memoire.setTranslateY(listeCommandes.getTranslateY());
            memoire.setPrefSize(listeCommandes.getPrefWidth() / 2 - 10, listeCommandes.getPrefHeight());
            this.getChildren().add(memoire);
        }
    }
}
