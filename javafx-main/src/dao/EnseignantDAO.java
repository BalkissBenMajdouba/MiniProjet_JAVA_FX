package dao;

import models.Enseignant;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnseignantDAO {
    private final Connection connection;

    public EnseignantDAO() {
        this.connection = DBUtil.getCn();
    }
    
    public List<Enseignant> getAllEnseignants() throws SQLException {
        List<Enseignant> enseignants = new ArrayList<>();
        String sql = "SELECT * FROM Enseignant";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Enseignant enseignant = new Enseignant(
                        resultSet.getInt("id_enseignant"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("telephone_mobile"),
                        resultSet.getString("email"),
                        resultSet.getInt("id_departement")
                );
                enseignants.add(enseignant);
            }
        }
        return enseignants;
    }
    
    public List<Enseignant> getAllEnseignants(int id_departement) throws SQLException {
        List<Enseignant> enseignants = new ArrayList<>();
        String sql = "SELECT * FROM Enseignant WHERE id_departement=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id_departement); // Set the id_departement parameter
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Enseignant enseignant = new Enseignant(
                            resultSet.getInt("id_enseignant"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getString("telephone_mobile"),
                            resultSet.getString("email"),
                            resultSet.getInt("id_departement")
                    );
                    enseignants.add(enseignant);
                }
            }
        }
        return enseignants;
    }

    public void insertEnseignant(Enseignant enseignant) throws SQLException {
        String sql = "INSERT INTO Enseignant (nom, prenom, telephone_mobile, email, id_departement) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, enseignant.getNom());
            statement.setString(2, enseignant.getPrenom());
            statement.setString(3, enseignant.getNumMob());
            statement.setString(4, enseignant.getEmail());
            statement.setInt(5, enseignant.getId_departement());
            statement.executeUpdate();
        }
    }

    public Enseignant getEnseignantById(int idE) throws SQLException {
        String sql = "SELECT * FROM Enseignant WHERE id_enseignant = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idE);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Enseignant(
                            resultSet.getInt("id_enseignant"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getString("telephone_mobile"),
                            resultSet.getString("email"),
                            resultSet.getInt("id_departement")
                    );
                }
            }
        }
        return null; // Not found&
    }

    public void updateEnseignantEmail(int idE, String newEmail) throws SQLException {
        String sql = "UPDATE Enseignant SET email = ? WHERE id_enseignant = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newEmail);
            statement.setInt(2, idE);
            statement.executeUpdate();
        }
    }

    public void deleteEnseignant(int idE) throws SQLException {
        String sql = "DELETE FROM Enseignant WHERE id_enseignant = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idE);
            statement.executeUpdate();
        }
    }
    
    public Enseignant getEnseignantByNameAndEmail(String name, String email) throws SQLException {
        String sql = "SELECT * FROM Enseignant WHERE nom = ? AND email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Enseignant(
                            resultSet.getInt("id_enseignant"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getString("telephone_mobile"),
                            resultSet.getString("email"),
                            resultSet.getInt("id_departement")
                    );
                }
            }
        }
        return null; // Not found
    }
    
    
    //--------------new check if a ens is chef
    public boolean isChefDepartement(int idE) throws SQLException {
        String sql = "SELECT * FROM Departement WHERE id_chef_departement = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idE);
            try (ResultSet resultSet = statement.executeQuery()) {
                // If the query returns a result, then the Enseignant is a chef_departement
                return resultSet.next();
            }
        }
    }
    
    //----------------get department 
    public String getDepartementOfEnseignant(int idE) throws SQLException {
        String sql = "SELECT nom FROM Departement WHERE id_chef_departement = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idE);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("nom");
                }
            }
        }
        return null; // Not found or not a chef_departement
    }
//update 
    public void updateEnseignant(Enseignant updatedEnseignant) throws SQLException {
        String sql = "UPDATE Enseignant SET nom = ?, prenom = ?, telephone_mobile = ?, email = ?, id_departement = ? WHERE id_enseignant = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, updatedEnseignant.getNom());
            statement.setString(2, updatedEnseignant.getPrenom());
            statement.setString(3, updatedEnseignant.getNumMob());
            statement.setString(4, updatedEnseignant.getEmail());
            statement.setInt(5, updatedEnseignant.getId_departement());
            statement.setInt(6, updatedEnseignant.getIdE());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                // Handle case where no rows were updated
                System.out.println("No rows were updated.");
            } else {
                System.out.println(rowsAffected + " row(s) updated.");
            }
        }
    }



}
