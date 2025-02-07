package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	void create(Utilisateur utilisateur);
	Utilisateur read(String email);
	Utilisateur findByPseudo(String pseudo);
	List<Utilisateur> findAll();
	int findPseudo(String pseudo);
}
