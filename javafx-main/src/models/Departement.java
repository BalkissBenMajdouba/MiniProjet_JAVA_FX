package models;

public class Departement {
	 private int idDep;
	    private String nom;
	    private String adresse;
	    private Enseignant chefDep;
		public Departement(int idDep, String nom, String adresse, Enseignant chefDep) {
			super();
			this.idDep = idDep;
			this.nom = nom;
			this.adresse = adresse;
			this.chefDep = chefDep;
		}
		public int getIdDep() {
			return idDep;
		}
		public void setIdDep(int idDep) {
			this.idDep = idDep;
		}
		public String getNom() {
			return nom;
		}
		public void setNom(String nom) {
			this.nom = nom;
		}
		public String getAdresse() {
			return adresse;
		}
		public void setAdresse(String adresse) {
			this.adresse = adresse;
		}
		public Enseignant getChefDep() {
			return chefDep;
		}
		public void setChefDep(Enseignant chefDep) {
			this.chefDep = chefDep;
		}
		@Override
		public String toString() {
			return "Departement [idDep=" + idDep + ", nom=" + nom + ", adresse=" + adresse + ", chefDep=" + chefDep
					+ "]";
		}
	    
	    

}