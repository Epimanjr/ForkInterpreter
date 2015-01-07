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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import memoire.Memoire;
import memoire.Variable;

/**
 *
 * @author Maxime
 */
public class Fenetre extends Application {

    public static String titre = "TPI : The Péchoux Interpreter";

    @Override
    public void start(Stage primaryStage) {
        Group root = new MainGroup();

        primaryStage.setTitle(titre);

        Scene scene = new Scene(root, Config.largeur, Config.hauteur);
        primaryStage.setResizable(false);
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

        public int nombreCommandes = 0;
        public int indiceCommande = -1;
        public int indiceCommandeSauve = -1;

        // Début des éléments de la page
        private final Label label;
        private final Label label1;
        private final Label label2;
        private final Label label3;
        private final TextField saisie;
        private final ListView listeCommandes;
        private final TextArea affichage;
        private final TableView memoire;
        private final Button exporter;
        private final Button exporterTout;
        private final Button importer;
        private final Button vider;
        // Fin des éléments

        public MainGroup() {
            // Label indicatif
            label = new Label("Saisir une commande ici (↑ et ↓ pour naviguer l'historique)");
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
            saisie.setOnKeyPressed((KeyEvent event) -> {
                if (event.getCode().toString().equals("UP")) {
                    // On affiche la commande précédente
                    afficherCommandePrecedente();
                } else if (event.getCode().toString().equals("DOWN")) {
                    // On affiche la commande suivante
                    afficherCommandeSuivante();
                } else {
                    indiceCommande = -1;
                }
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
            listeCommandes.setPrefSize(Config.largeur * 4 / 10 - 5, Config.hauteur - listeCommandes.getTranslateY() - 150);
            this.getChildren().add(listeCommandes);

            label2 = new Label("Historique des résultats");
            label2.setPrefSize(Config.largeur * 4 / 10 - 5, Config.hauteurLabel);
            label2.setTranslateX(10 + listeCommandes.getPrefWidth());
            label2.setTranslateY(20 + saisie.getTranslateY() + Config.hauteurSaisie);
            this.getChildren().add(label2);

            // L'affichage des résultats
            affichage = new TextArea();
            affichage.setTranslateX(10 + listeCommandes.getPrefWidth());
            affichage.setTranslateY(listeCommandes.getTranslateY());
            affichage.setPrefSize(listeCommandes.getPrefWidth(), listeCommandes.getPrefHeight() + 120);
            this.getChildren().add(affichage);

            label3 = new Label("Mémoire");
            label3.setPrefSize(Config.largeur * 4 / 10 - 5, Config.hauteurLabel);
            label3.setTranslateX(15 + listeCommandes.getPrefWidth() * 2);
            label3.setTranslateY(20 + saisie.getTranslateY() + Config.hauteurSaisie);
            this.getChildren().add(label3);

            // Affichage de la mémoire
            memoire = new TableView();
            memoire.setEditable(false);
            // Ajout des colonnes
            TableColumn nomCol = new TableColumn("Nom");
            nomCol.setCellValueFactory(
                    new PropertyValueFactory<>("Nom"));
            TableColumn valeurCol = new TableColumn("Valeur");
            valeurCol.setCellValueFactory(
                    new PropertyValueFactory<>("Valeur"));
            memoire.getColumns().addAll(nomCol, valeurCol);
            memoire.setTranslateX(15 + listeCommandes.getPrefWidth() * 2);
            memoire.setTranslateY(listeCommandes.getTranslateY());
            memoire.setPrefSize(listeCommandes.getPrefWidth() / 2, listeCommandes.getPrefHeight() + 120);
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
            exporter = new Button("Exporter sélection");
            exporter.setTranslateX(100);
            exporter.setTranslateY(Config.hauteur - 27);
            exporter.setOnAction((ActionEvent event) -> {
                actionExporter();
            });
            this.getChildren().add(exporter);

            // Exportation de toutes les commandes
            exporterTout = new Button("Exporter tout");
            exporterTout.setTranslateX(220);
            exporterTout.setTranslateY(Config.hauteur - 27);
            exporterTout.setOnAction((ActionEvent event) -> {
                actionExporterTout();
            });
            this.getChildren().add(exporterTout);

            vider = new Button("Vider");
            vider.setTranslateX(600);
            vider.setTranslateY(Config.hauteur - 27);
            vider.setOnAction((ActionEvent event) -> {
                actionVider();
            });
            this.getChildren().add(vider);

            // load the image
            Image image = new Image("tpi.jpg");

            // simple displays ImageView the image as is
            ImageView iv1 = new ImageView();
            iv1.setTranslateX(110);
            iv1.setTranslateY(Config.hauteur - 145);
            iv1.setImage(image);
            this.getChildren().add(iv1);
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
                if(text.equals("exit") || text.equals("EXIT")) {
                    System.exit(0);
                }
                System.out.println("Exécution de : " + text);
                // Execution de la commande
                executerCommande(text);
                // Affichage de la mémoire

                ArrayList<Variable> liste = new ArrayList<>();
                Set cles = Memoire.getMemoire().keySet();
                Iterator it = cles.iterator();
                while (it.hasNext()) {
                    String cle = (String) it.next(); // tu peux typer plus finement ici
                    String valeur = Memoire.getMemoire().get(cle); // tu peux typer plus finement ici
                    liste.add(new Variable(cle, valeur));
                }
                ObservableList<Variable> data = FXCollections.observableArrayList(liste);
                memoire.setItems(data);

                // Incrémentation du nombre de commandes
                nombreCommandes++;
            }
            saisie.setText("");
        }

        private File recupererFichier() {
            FileChooser fc = new FileChooser();
            fc.setTitle("Exporter vos commandes");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Péchoux FILE", "*.pechoux"));
            File file = fc.showSaveDialog(null);
            return file;
        }

        private void ecrireAvecIterateur(File file, Iterator it) {
            if (file != null) {
                System.out.println("*** DEBUT DE L'EXPORTATION ***");
                try {
                    PrintWriter pw = new PrintWriter(new FileWriter(file.getAbsolutePath()));

                    ArrayList<String> listeCommandesTemporaires = new ArrayList<>();
                    // Récupération des valeurs à exporter
                    while (it.hasNext()) {

                        listeCommandesTemporaires.add((String) it.next());
                        System.out.println("Bonjour");
                    }

                    System.out.println("Taille de la liste à exporter : " + listeCommandesTemporaires.size());
                    for (int i = listeCommandesTemporaires.size() - 1; i >= 0; i--) {
                        String commande = listeCommandesTemporaires.get(i);
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
         * Méthode appelée lorsque l'utilisateur appuie sur le bouton exporter.
         */
        private void actionExporter() {
            // On demande à l'utilisateur où souhaite-t-il l'exporter
            File file = recupererFichier();
            Iterator it = listeCommandes.getSelectionModel().getSelectedItems().iterator();
            ecrireAvecIterateur(file, it);
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
                        executerCommande(commande);
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
            File file = recupererFichier();
            Iterator it = listeCommandes.getItems().iterator();
            ecrireAvecIterateur(file, it);
        }

        private void executerCommande(String text) {
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

        private void actionVider() {
            System.out.println("Vidage de l'écran !");
            // Vidage de la liste des commandes
            ObservableList<String> names = FXCollections.observableArrayList("");
            listeCommandes.setItems(names);
            // Vidage des résultats
            affichage.setText("");
            // Vidage de la mémoire
        }

        private void afficherCommande() {
            if (indiceCommande < nombreCommandes && indiceCommande >= 0) {
                // On peut alors accéder à la commande
                Object commande = listeCommandes.getItems().get(indiceCommande);
                saisie.setText(commande.toString());

                // Algo de sélection dans la liste
                for (int i = 0; i < nombreCommandes; i++) {
                    if (i == indiceCommande) {
                        listeCommandes.getSelectionModel().select(i);

                    } else {
                        listeCommandes.getSelectionModel().clearSelection(i);

                    }
                }
            } else {
                // On a atteint la limite, on ne fait rien
                indiceCommande = indiceCommandeSauve;
            }
        }

        private void afficherCommandePrecedente() {
            indiceCommandeSauve = indiceCommande;
            indiceCommande++;
            afficherCommande();
        }

        private void afficherCommandeSuivante() {
            indiceCommandeSauve = indiceCommande;
            indiceCommande--;
            afficherCommande();
        }

    }
}
