package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Utilisateur;

public interface ArticleAVendreDAO {
	List<ArticleAVendre> findAll();
	void insertArticle(ArticleAVendre articleAVendre);
	ArticleAVendre find(long id);
	void updateArticle(ArticleAVendre articleAVendre);
	void delete(ArticleAVendre articleAVendre);
}
