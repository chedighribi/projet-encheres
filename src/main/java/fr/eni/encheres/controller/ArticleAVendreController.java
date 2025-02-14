package fr.eni.encheres.controller;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.AdresseService;
import fr.eni.encheres.bll.ArticleAVendreService;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exceptions.BusinessException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/articles")
@SessionAttributes({ "CategoriesEnSession","adressesEnSession", "membreEnSession" })
public class ArticleAVendreController {
	private ArticleAVendreService articleAVendreService;
	private AdresseService adresseService;

	public ArticleAVendreController(ArticleAVendreService articleAVendreService, AdresseService adresseService) {
		this.articleAVendreService = articleAVendreService;
		this.adresseService = adresseService;
	}

	@GetMapping
	public String afficherArticles(@ModelAttribute("membreEnSession") Utilisateur membreEnSession, Model model) {
		List<ArticleAVendre> articles = articleAVendreService.consulterArticles();
		model.addAttribute("articles", articles);
		return "view-articles";
	}

	@RequestMapping("/filtrer")
	public String afficherArticlesByCategorie(
	        @RequestParam(name = "nomArticle", required = false) String nomArticle,
	        @RequestParam(name = "idCategorie", required = false) Long idCategorie,
	        @RequestParam(name = "idVente", required = false) String idVente,
	        @RequestParam(name = "typeAchat", required = false) String idAchat,
	        @ModelAttribute("membreEnSession") Utilisateur membreEnSession,
	        Model model) {
	    List<ArticleAVendre> articles = articleAVendreService.consulterArticlesParFiltres(nomArticle, idCategorie, idVente, idAchat, membreEnSession.getPseudo());
	    model.addAttribute("articles", articles);
	    model.addAttribute("nomArticle", nomArticle);
	    model.addAttribute("idCategorie", idCategorie);
	    model.addAttribute("idVente", idVente);
	    model.addAttribute("idAchat", idAchat);

	    return "view-articles";
	}


	@GetMapping("/detail")
	public String detailVente(@RequestParam(name = "id", required = true) long id, @ModelAttribute("membreEnSession") Utilisateur utilisateur, Model model) {
		if (id > 0) {
			ArticleAVendre article = articleAVendreService.consulerArticleParId(id);
			if (article != null) {
				System.out.println("Type of dateFinEncheres: " + article.getDateFinEncheres().getClass().getName());
				model.addAttribute("article", article);
				Enchere enchere = new Enchere();
				enchere.getArticleAVendre().setId(article.getId());
				enchere.getUtilisateur().setPseudo(utilisateur.getPseudo());
				model.addAttribute("enchere", enchere);
				return "view-article-detail";
			} else
				System.out.println("id invalide");
		} else {
			System.out.println("id invalide");
		}
		return "redirect:/";
	}

	@GetMapping("/creer")
	public String creerArticle(Model model,@ModelAttribute("membreEnSession") Utilisateur user) {
		ArticleAVendre article = new ArticleAVendre();
		article.setDateDebutEncheres(LocalDate.now());
		article.setDateFinEncheres(LocalDate.now().plusWeeks(1));
		List<Adresse> adresses = adresseService.consulterAdressesEniPlusUtilisateur(user.getAdresse().getNoAdresse());
		model.addAttribute("adressesEnSession",adresses);
		model.addAttribute("article", article);
		return "view-article-creer";
	}

	@PostMapping("/creer")
	public String creerArticle(@Valid @ModelAttribute("article") ArticleAVendre articleAVendre,
			BindingResult bindingResult, @ModelAttribute("membreEnSession") Utilisateur user) {
		if (user != null && user.getPseudo().length() >= 1) {
			articleAVendre.setVendeur(user);
			if (!bindingResult.hasErrors()) {
				try {
					articleAVendreService.creerArticle(articleAVendre);
					return "redirect:/articles";
				} catch (BusinessException e) {
					System.err.println(e.getClefsExternalisations());
					e.getClefsExternalisations().forEach(key -> {
						ObjectError error = new ObjectError("globalError", key);
						bindingResult.addError(error);
					});
				}
			}
		}
		return "view-article-creer";
	}

	@GetMapping("/modifier")
	public String modifierArticle(@RequestParam(name="idArticle", required=true)long id, Model model,@ModelAttribute("membreEnSession") Utilisateur user) {
		 ArticleAVendre article = articleAVendreService.consulerArticleParId(id);
		 List<Adresse> adresses = adresseService.consulterAdressesEniPlusUtilisateur(user.getAdresse().getNoAdresse());
		 model.addAttribute("adressesEnSession",adresses);
		 model.addAttribute("article", article);
		return"view-article-modifier";

	}

	@PostMapping("/modifier")
	public String modifierArticle(@Valid @ModelAttribute("article") ArticleAVendre articleAVendre, BindingResult bindingResult) {
			if (!bindingResult.hasErrors()) {
				try {
					articleAVendreService.modifierArticle(articleAVendre);
					return "redirect:/articles";
				} catch (BusinessException e) {
					System.err.println(e.getClefsExternalisations());
					e.getClefsExternalisations().forEach(key -> {
						ObjectError error = new ObjectError("globalError", key);
						bindingResult.addError(error);
					});
				}
			}
		return "view-article-creer";
	}
	
	@PostMapping("/supprimer")
	public String supprimerArticle(@ModelAttribute("article") ArticleAVendre articleAVendre) {
		System.out.println("article suppr: " + articleAVendre);
		try {
			articleAVendreService.supprimerArticle(articleAVendre);
			return "redirect:/articles";
		} catch (BusinessException e) {
			System.err.println(e.getClefsExternalisations());
		}
		return "view-article-modifier";
	}

	@ModelAttribute("CategoriesEnSession")
	public List<Categorie> chargerCategories() {
		return articleAVendreService.consulterCategories();
	}

	@PostMapping("/enchere")
	public String encherir(@ModelAttribute("enchere") Enchere enchere) {
		try {
			articleAVendreService.creerEnchere(enchere);
			return "redirect:/";
		} catch (BusinessException e) {
			System.err.println(e.getClefsExternalisations());
		}
		return null;
	}

}
