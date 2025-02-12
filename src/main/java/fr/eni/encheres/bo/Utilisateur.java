package fr.eni.encheres.bo;

import java.time.LocalDate;
import java.util.Objects;

import fr.eni.encheres.exceptions.BusinessCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Utilisateur {
	@NotBlank
	@Size(max=30)
	private String pseudo;
	@NotBlank
	@Size(max=40)
	private String nom;
	@NotBlank
	@Size(max=50)
	private String prenom;
	@NotBlank
	@Size(max=100)
	@Email
	private String email;
	@Size(max=15)
	private String telephone;
	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%-+_!]).{8,20})",message = "{"+BusinessCode.VALIDATION_UTILISATEUR_MDP_PATTERN+"}")
	@NotBlank
	@Size(min=8, max=20)
	private String motDePasse;
	private int credit;
	private boolean administrateur;

	
	@Valid
	private Adresse adresse;	
	
	/**
	 * 
	 */
	public Utilisateur() {
	}

	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone, String motDePasse,
			int credit, boolean administrateur) {
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administrateur = administrateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public int isAdministrateur() {
		return (administrateur)?1:0;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}
	
	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Utilisateur [pseudo=");
		builder.append(pseudo);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", prenom=");
		builder.append(prenom);
		builder.append(", email=");
		builder.append(email);
		builder.append(", telephone=");
		builder.append(telephone);
		builder.append(", motDePasse=");
		builder.append(motDePasse);
		builder.append(", credit=");
		builder.append(credit);
		builder.append(", administrateur=");
		builder.append(isAdministrateur());
		builder.append("]");
		builder.append("Adresse => ");
		builder.append(adresse);
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(pseudo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utilisateur other = (Utilisateur) obj;
		return Objects.equals(pseudo, other.pseudo);
	}

		
	

}
