package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	void create(Utilisateur utilisateur);
	Utilisateur findByPseudo(String pseudo);
	List<Utilisateur> findAll();
	String findPseudo(String pseudo);
	Utilisateur findByEmail(String email);
}
