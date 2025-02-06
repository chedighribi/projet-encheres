package fr.eni.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.encheres.bll.ArticleAVendreService;
import fr.eni.encheres.bo.ArticleAVendre;

@Controller
@RequestMapping("/articles")
public class ArticleController {
	private ArticleAVendreService articleAVendreService;

	public ArticleController(ArticleAVendreService articleAVendreService) {
		this.articleAVendreService = articleAVendreService;
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
}
