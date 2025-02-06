package fr.eni.encheres.bo;

import java.io.Serializable;

public class Categorie implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String libelle;
	
	public Categorie() {
		
	}
	
	public Categorie(long id, String libelle) {
		this.id=id;
		this.libelle=libelle;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		return "Categorie [id=" + id + ", libelle=" + libelle + "]";
	}
	
	
}
