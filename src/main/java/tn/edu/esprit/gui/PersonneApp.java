package tn.edu.esprit.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.edu.esprit.entites.Personne;
import tn.edu.esprit.services.ServicePersonne;
import javafx.beans.property.SimpleStringProperty;

public class PersonneApp extends Application {
    private ServicePersonne servicePersonne = new ServicePersonne();
    private TableView<Personne> tableView = new TableView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestion des Personnes");

        TableColumn<Personne, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));

        TableColumn<Personne, String> prenomCol = new TableColumn<>("Prénom");
        prenomCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));

        tableView.getColumns().addAll(nomCol, prenomCol);
        loadTableData();

        TextField nomField = new TextField();
        nomField.setPromptText("Nom");
        TextField prenomField = new TextField();
        prenomField.setPromptText("Prénom");

        Button addButton = new Button("Ajouter");
        addButton.setOnAction(e -> {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            if (!nom.isEmpty() && !prenom.isEmpty()) {
                servicePersonne.ajouter(new Personne(nom, prenom));
                loadTableData();
                nomField.clear();
                prenomField.clear();
            }
        });

        Button deleteButton = new Button("Supprimer");
        deleteButton.setOnAction(e -> {
            Personne selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                servicePersonne.supprimer(selected.getId());
                loadTableData();
            }
        });

        Button updateButton = new Button("Modifier");
        updateButton.setOnAction(e -> {
            Personne selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selected.setNom(nomField.getText());
                selected.setPrenom(prenomField.getText());
                servicePersonne.modifier(selected);
                loadTableData();
                nomField.clear();
                prenomField.clear();
            }
        });

        VBox vbox = new VBox(10, tableView, nomField, prenomField, addButton, updateButton, deleteButton);
        vbox.setPadding(new Insets(10));

        primaryStage.setScene(new Scene(vbox, 400, 500));
        primaryStage.show();
    }

    private void loadTableData() {
        ObservableList<Personne> personnes = FXCollections.observableArrayList(servicePersonne.getAll());
        tableView.setItems(personnes);
    }
}
