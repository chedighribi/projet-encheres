package fr.eni.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.ArticleAVendreService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.exceptions.BusinessException;

@Controller
@RequestMapping("/encheres")
@SessionAttributes({"CategoriesEnSession"})
public class EncheresController {
	

	private ArticleAVendreService articleAVendreService;

	public EncheresController(ArticleAVendreService articleAVendreService, UtilisateurService utilisateurService) {
		this.articleAVendreService = articleAVendreService;
	}

	@GetMapping
	public String afficherEncheres(Model model) {
		List<ArticleAVendre> articles = articleAVendreService.consulterArticles();
		model.addAttribute("articles",articles);
		return "view-encheres";
	}
	
	@GetMapping("/creer")
	public String creerArticle(Model model) {
		model.addAttribute("article", new ArticleAVendre());
		return"view-article-creer";
	}
	
	@PostMapping("/creer")
	public String creerArticle( @ModelAttribute("article") ArticleAVendre articleAVendre, BindingResult bindingResult) {
		System.out.println("Article créer: " + articleAVendre);
		if (!bindingResult.hasErrors()) {
			try {
				articleAVendreService.creerArticle(articleAVendre);
				return "redirect:/films";
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
	
	@ModelAttribute("CategoriesEnSession")
	public List<Categorie> chargerCategories() {
		return articleAVendreService.consulterCategories();
	}
	
}
