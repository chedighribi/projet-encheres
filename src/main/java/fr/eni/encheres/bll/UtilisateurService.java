package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurService {
	void creerUtilisateur(Utilisateur utilisateur);
	Utilisateur consulterUtilisateurParPseudo(String pseudo);
	List<Utilisateur> consulterUtilisateurs();
	
	Utilisateur findByEmail(String email);
	
	public void add(Utilisateur utilisateur);
}
