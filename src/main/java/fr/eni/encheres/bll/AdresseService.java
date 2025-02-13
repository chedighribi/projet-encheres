package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;

public interface AdresseService {
	void creerAdresse(Adresse adresse);
	void modifierAdresse(Adresse adresse);
	public void add(Adresse adresse);

	Adresse consulterAdresseParNoAdresse(long noAdresse);
	List<Adresse> consulterAdresses();
	List<Adresse> consulterAdressesEni();
	List<Adresse> consulterAdressesEniPlusUtilisateur(long noAdresse);
}
