package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Enchere;

public interface EnchereDAO {
	void create(Enchere enchere);
	Enchere readByItem(long id);
	List<Enchere> readByUser(String pseudo);
}
