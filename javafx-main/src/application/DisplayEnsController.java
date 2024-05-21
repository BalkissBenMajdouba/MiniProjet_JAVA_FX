package application;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Enseignant;
import dao.EnseignantDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DisplayEnsController {

    @FXML
    private TableView<Enseignant> tableView;
    @FXML
    private TableColumn<Enseignant, Integer> idColumn;
    @FXML
    private TableColumn<Enseignant, String> nameColumn;
    @FXML
    private TableColumn<Enseignant, String> prenomColumn;
    @FXML
    private TableColumn<Enseignant, String> numMobColumn;
    @FXML
    private TableColumn<Enseignant, String> emailColumn;
    @FXML
    private TableColumn<Enseignant, Integer> departementColumn;
    @FXML
    private TableColumn<Enseignant, Button> updateColumn;
    @FXML
    private TableColumn<Enseignant, Button> deleteColumn;
    @FXML
    private Button returnButton;
    private Enseignant ense;
    



    private ObservableList<Enseignant> enseignantList;
    private EnseignantDAO enseignantDAO;
    
    
    private void showUpdateDialog(Enseignant enseignant) {
        // Create the custom dialog
        Dialog<Enseignant> dialog = new Dialog<>();
        dialog.setTitle("Update Enseignant");
        dialog.setHeaderText("Update Enseignant Information");

        // Set the button types
        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        // Create the layout and add fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nomField = new TextField(enseignant.getNom());
        TextField prenomField = new TextField(enseignant.getPrenom());
        TextField numMobField = new TextField(enseignant.getNumMob());
        TextField emailField = new TextField(enseignant.getEmail());

        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Prenom:"), 0, 1);
        grid.add(prenomField, 1, 1);
        grid.add(new Label("Num Mob:"), 0, 2);
        grid.add(numMobField, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the nomField by default
        Platform.runLater(nomField::requestFocus);

        // Convert the result to an Enseignant object when the update button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                return new Enseignant(
                        enseignant.getIdE(),
                        nomField.getText(),
                        prenomField.getText(),
                        numMobField.getText(),
                        emailField.getText(),
                        enseignant.getId_departement()
                );
            }
            return null;
        });

        // Show the dialog and wait for the user's response
        Optional<Enseignant> result = dialog.showAndWait();

        // If the user clicked update, update the enseignant object
        result.ifPresent(updatedEnseignant -> {
            // Update the enseignant in the database and update the table view
            try {
                enseignantDAO.updateEnseignant(updatedEnseignant);
                enseignant.setNom(updatedEnseignant.getNom());
                enseignant.setPrenom(updatedEnseignant.getPrenom());
                enseignant.setNumMob(updatedEnseignant.getNumMob());
                enseignant.setEmail(updatedEnseignant.getEmail());
                tableView.refresh(); // Refresh the table view to reflect the changes
            } catch (SQLException e) {
                e.printStackTrace();
                // TODO: Show an error message
            }
        });
    }

    
    
    public void initialize(Enseignant enseignant) {
        enseignantDAO = new EnseignantDAO();
        enseignantList = FXCollections.observableArrayList();
        setEnseignant(enseignant);
        // Fetch data from the database
        try {
            List<Enseignant> enseignants = enseignantDAO.getAllEnseignants(enseignant.getId_departement());
            enseignantList.addAll(enseignants);
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: Show an error message
        }

        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdE()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        prenomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));
        numMobColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumMob()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        departementColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_departement()).asObject());

        updateColumn.setCellFactory(col -> new TableCell<Enseignant, Button>() {
            private final Button button = new Button("Update");

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    button.setOnAction(event -> {
                        Enseignant enseignant = getTableView().getItems().get(getIndex());
                        showUpdateDialog(enseignant);
                    });
                    setGraphic(button);
                }
            }
        });


        deleteColumn.setCellFactory(col -> new TableCell<Enseignant, Button>() {
            private final Button button = new Button("Delete");

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    button.setOnAction(event -> {
                        Enseignant enseignant = getTableView().getItems().get(getIndex());
                        // Handle delete logic
                        try {
                            enseignantDAO.deleteEnseignant(enseignant.getIdE());
                            enseignantList.remove(enseignant);
                            System.out.println("Delete " + enseignant.getNom());
                        } catch (SQLException e) {
                            e.printStackTrace();
                            // TODO: Show an error message
                        }
                    });
                    setGraphic(button);
                }
            }
        });

        tableView.setItems(enseignantList);
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
