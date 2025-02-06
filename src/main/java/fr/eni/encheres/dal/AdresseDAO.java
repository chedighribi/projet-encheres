package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Adresse;

public interface AdresseDAO {
	void create(Adresse adresse);
	Adresse read(long id);
	List<Adresse> findAll();
}