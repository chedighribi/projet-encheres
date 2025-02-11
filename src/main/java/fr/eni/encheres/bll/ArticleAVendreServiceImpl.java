package fr.eni.encheres.bll;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.AdresseDAO;
import fr.eni.encheres.dal.ArticleAVendreDAO;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exceptions.BusinessCode;
import fr.eni.encheres.exceptions.BusinessException; 

@Service
public class ArticleAVendreServiceImpl implements ArticleAVendreService{
	private ArticleAVendreDAO ArticleAVendreDAO;
	private UtilisateurDAO utilisateurDAO;
	private CategorieDAO CategorieDAO;
	private AdresseDAO AdresseDAO;
	private EnchereDAO enchereDAO;

	public ArticleAVendreServiceImpl(ArticleAVendreDAO ArticleAVendreDAO, UtilisateurDAO utilisateurDAO, CategorieDAO CategorieDAO,AdresseDAO AdresseDAO, EnchereDAO enchereDAO) {
		this.ArticleAVendreDAO = ArticleAVendreDAO;
		this.CategorieDAO=CategorieDAO;
		this.utilisateurDAO = utilisateurDAO;
		this.AdresseDAO=AdresseDAO;
		this.enchereDAO = enchereDAO;
	}
	
	@Override
	public List<ArticleAVendre> consulterArticles() {
		return ArticleAVendreDAO.findAll();
	}

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
			List<Enchere> encheres = enchereDAO.readByArticle(a.getId());
			encheres.forEach((e) -> e.setUtilisateur(utilisateurDAO.findByPseudo(e.getUtilisateur().getPseudo())));
			Collections.sort(encheres);
			a.setEncheres(encheres);
			 
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
	
	@Override
	@Transactional
	public void creerArticle(ArticleAVendre article) {
		BusinessException be = new BusinessException();
		boolean isValid = true;
		isValid &= validerArticle(article, be);
		isValid &= validerCategorie(article.getCategorie(), be);
		isValid &= validerDescription(article.getDescription(), be);
		isValid &= validerAdresse(article.getAdresse(), be);
		isValid &= validerPrixInitial(article.getPrixInitial(), be);
		isValid &= validerDateDebutEnchere(article.getDateDebutEncheres(), be);
		isValid &= validerDateFinEnchere(article.getDateFinEncheres(), be);
		isValid &= validerDates(article.getDateDebutEncheres(), article.getDateFinEncheres(), be);
		if(isValid) {
			ArticleAVendreDAO.insertArticle(article);
		}else {
			throw be;
		}
	}
	
	public boolean validerArticle(ArticleAVendre article, BusinessException be) {
		if(article == null) {
			be.add(BusinessCode.VALIDATION_ARTICLE_NULL);
			return false;
		}
		return true;
	}
	
	public boolean validerCategorie(Categorie categorie, BusinessException be) {
		if(categorie == null) {
			be.add(BusinessCode.VALIDATION_ARTICLE_CATEGORIE_NULL);
			return false;
		}
		return true;
	}
	
	public boolean validerDescription(String description, BusinessException be) {
		if(description == null) {
			be.add(BusinessCode.VALIDATION_ARTICLE_DESCRIPTION_BLANK);
			return false;
		}
		if(description.length() > 250) {
			be.add(BusinessCode.VALIDATION_ARTICLE_DESCRIPTION_LENGTH);
			return false;
		}
		return true;
	}
	
	public boolean validerAdresse(Adresse adresse, BusinessException be) {
		if(adresse == null) {
			be.add(BusinessCode.VALIDATION_ARTICLE_ADRESSE_NULL);
			return false;
		}
		return true;
	}
	
	public boolean validerPrixInitial(int prix, BusinessException be) {
		if(prix < 1) {
			be.add(BusinessCode.VALIDATION_ARTICLE_PRIX_MIN);
			return false;
		}
		return true;
	}
	
	public boolean validerDateDebutEnchere(LocalDate dateDebut, BusinessException be) {
		if(dateDebut.isBefore(LocalDate.now())) {
			be.add(BusinessCode.VALIDATION_ARTICLE_DATEDEBUT_MIN);
			return false;
		}
		return true;
	}
	
	public boolean validerDateFinEnchere(LocalDate dateFin, BusinessException be) {
		if(dateFin.isBefore(LocalDate.now())) {
			be.add(BusinessCode.VALIDATION_ARTICLE_DATEFIN_MIN);
			return false;
		}
		return true;
	}
	
	public boolean validerDates(LocalDate dateDebut, LocalDate dateFin, BusinessException be) {
		if(dateFin.isBefore(dateDebut)) {
			be.add(BusinessCode.VALIDATION_ARTICLE_DATES);
			return false;
		}
		return true;
	}

	

}
