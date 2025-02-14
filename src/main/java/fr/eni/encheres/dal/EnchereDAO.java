package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Enchere;

public interface EnchereDAO {
	void create(Enchere enchere);
	List<Enchere> readByArticle(long id);
	List<Enchere> readByUser(String pseudo);
	Enchere readHighestEnchere(long articleId);
	void deleteEnchere(String pseudo);
}
