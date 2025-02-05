package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	void create(Utilisateur utilisateur);
	Utilisateur read(long id);
	Utilisateur read(String email);
	List<Utilisateur> findAll();
	String findPseudo(String pseudo);

}
