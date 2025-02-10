package fr.eni.encheres.bll;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exceptions.BusinessException;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	
	private Logger logger = (Logger) LoggerFactory.getLogger("fr.eni.encheres.dev");
	
	private UtilisateurDAO utilisateurDAO;
	
	
	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO) {
		this.utilisateurDAO = utilisateurDAO;
	}

	@Override
	public void creerUtilisateur(Utilisateur utilisateur) {
		BusinessException be = new BusinessException();
		boolean isValid = true;
		isValid &= isPseudoDisponible(utilisateur.getPseudo(), be);
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
	public Utilisateur consulterUtilisateurParPseudo(String pseudo) {
		return utilisateurDAO.findByPseudo(pseudo);
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

	private boolean isPseudoDisponible(String pseudo, BusinessException be) {
		System.out.println("isPseudoDisponible");
		int pseudoExist = 1;
		try {
			pseudoExist = utilisateurDAO.findPseudo(pseudo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//				throw be;
			e.printStackTrace();
			
		}
		return (pseudoExist == 0)?true:false;
	}

	@Override
	public Utilisateur findByEmail(String email) {
		System.out.println("BLL findByEmail");
		return utilisateurDAO.read(email);
	}

	@Override
	public void add(Utilisateur utilisateur) {
		logger.trace("BLL add utilisateurs");
		logger.trace(utilisateur.toString());
		logger.trace(utilisateur.getEmail());
		utilisateurDAO.create(utilisateur);
	}
	
	@Override
	public boolean canAdd(Utilisateur utilisateur) {
		logger.trace("BLL add utilisateurs");
		logger.trace(utilisateur.toString());
		logger.trace(utilisateur.getEmail());
		BusinessException be = new BusinessException();
		if (this.isPseudoDisponible(utilisateur.getPseudo(), be)) {
			logger.trace("Pseudo disponible !");
			Utilisateur utilisateurAvecEmailIdentique = utilisateurDAO.read(utilisateur.getEmail());
			logger.trace(" " + utilisateurAvecEmailIdentique);
			if (utilisateurAvecEmailIdentique == null) {
				logger.trace("utilisateurAvecEmailIdentique est vide");
				return true;
			}
		}
		return false;
	}
	

}
