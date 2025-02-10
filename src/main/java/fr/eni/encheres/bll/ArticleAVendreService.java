package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Categorie;

public interface ArticleAVendreService {
	List<ArticleAVendre> consulterArticles();
	void creerArticle(ArticleAVendre articleAVendre);
	List<Categorie> consulterCategories();
	Categorie consulterCategorieParId(long id);
	ArticleAVendre consulerArticleParId(long id);
}
