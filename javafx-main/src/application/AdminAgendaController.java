
package application;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Activite;
import models.Enseignant;
import dao.ActiviteDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class AdminAgendaController {
 @FXML
 private TableView<Activite> agendaTable;
 @FXML
 private TableColumn<Activite, Integer> idActivitieColumn;
 @FXML
 private TableColumn<Activite, String> typeColumn;
 @FXML
 private TableColumn<Activite, String> nomColumn;
 @FXML
 private TableColumn<Activite, LocalDate> dateColumn;
 @FXML
 private TableColumn<Activite, LocalTime> heureDebutColumn;
 @FXML
 private TableColumn<Activite, LocalTime> heureFinColumn;
 @FXML
 private TableColumn<Activite, Boolean> alerteColumn;
 @FXML
 private TableColumn<Activite, Void> deleteColumn;
 @FXML
 private TableColumn<Activite, Void> updateColumn;
 @FXML
 private Button returnButton;


 private Enseignant enseignant;
 private ActiviteDAO activiteDAO;

 public void setEnseignant(Enseignant enseignant) {
     this.enseignant = enseignant;
     
     if (enseignant != null) {
         try {
             agendaTable.getItems().addAll(activiteDAO.getAllActivitiesForEnseignant(this.enseignant.getIdE()));
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
 }

 public void initialize() {
     activiteDAO = new ActiviteDAO();

     idActivitieColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdAct()).asObject());
     typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
     nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
     dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));
     heureDebutColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getHeureDebut()));
     heureFinColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getHeureFin()));
     alerteColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isAlerte()));

     deleteColumn.setCellFactory(param -> new TableCell<>() {
         private final Button deleteButton = new Button("Delete");

         {
             deleteButton.setOnAction(event -> {
                 Activite activite = getTableView().getItems().get(getIndex());
                 System.out.println(activite);
                 try {
                     // Delete the activite from the database
                     activiteDAO.deleteActivite(activite.getIdAct());
                     // Remove the activite from the TableView
                     agendaTable.getItems().remove(activite);
                 } catch (SQLException e) {
                     e.printStackTrace();
                     // TODO: Show an error message
                 };
             });
         }

         @Override
         protected void updateItem(Void item, boolean empty) {
             super.updateItem(item, empty);

             if (empty) {
                 setGraphic(null);
             } else {
                 setGraphic(deleteButton);
             }
         }
     });

     updateColumn.setCellFactory(param -> new TableCell<>() {
         private final Button updateButton = new Button("Update");

         {
         
         	updateButton.setOnAction(event -> {
         	    Activite activite = getTableView().getItems().get(getIndex());

         	    // Create a new Dialog
         	    Dialog<Activite> dialog = new Dialog<>();
         	    dialog.setTitle("Update Activite");
         	    dialog.setHeaderText("Enter the new details for the Activite");

         	    // Create labels and fields for the Activite's details
         	    Label typeLabel = new Label("Type: ");
         	    TextField typeField = new TextField(activite.getType());
         	    Label nomLabel = new Label("Nom: ");
         	    TextField nomField = new TextField(activite.getNom());
         	    Label dateLabel = new Label("Date: ");
         	    DatePicker dateField = new DatePicker(activite.getDate());
         	    Label heureDebutLabel = new Label("Heure Debut: ");
         	    TextField heureDebutField = new TextField(activite.getHeureDebut().toString());
         	    Label heureFinLabel = new Label("Heure Fin: ");
         	    TextField heureFinField = new TextField(activite.getHeureFin().toString());
         	    // Add more labels and fields as needed...

         	    // Create a grid for the labels and fields
         	    GridPane grid = new GridPane();
         	    grid.add(typeLabel, 1, 1);
         	    grid.add(typeField, 2, 1);
         	    grid.add(nomLabel, 1, 2);
         	    grid.add(nomField, 2, 2);
         	    grid.add(dateLabel, 1, 3);
         	    grid.add(dateField, 2, 3);
         	    grid.add(heureDebutLabel, 1, 4);
         	    grid.add(heureDebutField, 2, 4);
         	    grid.add(heureFinLabel, 1, 5);
         	    grid.add(heureFinField, 2, 5);
         	    // Add more items to the grid as needed...

         	    // Add the grid to the Dialog
         	    dialog.getDialogPane().setContent(grid);

         	    // Add a button to the Dialog
         	    ButtonType buttonTypeOk = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
         	    dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

         	    // When the Dialog is closed, update the Activite with the new details
         	    dialog.setResultConverter(dialogButton -> {
         	        if (dialogButton == buttonTypeOk) {
         	            activite.setType(typeField.getText());
         	            activite.setNom(nomField.getText());
         	            activite.setDate(dateField.getValue());
         	            activite.setHeureDebut(LocalTime.parse(heureDebutField.getText()));
         	            activite.setHeureFin(LocalTime.parse(heureFinField.getText()));
         	            // Update more fields as needed...
         	            return activite;
         	        }
         	        return null;
         	    });

         	    // Show the Dialog and get the updated Activite
         	    Optional<Activite> result = dialog.showAndWait();
         	    result.ifPresent(updatedActivite -> {
         	        try {
         	            // Update the activite in the database
         	            activiteDAO.updateActivite(updatedActivite);
         	            // Update the activite in the TableView
         	            agendaTable.getItems().set(getIndex(), updatedActivite);
         	        } catch (SQLException e) {
         	            e.printStackTrace();
         	            // TODO: Show an error message
         	        }
         	    });
         	});


         }

         @Override
         protected void updateItem(Void item, boolean empty) {
             super.updateItem(item, empty);

             if (empty) {
                 setGraphic(null);
             } else {
                 setGraphic(updateButton);
             }
         }
     });

     if (enseignant != null) {
         try {
             agendaTable.getItems().addAll(activiteDAO.getAllActivitiesForEnseignant(this.enseignant.getIdE()));
         } catch (SQLException e) {
             e.printStackTrace();
         }
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
