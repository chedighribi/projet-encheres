package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	void create(Utilisateur utilisateur);

	Utilisateur readByPseudo(String pseudo);

	List<Utilisateur> read(String email);

	List<Utilisateur> findAll();

	int findPseudo(String pseudo);
}
