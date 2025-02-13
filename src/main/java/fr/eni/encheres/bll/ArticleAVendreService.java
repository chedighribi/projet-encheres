package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;

public interface ArticleAVendreService {
	List<ArticleAVendre> consulterArticles();
	List<ArticleAVendre> consulterArticlesParFiltres(String nomArticle, Long idCategorie, String idVente, String idAchat, String pseudoMembre);
	void creerArticle(ArticleAVendre articleAVendre);
	List<Categorie> consulterCategories();
	Categorie consulterCategorieParId(long id);
	ArticleAVendre consulerArticleParId(long id);
	void modifierArticle(ArticleAVendre articleAVendre);
	void supprimerArticle(ArticleAVendre articleAVendre);
	void creerEnchere(Enchere enchere);
}
