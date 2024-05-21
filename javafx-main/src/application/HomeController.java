package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Enseignant;
import models.Departement;
import dao.EnseignantDAO;

import java.io.IOException;
import java.sql.SQLException;

import dao.DepartementDAO;

public class HomeController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private Button navigateButton;
    @FXML
    private Button navigateToActiviteButton;
    
    private EnseignantDAO enseignantDAO;
    private DepartementDAO departementDAO;

    private Enseignant enseignant;
    
    @FXML
    private void navigateToOtherFXML(ActionEvent event) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("agenda.fxml"));
            Parent root = loader.load();
            AgendaController agnd = loader.getController();
            agnd.setEnseignant(this.enseignant);
            Stage stage = (Stage) navigateToActiviteButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Show an error message
        }
    }
    @FXML
    public void navigateToActivite() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addactivitie.fxml"));
            Parent root = loader.load();
            AddActiviteController addActiviteController = loader.getController();
            addActiviteController.setEnseignant(this.enseignant);
            
            Stage stage = (Stage) navigateToActiviteButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Show an error message
        }
    }

    
    public void initialize() {
        enseignantDAO = new EnseignantDAO();
        departementDAO = new DepartementDAO();
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
        Departement departement = null;
		try {
			departement = departementDAO.getDepartementById(enseignant.getId_departement());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace() ;
		}
        welcomeLabel.setText("Hello Mr " + enseignant.getNom() + " to the " + departement.getNom()+" Department");
    }

    // Rest of your controller code...
}
