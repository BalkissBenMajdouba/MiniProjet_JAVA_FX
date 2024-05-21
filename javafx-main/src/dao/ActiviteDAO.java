package dao;

import models.Activite;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class ActiviteDAO {
    private final Connection connection; // Inject your database connection here

    public ActiviteDAO() {
        this.connection = DBUtil.getCn();
    }

 // Insert a new activity (Activite) into the database-------------------------------OK
    public void insertActivite(Activite activite, int idEnseignant) throws SQLException {
        String sql = "INSERT INTO Activite (id_enseignant, type_activite, nom_activite, date, heure_debut, heure_fin, alerte) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idEnseignant);
            statement.setString(2, activite.getType());
            statement.setString(3, activite.getNom());
            statement.setDate(4, java.sql.Date.valueOf(activite.getDate()));
            statement.setTime(5, java.sql.Time.valueOf(activite.getHeureDebut()));
            statement.setTime(6, java.sql.Time.valueOf(activite.getHeureFin()));
            statement.setBoolean(7, activite.isAlerte());
            statement.executeUpdate();
        }
    }

    public List<Activite> getAllActivitiesForEnseignant(int idEnseignant) throws SQLException {
        List<Activite> activities = new ArrayList<>();
        String sql = "SELECT * FROM Activite WHERE id_enseignant = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idEnseignant);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                	System.out.println(resultSet.getInt("id_activite"));
                	activities.add(new Activite(
                		    resultSet.getInt("id_activite"), // Get id_activite from the result set
                		    resultSet.getInt("id_enseignant"),
                		    resultSet.getString("type_activite"),
                		    resultSet.getString("nom_activite"),
                		    resultSet.getDate("date").toLocalDate(),
                		    resultSet.getTime("heure_debut").toLocalTime(),
                		    resultSet.getTime("heure_fin").toLocalTime(),
                		    resultSet.getBoolean("alerte")
                		));

                }
            }
        }
        return activities;
    }
    
    


    public void updateActivite(Activite activite) throws SQLException {
        String sql = "UPDATE Activite SET type_activite = ?, nom_activite = ?, date = ?, heure_debut = ?, heure_fin = ?, alerte = ? WHERE id_activite = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, activite.getType());
            statement.setString(2, activite.getNom());
            statement.setDate(3, java.sql.Date.valueOf(activite.getDate()));
            statement.setTime(4, java.sql.Time.valueOf(activite.getHeureDebut()));
            statement.setTime(5, java.sql.Time.valueOf(activite.getHeureFin()));
            statement.setBoolean(6, activite.isAlerte());
            statement.setInt(7, activite.getIdAct());
            statement.executeUpdate();
        }
    }



    // Delete an activity by ID
    public void deleteActivite(int idActivite) throws SQLException {
        String sql = "DELETE FROM Activite WHERE id_activite = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idActivite);
            statement.executeUpdate();
        }
    }
}
