package dao;



import models.Departement;
import models.Enseignant;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DepartementDAO {
    private final Connection connection; // Inject your database connection here

    public DepartementDAO( ) {
    	this.connection = DBUtil.getCn();
    	}

    // Insert a new department into the database
    public void insertDepartement(Departement departement) throws SQLException {
        String sql = "INSERT INTO Departement (nom, adresse, id_chef_departement) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, departement.getNom());
            statement.setString(2, departement.getAdresse());
            statement.setInt(3, departement.getChefDep().getIdE()); // Assuming chefDep is an Enseignant object
            statement.executeUpdate();
        }
    }

    // Retrieve a department by ID
    public Departement getDepartementById(int idDep) throws SQLException {
        String sql = "SELECT * FROM Departement WHERE id_departement = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idDep);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int chefId = resultSet.getInt("id_chef_departement");
                    Enseignant chefDep = getEnseignantById(chefId); // Retrieve the associated chef
                    return new Departement(
                            resultSet.getInt("id_departement"),
                            resultSet.getString("nom"),
                            resultSet.getString("adresse"),
                            chefDep
                    );
                }
            }
        }
        return null; // Not found
    }

    // Update department information (e.g., address)
    public void updateDepartementAddress(int idDep, String newAddress) throws SQLException {
        String sql = "UPDATE Departement SET adresse = ? WHERE id_departement = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newAddress);
            statement.setInt(2, idDep);
            statement.executeUpdate();
        }
    }

    // Delete a department by ID
    public void deleteDepartement(int idDep) throws SQLException {
        String sql = "DELETE FROM Departement WHERE id_departement = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idDep);
            statement.executeUpdate();
        }
    }

    // Helper method to retrieve an Enseignant by ID
    private Enseignant getEnseignantById(int idE) throws SQLException {
        // Implement this method to retrieve an Enseignant from the database
        // You can use EnseignantDAO or any other method you prefer
        // Return the Enseignant object
        // Example: return new Enseignant(idE, nom, prenom, numMob, email);
        return null; // Placeholder
    }
}

