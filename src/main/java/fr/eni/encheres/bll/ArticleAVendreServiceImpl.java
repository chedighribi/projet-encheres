package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.ArticleAVendreDAO;
import fr.eni.encheres.dal.CategorieDAO;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;

@Service
public class ArticleAVendreServiceImpl implements ArticleAVendreService{
	private ArticleAVendreDAO ArticleAVendreDAO;
	private UtilisateurDAO utilisateurDAO;
	private CategorieDAO CategorieDAO;

	public ArticleAVendreServiceImpl(ArticleAVendreDAO ArticleAVendreDAO, UtilisateurDAO utilisateurDAO, CategorieDAO CategorieDAO) {
		this.ArticleAVendreDAO = ArticleAVendreDAO;
		this.CategorieDAO=CategorieDAO;
		this.utilisateurDAO = utilisateurDAO;
	}
	
	@Override
	public List<ArticleAVendre> consulterArticles() {
		return ArticleAVendreDAO.findAll();
	}

	//not clear ( ajoutez vendeur / acquereur ) 
	@Override
	public ArticleAVendre consulerArticleParId(long id) {
		ArticleAVendre a =  ArticleAVendreDAO.find(id);
		if(a != null) {
			chargerVendeurAcquereur(a); 
		}
		return a;
	}

	private void chargerVendeurAcquereur(ArticleAVendre a) {
			Utilisateur vendeur = utilisateurDAO.findByPseudo(a.getVendeur().getPseudo());
			a.setVendeur(vendeur);
	}

	@Override
	public void creerArticle(ArticleAVendre articleAVendre) {
		ArticleAVendreDAO.insertArticle(articleAVendre);
	}

	@Override
	public List<Categorie> consulterCategories() {
		return CategorieDAO.findAll();
	}

	@Override
	public Categorie consulterCategorieParId(long id) {
		return CategorieDAO.findById(id);
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
