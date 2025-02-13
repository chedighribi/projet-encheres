package fr.eni.encheres.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Adresse implements Serializable {
	/**
	 * Identifiant de l'interface Serializable
	 */
	private static final long serialVersionUID = 1L;
	private long noAdresse;
	@NotBlank
	@Size(max=100)
	private String rue;
	@NotBlank
	@Size(max=10)
	private String codePostal;
	@NotBlank
	@Size(max=50)
	private String ville;
	@NotNull
	private boolean adresseEni;
	
	public Adresse() {
	}
	
	/**
	 * @param noAdresse
	 * @param rue
	 * @param codePostal
	 * @param ville
	 * @param adresseEni
	 */
	public Adresse(String rue, String codePostal, String ville) {
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.adresseEni = false;
	}

	/**
	 * @return the id
	 */
	public long getNoAdresse() {
		return noAdresse;
	}


	/**
	 * @param noAdresse the noAdresse to set
	 */
	public void setNoAdresse(long noAdresse) {
		this.noAdresse = noAdresse;
	}


	/**
	 * @return the rue
	 */
	public String getRue() {
		return rue;
	}


	/**
	 * @param rue the rue to set
	 */
	public void setRue(String rue) {
		this.rue = rue;
	}


	/**
	 * @return the codePostal
	 */
	public String getCodePostal() {
		return codePostal;
	}


	/**
	 * @param codePostal the codePostal to set
	 */
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}


	/**
	 * @return the ville
	 */
	public String getVille() {
		return ville;
	}


	/**
	 * @param ville the ville to set
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}

	/**
	 * @return the adresseEni
	 */
	public boolean isAdresseEni() {
		return adresseEni;
	}

	/**
	 * @param adresseEni the adresseEni to set
	 */
	public void setAdresseEni(boolean adresseEni) {
		this.adresseEni = adresseEni;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(noAdresse);
		builder.append(" ");
		builder.append(rue);
		builder.append(", ");
		builder.append(codePostal);
		builder.append(" - ");
		builder.append(ville);
		return builder.toString();
	}
		
}
