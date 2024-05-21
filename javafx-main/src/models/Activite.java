package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Activite {
		private int idAct;
	    private int idEnseignant; // New field
	    private String type;
	    private String nom;
	    private LocalDate date;
	    private LocalTime heureDebut;
	    private LocalTime heureFin;
	    private boolean alerte;

	    public Activite(int idEnseignant, String type, String nom, LocalDate date, LocalTime heureDebut, LocalTime heureFin, boolean alerte) {
	        this.idEnseignant = idEnseignant;
	        this.type = type;
	        this.nom = nom;
	        this.date = date;
	        this.heureDebut = heureDebut;
	        this.heureFin = heureFin;
	        this.alerte = alerte;
	    }
	    
	    public Activite(int idAct, int idEnseignant, String type, String nom, LocalDate date, LocalTime heureDebut, LocalTime heureFin, boolean alerte) {
	        this.idAct = idAct;
	        this.idEnseignant = idEnseignant;
	        this.type = type;
	        this.nom = nom;
	        this.date = date;
	        this.heureDebut = heureDebut;
	        this.heureFin = heureFin;
	        this.alerte = alerte;
	    }



    // Getters
    public int getIdAct() {
        return idAct;
    }

    public String getType() {
        return type;
    }

    public String getNom() {
        return nom;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public boolean isAlerte() {
        return alerte;
    }

    // Setters
    public void setIdAct(int idAct) {
        this.idAct = idAct;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public void setAlerte(boolean alerte) {
        this.alerte = alerte;
    }
    
    public int getIdEnseignant() {
        return idEnseignant;
    }

    public void setIdEnseignant(int idEnseignant) {
        this.idEnseignant = idEnseignant;
    }

    @Override
    public String toString() {
        return "Activite [idAct=" + idAct + ", type=" + type + ", nom=" + nom + ", date=" + date +
                ", heureDebut=" + heureDebut + ", heureFin=" + heureFin + ", alerte=" + alerte + "]";
    }
}
