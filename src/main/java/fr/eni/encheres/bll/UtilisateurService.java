package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurService {
	void creerUtilisateur(Utilisateur utilisateur);
	Utilisateur consulterUtilisateurParId(long id);
	List<Utilisateur> consulterUtilisateurs();
	Utilisateur findByEmail(String email);
}
