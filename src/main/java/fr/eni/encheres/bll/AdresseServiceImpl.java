package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.dal.AdresseDAO;
import fr.eni.encheres.exceptions.BusinessException;

@Service
public class AdresseServiceImpl implements AdresseService {
	
	private AdresseDAO adresseDAO;
	
	public AdresseServiceImpl(AdresseDAO adresseDAO) {
		this.adresseDAO = adresseDAO;
	}

	@Override
	public void creerAdresse(Adresse adresse) {
		BusinessException be = new BusinessException();
		boolean isValid = true;
		isValid &= validerRue(adresse, be);
		isValid &= validerCodePostal(adresse, be);
		isValid &= validerVille(adresse, be);
		isValid &= validerIsEni(adresse,be);
		if(isValid) {
			adresseDAO.create(adresse);
		}
		else {
			throw be;
		}
	}

	private boolean validerIsEni(Adresse adresse, BusinessException be) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean validerVille(Adresse adresse, BusinessException be) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean validerCodePostal(Adresse adresse, BusinessException be) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean validerRue(Adresse adresse, BusinessException be) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public Adresse consulterAdresseParNoAdresse(long noAdresse) {
		return adresseDAO.read(noAdresse);
	}

	@Override
	public List<Adresse> consulterAdresses() {
		return adresseDAO.findAll();
	}

	@Override
	public void add(Adresse adresse) {
		System.out.println("BLL add adresse");
		System.out.println(adresse);
		adresseDAO.create(adresse);		
	}
	
}
