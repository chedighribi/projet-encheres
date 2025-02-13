package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	void create(Utilisateur utilisateur);
	Utilisateur read(String email);
	Utilisateur findByPseudo(String pseudo);
	void updateCredit(String pseudo, int newCredit);
	List<Utilisateur> findAll();
	int uniqueEmail(String email);
	int uniquePseudo(String pseudo);
	Utilisateur ReadByPseudo(String pseudo);
	void update(Utilisateur utilisateur);
	int uniqueNewEmail(String email, String pseudo);
	void delete(Utilisateur utilisateur);
}
