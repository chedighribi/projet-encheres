package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Adresse;

public interface AdresseService {
	void creerAdresse(Adresse adresse);
	Adresse consulterAdresseParNoAdresse(long noAdresse);
	List<Adresse> consulterAdresses();
	
	public void add(Adresse adresse);
}
