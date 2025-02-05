package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.ArticleAVendre;

public interface ArticleAVendreDAO {
	List<ArticleAVendre> findAll();
}
