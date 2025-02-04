package fr.eni.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eni.encheres.bll.ArticleAVendreService;
import fr.eni.encheres.bo.ArticleAVendre;

@Controller
@RequestMapping("/encheres")
public class EncheresController {
	
	private ArticleAVendreService articleAVendreService;

	public EncheresController(ArticleAVendreService articleAVendreService) {
		this.articleAVendreService = articleAVendreService;
	}
	
	@GetMapping
	public String afficherEncheres(Model model) {
		List<ArticleAVendre> articles = articleAVendreService.consulterArticles();
		model.addAttribute("articles",articles);
		return "view-encheres";
	}
	
	@GetMapping("/creer")
	public String creerArticle() {
		return"view-article-creer";
	}
}
