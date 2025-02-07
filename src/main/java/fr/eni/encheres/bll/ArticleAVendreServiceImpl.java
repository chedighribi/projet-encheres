package fr.eni.encheres.bll;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.ArticleAVendreDAO;
import fr.eni.encheres.dal.CategorieDAO;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;

import fr.eni.encheres.dal.AdresseDAO;

import fr.eni.encheres.exceptions.BusinessException;
import fr.eni.encheres.exceptions.BusinessCode;

@Service
public class ArticleAVendreServiceImpl implements ArticleAVendreService{
	private ArticleAVendreDAO ArticleAVendreDAO;
	private UtilisateurDAO utilisateurDAO;
	private CategorieDAO CategorieDAO;
	private AdresseDAO AdresseDAO;

	public ArticleAVendreServiceImpl(ArticleAVendreDAO ArticleAVendreDAO, UtilisateurDAO utilisateurDAO, CategorieDAO CategorieDAO,AdresseDAO AdresseDAO) {
		this.ArticleAVendreDAO = ArticleAVendreDAO;
		this.CategorieDAO=CategorieDAO;
		this.utilisateurDAO = utilisateurDAO;
		this.AdresseDAO=AdresseDAO;
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
	public List<Adresse> consulterAdresses() {
		return AdresseDAO.findAll();
	}

	@Override
	public Adresse consulterAdresseParId(long id) {
		return AdresseDAO.read(id);
	}
	
	public boolean validerArticle(ArticleAVendre article, BusinessException be) {
		if(article == null) {
			be.add(BusinessCode.VALIDATION_ARTICLE_NULL);
			return false;
		}
		return true;
	}
	
	public boolean validerCategorie(Categorie categorie, BusinessException be) {
		return false;
	}
	
	public boolean validerDescription(String decription, BusinessException be) {
		return false;
	}
	
	public boolean validerAdresse(Adresse adresse, BusinessException be) {
		return false;
	}
	
	public boolean validerPrixInitial(int prix, BusinessException be) {
		return false;
	}
	
	public boolean validerDateDebutEnchere(LocalDate dateDebut, BusinessException be) {
		return false;
	}
	
	public boolean validerDateFinEnchere(LocalDate dateFin, BusinessException be) {
		return false;
	}
	
	public boolean validerDates() {
		return false;
	}

	

}
