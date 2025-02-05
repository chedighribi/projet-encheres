package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.dal.ArticleAVendreDAO;

@Service
public class ArticleAVendreServiceImpl implements ArticleAVendreService{
	private ArticleAVendreDAO ArticleAVendreDao;
	
	public ArticleAVendreServiceImpl(ArticleAVendreDAO ArticleAVendreDao) {
		this.ArticleAVendreDao=ArticleAVendreDao;
	}
	
	@Override
	public List<ArticleAVendre> consulterArticles() {
		return ArticleAVendreDao.findAll();
	}

}
