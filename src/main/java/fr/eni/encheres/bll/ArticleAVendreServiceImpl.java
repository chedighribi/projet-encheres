package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleAVendreDAO;
import fr.eni.encheres.dal.UtilisateurDAO;

@Service
public class ArticleAVendreServiceImpl implements ArticleAVendreService{
	private ArticleAVendreDAO articleAVendreDao;
	private UtilisateurDAO utilisateurDAO;

	
	public ArticleAVendreServiceImpl(ArticleAVendreDAO articleAVendreDao, UtilisateurDAO utilisateurDAO) {
		this.articleAVendreDao = articleAVendreDao;
		this.utilisateurDAO = utilisateurDAO;
	}
	
	@Override
	public List<ArticleAVendre> consulterArticles() {
		return articleAVendreDao.findAll();
	}

	//not clear ( ajoutez vendeur / acquereur ) 
	@Override
	public ArticleAVendre consulerArticleParId(long id) {
		ArticleAVendre a =  articleAVendreDao.find(id);
		if(a != null) {
			chargerVendeurAcquereur(a); 
		}
		return a;
	}

	private void chargerVendeurAcquereur(ArticleAVendre a) {
			Utilisateur vendeur = utilisateurDAO.findByPseudo(a.getVendeur().getPseudo());
			a.setVendeur(vendeur);
	}

}
