package fr.eni.encheres.controller;

import java.time.LocalDate;
import java.util.List;

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

import fr.eni.encheres.bll.ArticleAVendreService;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exceptions.BusinessException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/articles")
@SessionAttributes({"CategoriesEnSession", "AdresseEnSession", "membreEnSession"})
public class ArticleAVendreController {
	private ArticleAVendreService articleAVendreService;

	public ArticleAVendreController(ArticleAVendreService articleAVendreService) {
		this.articleAVendreService = articleAVendreService;
	}

	@GetMapping
	public String afficherArticles(Model model) {
		List<ArticleAVendre> articles = articleAVendreService.consulterArticles();
		model.addAttribute("articles",articles);
		return "view-articles";
	}

	@GetMapping("/detail")
	public String detailVente(@RequestParam(name = "id", required = true) long id, Model model) {
		if (id > 0) {
			ArticleAVendre article = articleAVendreService.consulerArticleParId(id);
			if (article != null) {
				System.out.println("Type of dateFinEncheres: " + article.getDateFinEncheres().getClass().getName());
				model.addAttribute("article", article);
				return "view-article-detail";
			} else
				System.out.println("id invalide");
		} else {
			System.out.println("id invalide");
		}
		return "redirect:/";
	}
	
	@GetMapping("/creer")
	public String creerArticle(Model model) {
		 ArticleAVendre article = new ArticleAVendre();
		 article.setDateDebutEncheres(LocalDate.now());
		 article.setDateFinEncheres(LocalDate.now().plusWeeks(1));
		 model.addAttribute("article", article);
		return"view-article-creer";
	}
	
	@PostMapping("/creer")
	public String creerArticle(@Valid @ModelAttribute("article") ArticleAVendre articleAVendre, BindingResult bindingResult, @ModelAttribute("membreEnSession") Utilisateur user) {
		if (user != null && user.getPseudo().length() >= 1) {
			articleAVendre.setVendeur(user);
			System.out.println("Article créer: " + articleAVendre);
			System.out.println(bindingResult);
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
		}/*else {
			
			System.out.println("Aucun administrateur en session");
			ObjectError error = new ObjectError("globalError", BusinessCode.VALIDATION_MEMBRE_ADMIN);
			bindingResult.addError(error);
			
		}*/
		return "view-article-creer";
	}
	
	@ModelAttribute("CategoriesEnSession")
	public List<Categorie> chargerCategories() {
		return articleAVendreService.consulterCategories();
	}
	
	@ModelAttribute("AdresseEnSession")
	public List<Adresse> chargerAdresses() {
		return articleAVendreService.consulterAdresses();
	}

}
