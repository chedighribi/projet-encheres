package fr.eni.encheres.bll;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exceptions.BusinessCode;
import fr.eni.encheres.exceptions.BusinessException;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	private Logger logger = (Logger) LoggerFactory.getLogger("fr.eni.encheres.dev");

	private UtilisateurDAO utilisateurDAO;
	
	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO) {
		this.utilisateurDAO = utilisateurDAO;
	}

	@Override
	@Transactional
	public void creerUtilisateur(Utilisateur utilisateur) {
		BusinessException be = new BusinessException();
		boolean isValid = true;
//		isValid &= isPseudoDisponible(utilisateur.getPseudo(), be);
		System.out.println("BLL utilisateur creerUtilisateur");
		isValid &= validerUtilisateur(utilisateur, be);
		System.out.println("BLL utilisateur creerUtilisateur 1");
		isValid &= validerPseudo(utilisateur, be);
		System.out.println("BLL utilisateur creerUtilisateur 2");
		isValid &= validerNom(utilisateur, be);
		System.out.println("BLL utilisateur creerUtilisateur 3");
		isValid &= validerPrenom(utilisateur, be);
		System.out.println("BLL utilisateur creerUtilisateur 4");
		isValid &= validerEmail(utilisateur, be);
		System.out.println("BLL utilisateur creerUtilisateur 5");
		isValid &= validerTelephone(utilisateur, be);
		System.out.println("BLL utilisateur creerUtilisateur 6");
		isValid &= validerMotDePasse(utilisateur, be);
		System.out.println("BLL utilisateur creerUtilisateur 7");
		isValid &= validerUniquePseudo(utilisateur, be);
		System.out.println("BLL utilisateur creerUtilisateur 8");
		isValid &= validerUniqueEmail(utilisateur, be);
		if (isValid) {
			System.out.println("BLL utilisateur creerUtilisateur valid");
//			adresseService.creerAdresse(utilisateur.getAdresse());
//			System.out.println("BLL utilisateur creerUtilisateur valid after adresse");
			
			utilisateurDAO.create(utilisateur);
		} else {
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

	private boolean validerUtilisateur(Utilisateur utilisateur, BusinessException be) {
		if (utilisateur == null) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_NULL);
			return false;
		}
		return true;
	}

	private boolean validerEmail(Utilisateur utilisateur, BusinessException be) {
		if (utilisateur.getEmail() == null || utilisateur	.getEmail()
															.isBlank()) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_PSEUDO_BLANK);
			return false;
		}
		if (utilisateur	.getEmail()
						.length() > 100) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_PSEUDO_SIZE);
			return false;
		}
		return true;
	}

	private boolean validerTelephone(Utilisateur utilisateur, BusinessException be) {
		if (utilisateur	.getTelephone()
						.length() > 15) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_PHONE_SIZE);
			return false;
		}
		return true;
	}

	private boolean validerPseudo(Utilisateur utilisateur, BusinessException be) {
		if (utilisateur.getPseudo() == null || utilisateur	.getPseudo()
															.isBlank()) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_PSEUDO_BLANK);
			return false;
		}
		if (utilisateur	.getPseudo()
						.length() > 30) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_PSEUDO_SIZE);
			return false;
		}
		return true;
	}

	private boolean validerPrenom(Utilisateur utilisateur, BusinessException be) {
		if (utilisateur.getPrenom() == null || utilisateur	.getPrenom()
															.isBlank()) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_PRENOM_BLANK);
			return false;
		}
		if (utilisateur	.getPrenom()
						.length() > 50) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_PRENOM_SIZE);
			return false;
		}
		return true;
	}

	private boolean validerNom(Utilisateur utilisateur, BusinessException be) {
		if (utilisateur.getNom() == null || utilisateur	.getNom()
														.isBlank()) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_NOM_BLANK);
			return false;
		}
		if (utilisateur	.getNom()
						.length() > 40) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_NOM_SIZE);
			return false;
		}
		return true;
	}

	private boolean validerMotDePasse(Utilisateur utilisateur, BusinessException be) {
		if (utilisateur.getMotDePasse() == null || utilisateur	.getMotDePasse()
																.isBlank()) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_MDP_BLANK);
			return false;
		}
		if (utilisateur	.getMotDePasse()
						.length() < 8
				|| utilisateur	.getMotDePasse()
								.length() > 20) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_MDP_SIZE);
			return false;
		}
		return true;
	}

	private boolean validerCredit(Utilisateur utilisateur, BusinessException be) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean validerUniqueEmail(Utilisateur utilisateur, BusinessException be) {
		int count = utilisateurDAO.uniqueEmail(utilisateur.getEmail());
		if (count == 1) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_EMAIL_UNIQUE);
			return false;
		}
		return true;
	}
	
	private boolean validerUniquePseudo(Utilisateur utilisateur, BusinessException be) {
		int count = utilisateurDAO.uniquePseudo(utilisateur.getPseudo());
		if (count == 1) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_PSEUDO_UNIQUE);
			return false;
		}
		return true;
	}

	private boolean isPseudoDisponible(String pseudo, BusinessException be) {
		System.out.println("isPseudoDisponible");
		int pseudoExist = 1;
		try {
			pseudoExist = utilisateurDAO.uniquePseudo(pseudo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//				throw be;
			e.printStackTrace();

		}
		return (pseudoExist == 0) ? true : false;
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
