package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.ArticleAVendreDAO;
import fr.eni.encheres.dal.CategorieDAO;

@Service
public class ArticleAVendreServiceImpl implements ArticleAVendreService{
	private CategorieDAO categorieDao;
	private ArticleAVendreDAO ArticleAVendreDao;
	
	public ArticleAVendreServiceImpl(ArticleAVendreDAO ArticleAVendreDao, CategorieDAO categorieDao) {
		this.ArticleAVendreDao=ArticleAVendreDao;
		this.categorieDao=categorieDao;
	}
	
	@Override
	public List<ArticleAVendre> consulterArticles() {
		return ArticleAVendreDao.findAll();
	}

	@Override
	public void creerArticle(ArticleAVendre articleAVendre) {
		ArticleAVendreDao.insertArticle(articleAVendre);
	}

	@Override
	public List<Categorie> consulterCategories() {
		return categorieDao.findAll();
	}

	@Override
	public Categorie consulterCategorieParId(long id) {
		return categorieDao.findById(id);
	}

	@Override
	public List<Adresse> consulterAdresse() {
		return null;
	}

	@Override
	public Adresse consulterAdresseParId(long id) {
		return null;
	}

	

}
