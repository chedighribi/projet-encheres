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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.ArticleAVendreService;
import fr.eni.encheres.bo.ArticleAVendre;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.exceptions.BusinessException;

@Controller
@RequestMapping("/articles")
@SessionAttributes({"CategoriesEnSession"})
public class ArticleAVendreController {
	private ArticleAVendreService articleAVendreService;

	public ArticleAVendreController(ArticleAVendreService articleAVendreService) {
		this.articleAVendreService = articleAVendreService;
	}

	@GetMapping
	public String afficherArticles(Model model) {
		List<ArticleAVendre> articles = articleAVendreService.consulterArticles();
		model.addAttribute("articles",articles);
		return "view-encheres";
	}

	@GetMapping("/detail")
	public String detailVente(@RequestParam(name = "id", required = true) long id, Model model) {
		if (id > 0) {
			ArticleAVendre article = articleAVendreService.consulerArticleParId(id);
			if (article != null) {
				model.addAttribute("article", article);
				return "view-detail-vente";
			} else
				System.out.println("id invalide");
		} else {
			System.out.println("id invalide");
		}
		return "redirect:/";
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
	
	@ModelAttribute("CategoriesEnSession")
	public List<Categorie> chargerCategories() {
		return articleAVendreService.consulterCategories();
	}
}
