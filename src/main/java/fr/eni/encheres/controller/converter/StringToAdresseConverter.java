package fr.eni.encheres.controller.converter;

import fr.eni.encheres.bll.ArticleAVendreService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.encheres.bo.Adresse;

@Component
public class StringToAdresseConverter implements Converter<String, Adresse>{
	private ArticleAVendreService articleAVendreService;

	public StringToAdresseConverter(ArticleAVendreService articleAVendreService) {
		this.articleAVendreService = articleAVendreService;
	}

	@Override
	public Adresse convert(String noAdresse) {
	    Long theNoAdresse = Long.parseLong(noAdresse);
	    return articleAVendreService.consulterAdresseParId(theNoAdresse);
	    
	}
}
