package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exceptions.BusinessException;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	
	private UtilisateurDAO utilisateurDAO;
	
	
	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO) {
		this.utilisateurDAO = utilisateurDAO;
	}

	@Override
	public void creerUtilisateur(Utilisateur utilisateur) {
		BusinessException be = new BusinessException();
		boolean isValid = true;
		isValid &= validerPseudo(utilisateur.getPseudo(), be);
		isValid &= validerNom(utilisateur, be);
		isValid &= validerPrenom(utilisateur, be);
		isValid &= validerEmail(utilisateur, be);
		isValid &= validerTelephone(utilisateur,be);
		isValid &= validerCredit(utilisateur,be);
		if(isValid) {
			utilisateurDAO.create(utilisateur);
		}
		else {
			throw be;
		}
	}

	@Override
	public Utilisateur consulterUtilisateurParId(long id) {
		return utilisateurDAO.read(id);
	}

	@Override
	public List<Utilisateur> consulterUtilisateurs() {
		return utilisateurDAO.findAll();
	}
	
	
	private boolean validerCredit(Utilisateur utilisateur, BusinessException be) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean validerEmail(Utilisateur utilisateur, BusinessException be) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean validerTelephone(Utilisateur utilisateur, BusinessException be) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean validerPrenom(Utilisateur utilisateur, BusinessException be) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean validerNom(Utilisateur utilisateur, BusinessException be) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean validerPseudo(String pseudo, BusinessException be) {
		try {
			String pseudoExist = utilisateurDAO.findPseudo(pseudo);
			
		} catch (DataAccessException e) {
			
		}
		return true;
	}

	@Override
	public Utilisateur findByEmail(String email) {
		System.out.println("BLL findByEmail");
		return utilisateurDAO.findByEmail(email);
	}


}
