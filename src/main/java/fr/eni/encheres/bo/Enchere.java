package fr.eni.encheres.bo;

import java.time.LocalDateTime;

public class Enchere {
	private LocalDateTime date;
	private int montant;
	private Utilisateur utilisateur;
	private ArticleAVendre articleAVendre;
	
	public Enchere() {
		
	}
	
	public Enchere(LocalDateTime date, int montant, Utilisateur utilisateur, ArticleAVendre articleAVendre) {
		this.date=date;
		this.montant=montant;
		this.utilisateur=utilisateur;
		this.articleAVendre=articleAVendre;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public ArticleAVendre getArticleAVendre() {
		return articleAVendre;
	}

	public void setArticleAVendre(ArticleAVendre articleAVendre) {
		this.articleAVendre = articleAVendre;
	}

	@Override
	public String toString() {
		return "Enchere [date=" + date + ", montant=" + montant + ", utilisateur=" + utilisateur + ", articleAVendre="
				+ articleAVendre + "]";
	}
	
	
}
