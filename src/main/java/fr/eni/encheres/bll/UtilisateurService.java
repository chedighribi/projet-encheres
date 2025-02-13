package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurService {
	void creerUtilisateur(Utilisateur utilisateur);
	void modifierUtilisateur(Utilisateur utilisateur);
	void supprimerUtilisateur(Utilisateur utilisateur);
	public void add(Utilisateur utilisateur);

	void modifierMdp(String pseudo, String motDePasse);

	Utilisateur consulterUtilisateurParPseudo(String pseudo);
	List<Utilisateur> consulterUtilisateurs();
	Utilisateur findByEmail(String email);
	
	
	boolean canAdd(Utilisateur utilisateur);
	String getMdpParPseudo(String pseudo);
}
