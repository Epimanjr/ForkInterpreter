/*
 * Ceci est la fenêtre principale de l'Interface Graphique.
 * Api utilisé : Java FX.
 */
package graphique;

import arbre.Arbre;
import arbre.GenererArbre;
import exception.SyntaxErrorException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

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

        // Début des éléments de la page
        private final Label label;
        private final Label label1;
        private final TextField saisie;
        private final ListView listeCommandes;
        private final TextArea affichage;
        private final TableView memoire;
        private final Button exporter;
        private final Button exporterTout;
        private final Button importer;
        // Fin des éléments

        public MainGroup() {
            // Label indicatif
            label = new Label("Saisir une commande ici");
            label.setPrefSize(Config.largeur * 8 / 10, Config.hauteurLabel);
            label.setTranslateX(5);
            label.setTranslateY(5);
            this.getChildren().add(label);

            // Zone de saisie des commandes
            saisie = new TextField();
            saisie.setPrefSize(Config.largeur * 8 / 10 - 5, Config.hauteurSaisie);
            saisie.setTranslateX(5);
            saisie.setTranslateY(5 + label.getTranslateY() + Config.hauteurLabel);
            // Evenement
            saisie.setOnAction((ActionEvent event) -> {
                // Appel d'une fonction de traitement
                actionCommande(saisie.getText());
            });
            this.getChildren().add(saisie);

            label1 = new Label("Historique des commandes");
            label1.setPrefSize(Config.largeur * 4 / 10 - 5, Config.hauteurLabel);
            label1.setTranslateX(5);
            label1.setTranslateY(20 + saisie.getTranslateY() + Config.hauteurSaisie);
            this.getChildren().add(label1);

            // Liste des commandes
            listeCommandes = new ListView();
            listeCommandes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            listeCommandes.setTranslateX(5);
            listeCommandes.setTranslateY(40 + saisie.getTranslateY() + Config.hauteurSaisie);
            listeCommandes.setPrefSize(Config.largeur * 4 / 10 - 5, Config.hauteur - listeCommandes.getTranslateY() - 30);
            this.getChildren().add(listeCommandes);

            // L'affichage des résultats
            affichage = new TextArea();
            affichage.setTranslateX(10 + listeCommandes.getPrefWidth());
            affichage.setTranslateY(listeCommandes.getTranslateY());
            affichage.setPrefSize(listeCommandes.getPrefWidth(), listeCommandes.getPrefHeight());
            this.getChildren().add(affichage);

            // Affichage de la mémoire
            memoire = new TableView();
            memoire.setTranslateX(15 + listeCommandes.getPrefWidth() * 2);
            memoire.setTranslateY(listeCommandes.getTranslateY());
            memoire.setPrefSize(listeCommandes.getPrefWidth() / 2 - 10, listeCommandes.getPrefHeight());
            this.getChildren().add(memoire);

            // Importation d'une ou de plusieurs commandes
            importer = new Button("Importer");
            importer.setTranslateX(25);
            importer.setTranslateY(Config.hauteur - 27);
            importer.setOnAction((ActionEvent event) -> {
                actionImporter();
            });
            this.getChildren().add(importer);

            // Exportation d'une ou de plusieurs commandes
            exporter = new Button("Exporter");
            exporter.setTranslateX(100);
            exporter.setTranslateY(Config.hauteur - 27);
            exporter.setOnAction((ActionEvent event) -> {
                actionExporter();
            });
            this.getChildren().add(exporter);

            // Exportation de toutes les commandes
            exporterTout = new Button("Exporter tout");
            exporterTout.setTranslateX(180);
            exporterTout.setTranslateY(Config.hauteur - 27);
            exporterTout.setOnAction((ActionEvent event) -> {
                actionExporterTout();
            });
            this.getChildren().add(exporterTout);
        }

        /**
         * Méthode appelée lorsque l'utilisateur appui sur entrée.
         *
         * @param text commande (ligne obtenue)
         */
        private void actionCommande(String text) {
            if (text.equals("")) {
                System.out.println("Veuillez saisir quelque chose !");
                JOptionPane.showMessageDialog(null, "Veuillez saisir quelque chose !", "Erreur commande", JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("Exécution de : " + text);
                // Ajout à la liste des commandes
                listeCommandes.getItems().add(0, text);
                // Ajout de la commande aux résultats
                affichage.appendText(">>> " + text + "\n");
                // Exécution
                try {
                    Arbre a;
                    a = GenererArbre.genererArbreSyntaxique(text);
                    String res = a.interpreterArbre();
                    if (!res.equals("")) {
                        affichage.appendText(res + "\n");
                    }
                } catch (SyntaxErrorException ex) {
                    Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            saisie.setText("");
        }

        /**
         * Méthode appelée lorsque l'utilisateur appuie sur le bouton exporter.
         */
        private void actionExporter() {
            // On demande à l'utilisateur où souhaite-t-il l'exporter
            FileChooser fc = new FileChooser();
            fc.setTitle("Exporter vos commandes");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Péchoux FILE", "*.pechoux"));
            File file = fc.showSaveDialog(null);
            if (file != null) {
                System.out.println("*** DEBUT DE L'EXPORTATION ***");
                try {
                    PrintWriter pw = new PrintWriter(new FileWriter(file.getAbsolutePath()));

                    // Récupération des valeurs à exporter
                    Iterator it = listeCommandes.getSelectionModel().getSelectedItems().iterator();
                    while (it.hasNext()) {
                        String commande = (String) it.next();
                        System.out.println("--> " + commande);
                        pw.println(commande);
                    }

                    pw.close();
                    JOptionPane.showMessageDialog(null, "Exportation terminée", "Succès", JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException ex) {
                    Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Erreur", "Fichier incorrect", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println("*** FIN DE L'EXPORTATION ***");
        }

        /**
         * Méthode appelée lorsque l'utilisateur appuie sur le bouton importer.
         */
        private void actionImporter() {
            // On demande à l'utilisateur où souhaite-t-il l'exporter
            FileChooser fc = new FileChooser();
            fc.setTitle("Importer vos commandes");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Péchoux FILE", "*.pechoux"));
            File file = fc.showOpenDialog(null);
            if (file != null) {
                System.out.println("*** DEBUT DE L'IMPORTATION ***");
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));

                    // Récupération des valeurs à importer
                    while (br.ready()) {
                        String commande = br.readLine();
                        System.out.println(commande);
                    }

                    br.close();
                    JOptionPane.showMessageDialog(null, "Importation terminée", "Succès", JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException ex) {
                    Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Erreur", "Fichier incorrect", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println("*** FIN DE L'IMPORTATION ***");
        }

        private void actionExporterTout() {
// On demande à l'utilisateur où souhaite-t-il l'exporter
            FileChooser fc = new FileChooser();
            fc.setTitle("Exporter vos commandes");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Péchoux FILE", "*.pechoux"));
            File file = fc.showSaveDialog(null);
            if (file != null) {
                System.out.println("*** DEBUT DE L'EXPORTATION ***");
                try {
                    PrintWriter pw = new PrintWriter(new FileWriter(file.getAbsolutePath()));

                    // Récupération des valeurs à exporter
                    Iterator it = listeCommandes.getSelectionModel().getSelectedItems().iterator();
                    while (it.hasNext()) {
                        String commande = (String) it.next();
                        System.out.println("--> " + commande);
                        pw.println(commande);
                    }

                    pw.close();
                    JOptionPane.showMessageDialog(null, "Exportation terminée", "Succès", JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException ex) {
                    Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Erreur", "Fichier incorrect", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println("*** FIN DE L'EXPORTATION ***");
        }
    }
}
