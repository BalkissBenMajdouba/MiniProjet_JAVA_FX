package models;

public class Enseignant {
	private int idE;
    private String nom;
    private String prenom;
    private String numMob;
    private String email;
    private int id_departement;
    
    public Enseignant(int idE, String nom, String prenom, String numMob, String email, int id_departement) {
        super();
        this.idE = idE;
        this.nom = nom;
        this.prenom = prenom;
        this.numMob = numMob;
        this.email = email;
        this.id_departement = id_departement; // Initialize new field
    }
    
    public Enseignant( String nom, String prenom, String numMob, String email, int id_departement) {
        super();
        this.nom = nom;
        this.prenom = prenom;
        this.numMob = numMob;
        this.email = email;
        this.id_departement = id_departement; // Initialize new field
    }
    
	public int getIdE() {
		return idE;
	}
	public void setIdE(int idE) {
		this.idE = idE;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNumMob() {
		return numMob;
	}
	public void setNumMob(String numMob) {
		this.numMob = numMob;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId_departement() {
        return id_departement;
    }

    public void setId_departement(int id_departement) {
        this.id_departement = id_departement;
    }
    @Override
    public String toString() {
        return "Enseignant [idE=" + idE + ", nom=" + nom + ", prenom=" + prenom + ", numMob=" + numMob + ", email="
                + email + ", id_departement=" + id_departement + "]"; // Include new field in toString
    }
	

}
