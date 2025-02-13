package fr.eni.encheres.controller.converter;

import fr.eni.encheres.bll.AdresseService;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.encheres.bo.Adresse;

@Component
public class StringToAdresseConverter implements Converter<String, Adresse>{
	private AdresseService adresseService;

	public StringToAdresseConverter(AdresseService adresseService) {
		this.adresseService = adresseService;
	}

	@Override
	public Adresse convert(String noAdresse) {
	    Long theNoAdresse = Long.parseLong(noAdresse);
	    return adresseService.consulterAdresseParNoAdresse(theNoAdresse);
	    
	}
}
