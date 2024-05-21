package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Enseignant;

public class AdminHomeController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private Button navigateButton1;
    @FXML
    private Button navigateButton2;
    @FXML
    private Button navigateButton3;
    @FXML
    private Button navigateButton4;

    @FXML
    private void navigateToDisplayEns(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("displayEns.fxml"));
            Parent root = loader.load();
            DisplayEnsController dis =loader.getController();
            dis.initialize(this.enseignant);
            Stage stage = (Stage) navigateButton4.getScene().getWindow(); // Assuming navigateButton5 is the button for this navigation
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Show an error message
        }
    }
    
    @FXML
    private void navigateToAddEnseignant(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addEnseignant.fxml"));
            Parent root = loader.load();
            AddEnseignantController adding = loader.getController();
            adding.setEnseignant(this.enseignant);
            Stage stage = (Stage) navigateButton3.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Show an error message
        }
    }
   
    @FXML
    private void navigateToOtherFXML(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminActivitie.fxml"));
            Parent root = loader.load();
            AdminActivitieController admin = loader.getController();
            admin.setEnseignant(this.enseignant);
            Stage stage = (Stage) navigateButton1.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Show an error message
        }
    }
    
    @FXML
    private void navigateToOtherFXMLlist(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminagenda.fxml"));
            Parent root = loader.load();
            AdminAgendaController admin = loader.getController();
            admin.setEnseignant(this.enseignant);
            Stage stage = (Stage) navigateButton2.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Show an error message
        }
    }
    
    private Enseignant enseignant;
    private String depname;

    public void setEnseignantAndDepname(Enseignant enseignant, String depname) {
        this.enseignant = enseignant;
        this.depname = depname;
        updateUI();
    }

    private void updateUI() {
        if (enseignant != null && depname != null) {
        	welcomeLabel.setText("Hello Mr. " + enseignant.getNom() + ", chef department of " + depname);
        }
        else if(enseignant != null){
        	welcomeLabel.setText("Hello Mr. " + enseignant.getNom());

        }
    }
    // TODO: Add methods to handle button clicks
}
