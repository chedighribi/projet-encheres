package fr.eni.encheres.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Profil implements Serializable {
	/**
	 * Identifiant de l'interface Serializable
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
	@Size(min = 4, max=250, message = "Le nom doit avoir au moins 4 caractères")
	private String nom;
	
	@NotBlank
	@Size(min = 4, max=250)
	private String prenom;

	@NotBlank
	@Email
	@Pattern(regexp="^[\\w-\\.]+@campus-eni.fr$")
	private String email;
	
	public Profil() {
		
	}

	public Profil(String nom, String prenom, String email) {
		this();// appel du constructeur par défaut
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
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

	@Override
	public String toString() {
		return "Formateur [nom=" + nom + ", prenom=" + prenom + ", email=" + email + "]";
	}

}
