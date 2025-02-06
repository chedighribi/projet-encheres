package fr.eni.encheres.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.encheres.bll.ArticleAVendreService;
import fr.eni.encheres.bo.Categorie;

@Component
public class StringToCategorieConverter implements Converter<String, Categorie> {
		private ArticleAVendreService articleAVendreService;

		public StringToCategorieConverter(ArticleAVendreService articleAVendreService) {
			this.articleAVendreService = articleAVendreService;
		}

		@Override
		public Categorie convert(String id) {
		    Long theId = Long.parseLong(id);
		    return articleAVendreService.consulterCategorieParId(theId);
		    
		}
}
