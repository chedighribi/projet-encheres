package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.ArticleAVendre;

public interface ArticleAVendreService {
	List<ArticleAVendre> consulterArticles();
	ArticleAVendre consulerArticleParId(long id);
}
