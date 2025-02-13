package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.dal.AdresseDAO;
import fr.eni.encheres.exceptions.BusinessCode;
import fr.eni.encheres.exceptions.BusinessException;

@Service
public class AdresseServiceImpl implements AdresseService {

	private AdresseDAO adresseDAO;

	public AdresseServiceImpl(AdresseDAO adresseDAO) {
		this.adresseDAO = adresseDAO;
	}

	private boolean isValidAdresse(Adresse adresse) {
		BusinessException be = new BusinessException();
		boolean isValid = true;
		isValid &= validerAdresse(adresse, be);
		isValid &= validerRue(adresse, be);
		isValid &= validerCodePostal(adresse, be);
		isValid &= validerVille(adresse, be);
		if (isValid) {
			return true;
		} else {
			throw be;
		}
	}
	
	@Override
	public void modifierAdresse(Adresse adresse) {
		if (isValidAdresse(adresse)) {
			adresseDAO.update(adresse);
		}
	}

	private boolean validerIsEni(Adresse adresse, BusinessException be) {
		// TODO Auto-generated method stub
		return true;
	}

	private boolean validerAdresse(Adresse adresse, BusinessException be) {
		if (adresse == null) {
			be.add(BusinessCode.VALIDATION_ADRESSE_NULL);
			return false;
		}
		return true;
	}

	private boolean validerVille(Adresse adresse, BusinessException be) {
		if (adresse.getVille() == null || adresse	.getVille()
													.isBlank()) {
			be.add(BusinessCode.VALIDATION_ADRESSE_VILLE_BLANK);
			return false;
		}
		if (adresse	.getVille()
					.length() > 50) {
			be.add(BusinessCode.VALIDATION_ADRESSE_VILLE_SIZE);
			return false;
		}
		return true;
	}

	private boolean validerCodePostal(Adresse adresse, BusinessException be) {
		if (adresse.getCodePostal() == null || adresse	.getCodePostal()
														.isBlank()) {
			be.add(BusinessCode.VALIDATION_ADRESSE_CODEPOSTAL_BLANK);
			return false;
		}
		if (adresse	.getCodePostal()
					.length() > 10) {
			be.add(BusinessCode.VALIDATION_ADRESSE_CODEPOSTAL_SIZE);
			return false;
		}
		return true;
	}

	private boolean validerRue(Adresse adresse, BusinessException be) {
		if (adresse.getRue() == null || adresse	.getRue()
												.isBlank()) {
			be.add(BusinessCode.VALIDATION_ADRESSE_RUE_BLANK);
			return false;
		}
		if (adresse	.getRue()
					.length() > 100) {
			be.add(BusinessCode.VALIDATION_ADRESSE_RUE_SIZE);
			return false;
		}
		return true;
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

	@Override
	public List<Adresse> consulterAdressesEni() {
		return adresseDAO.findAllEni();
	}
	
	@Override
	public List<Adresse> consulterAdressesEniPlusUtilisateur(long noAdresse) {
		return adresseDAO.findEniPlusUtilisateur(noAdresse);
	}

	@Override
	public void creerAdresse(Adresse adresse) {
		// TODO Auto-generated method stub
		
	}
	
}
