package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import models.Enseignant;
import dao.EnseignantDAO;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private Button loginButton;

  

    private EnseignantDAO enseignantDAO;

    private Enseignant enseignant;
    
    public void setEnseignant(Enseignant enseignant){
    	this.enseignant=enseignant;
    }
    @FXML
    public void initialize() {
        enseignantDAO = new EnseignantDAO();
        loginButton.setOnAction(event -> handleLogin());
    }
    
    private void navigateToOtherInterface(Enseignant enseignant) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
            Parent root = loader.load();

            HomeController homeController = loader.getController();
            homeController.setEnseignant(enseignant);

            Scene scene = new Scene(root);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Show an error message
        }
    }

    private void navigateToAdminHome(Enseignant enseignant, String depname) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml"));
            Parent root = loader.load();

            AdminHomeController adminHomeController = loader.getController();
            adminHomeController.setEnseignantAndDepname(enseignant, depname);

            Scene scene = new Scene(root);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Show an error message
        }
    }

    private void handleLogin() {
        String email = emailField.getText();
        String name = nameField.getText();
        String depname="";

        try {
            Enseignant enseignant = enseignantDAO.getEnseignantByNameAndEmail(name, email);
            if (enseignant != null && enseignantDAO.isChefDepartement(enseignant.getIdE()) ) {
            	depname=enseignantDAO.getDepartementOfEnseignant(enseignant.getIdE());
            	navigateToAdminHome(enseignant, depname);
            }else if (enseignant != null ) {
            	navigateToOtherInterface(enseignant);
            } else {
                System.out.println("login invalid");
                // TODO: Show an error message
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: Show an error message
        }
    }
}

