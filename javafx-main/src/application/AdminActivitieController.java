package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Activite;
import models.Enseignant;
import dao.ActiviteDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AdminActivitieController {

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

    private Enseignant enseignant;

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public void initialize() {
        for (int i = 0; i < 24; i++) {
            heureDebutHourField.getItems().add(i);
            heureFinHourField.getItems().add(i);
        }

        for (int i = 0; i < 60; i++) {
            heureDebutMinuteField.getItems().add(i);
            heureFinMinuteField.getItems().add(i);
        }

        typeField.getItems().addAll("Workshop", "Lecture", "Meeting");
    }

    @FXML
    protected void addActivite() {
        int idEnseignant = this.enseignant.getIdE();
        String type = typeField.getValue();
        String nom = nomField.getText();
        LocalDate date = dateField.getValue();
        LocalTime heureDebut = LocalTime.of(heureDebutHourField.getValue(), heureDebutMinuteField.getValue());
        LocalTime heureFin = LocalTime.of(heureFinHourField.getValue(), heureFinMinuteField.getValue());
        boolean alerte = alerteField.isSelected();

        Activite activite = new Activite(idEnseignant, type, nom, date, heureDebut, heureFin, alerte);

        ActiviteDAO activiteDAO = new ActiviteDAO();

        try {
            activiteDAO.insertActivite(activite, idEnseignant);
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: Show an error message
        }
    }

    @FXML
    private void returnToAdminHome() {
    	String depname = null;
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml"));
            Parent root = loader.load();

            AdminHomeController adminHomeController = loader.getController();
            adminHomeController.setEnseignantAndDepname(enseignant, depname);

            Scene scene = new Scene(root);
            Stage stage = (Stage) returnButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Show an error message
        } 
    }
}
