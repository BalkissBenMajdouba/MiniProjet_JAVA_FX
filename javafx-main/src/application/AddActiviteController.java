package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Activite;
import models.Enseignant;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import dao.ActiviteDAO;

public class AddActiviteController {

    @FXML
    private ComboBox<String> typeField;
    @FXML
    private TextField nomField;
    @FXML
    private DatePicker dateField;
    @FXML
    private ComboBox<Integer> heureDebutHourField;
    @FXML
    private ComboBox<Integer> heureDebutMinuteField;
    @FXML
    private ComboBox<Integer> heureFinHourField;
    @FXML
    private ComboBox<Integer> heureFinMinuteField;
    @FXML
    private CheckBox alerteField;
    @FXML
    private Button submitButton;
    @FXML
    private Button returnButton;


    private Enseignant enseignant; // You can still keep this if you're setting it from another controller
    
    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
        
        // You can update the UI here based on the enseignant object if needed
    }
    
    
    public void initialize() {
        // Populate the hour ComboBoxes with values from 0 to 23
        for (int i = 0; i < 24; i++) {
            heureDebutHourField.getItems().add(i);
            heureFinHourField.getItems().add(i);
        }

        // Populate the minute ComboBoxes with values from 0 to 59
        for (int i = 0; i < 60; i++) {
            heureDebutMinuteField.getItems().add(i);
            heureFinMinuteField.getItems().add(i);
        }

        // Populate the type ComboBox with the appropriate activity types
        typeField.getItems().addAll("Type1", "Type2", "Type3"); // Replace with your actual activity types
        
    }
    
    @FXML
    protected void addActivite() {
        int idEnseignant = this.enseignant.getIdE(); // Use idE from the enseignant object
        String type = typeField.getValue(); // Use getValue() for ComboBox
        String nom = nomField.getText();
        LocalDate date = dateField.getValue();
        LocalTime heureDebut = LocalTime.of(heureDebutHourField.getValue(), heureDebutMinuteField.getValue()); // Use getValue() for ComboBox
        LocalTime heureFin = LocalTime.of(heureFinHourField.getValue(), heureFinMinuteField.getValue()); // Use getValue() for ComboBox
        boolean alerte = alerteField.isSelected();
        System.out.println(this.enseignant);
        Activite activite = new Activite(idEnseignant, type, nom, date, heureDebut, heureFin, alerte);
        
        ActiviteDAO activiteDAO = new ActiviteDAO(); // Replace with your actual database connection
        
        try {
            activiteDAO.insertActivite(activite, idEnseignant);
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: Show an error message
        }
    }
    @FXML
    private void returnToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent root = loader.load();
            HomeController home=loader.getController();
            home.setEnseignant(enseignant);
            Stage stage = (Stage) returnButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Show an error message
        }
    }
}
