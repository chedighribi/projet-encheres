package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	void create(Utilisateur utilisateur);
	void update(Utilisateur utilisateur);
	void delete(Utilisateur utilisateur);

	void updateCredit(String pseudo, int newCredit);
	void updatePassword(String pseudo, String newPassword);

	Utilisateur read(String email);
	Utilisateur findByPseudo(String pseudo);
	Utilisateur ReadByPseudo(String pseudo);
	List<Utilisateur> findAll();
	String readPasswordByPseudo(String pseudo);

	int uniqueEmail(String email);
	int uniquePseudo(String pseudo);
	int uniqueNewEmail(String email, String pseudo);
}
