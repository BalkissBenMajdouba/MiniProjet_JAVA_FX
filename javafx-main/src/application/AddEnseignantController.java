package application;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Enseignant;

import java.io.IOException;

import dao.EnseignantDAO;

public class AddEnseignantController {



    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField telephoneMobileField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField idDepartementField;
    @FXML
    private Button returnButton;
    
    private Enseignant ense;
    


    @FXML
    private void handleAddEnseignant() {
        try {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String telephoneMobile = telephoneMobileField.getText();
            String email = emailField.getText();
            int idDepartement = Integer.parseInt(idDepartementField.getText());

            Enseignant enseignant = new Enseignant( nom, prenom, telephoneMobile, email, idDepartement);
            EnseignantDAO enseignantDAO = new EnseignantDAO();
            enseignantDAO.insertEnseignant(enseignant);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Enseignant added successfully!");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to add Enseignant: " + e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    private void returnToAdminHome() {
    	String depname = null;
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml"));
            Parent root = loader.load();

            AdminHomeController adminHomeController = loader.getController();
            adminHomeController.setEnseignantAndDepname(ense, depname);

            Scene scene = new Scene(root);
            Stage stage = (Stage) returnButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Show an error message
        } 
    }

	public void setEnseignant(Enseignant enseignant) {
		this.ense=enseignant;
		
	}
}
