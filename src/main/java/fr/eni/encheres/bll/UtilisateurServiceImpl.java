package fr.eni.encheres.bll;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.AdresseDAO;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exceptions.BusinessCode;
import fr.eni.encheres.exceptions.BusinessException;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	private Logger logger = (Logger) LoggerFactory.getLogger("fr.eni.encheres.dev");

	private UtilisateurDAO utilisateurDAO;
	private AdresseDAO adresseDAO;
	
	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, AdresseDAO adresseDAO) {
		this.utilisateurDAO = utilisateurDAO;
		this.adresseDAO = adresseDAO;
	}

	private boolean isValidUtilisateur(Utilisateur utilisateur) {
		BusinessException be = new BusinessException();
		boolean isValid = true;
		isValid &= validerUtilisateur(utilisateur, be);
		isValid &= validerPseudo(utilisateur, be);
		isValid &= validerNom(utilisateur, be);
		isValid &= validerPrenom(utilisateur, be);
		isValid &= validerEmail(utilisateur, be);
		isValid &= validerTelephone(utilisateur, be);
		if (isValid) {
			return true;
		} else {
			throw be;
		}
	}
	
	private boolean isValidPasswordUtilisateur(Utilisateur utilisateur) {
		BusinessException be = new BusinessException();
		boolean isValid = true;
		isValid &= validerMotDePasse(utilisateur, be);
		if (isValid) {
			return true;
		} else {
			throw be;
		}
	}
	
	private boolean isValidPseudoEmailUtilisateur(Utilisateur utilisateur) {
		BusinessException be = new BusinessException();
		boolean isValid = true;
		isValid &= isPseudoDisponible(utilisateur.getPseudo(), be);
		isValid &= validerUniqueEmail(utilisateur, be);
		if (isValid) {
			return true;
		} else {
			throw be;
		}
	}
	
	private boolean isValidNewEmailUtilisateur(Utilisateur utilisateur) {
		BusinessException be = new BusinessException();
		boolean isValid = true;
		isValid &= validerNewUniqueEmail(utilisateur, be);
		if (isValid) {
			return true;
		} else {
			throw be;
		}
	}
	
	@Override
	@Transactional
	public void creerUtilisateur(Utilisateur utilisateur) {
		System.out.println("BLL utilisateur creerUtilisateur");
		if (isValidUtilisateur(utilisateur) 
				&& isValidPseudoEmailUtilisateur(utilisateur)
				&& isValidPasswordUtilisateur(utilisateur)
				) {
			System.out.println("BLL utilisateur creerUtilisateur valid");
			adresseDAO.create(utilisateur.getAdresse());
			utilisateurDAO.create(utilisateur);
		} 
	}
	
	@Override
	@Transactional
	public void modifierUtilisateur(Utilisateur utilisateur) {
		System.out.println("BLL utilisateur creerUtilisateur");
		if (isValidUtilisateur(utilisateur)
				&& isValidNewEmailUtilisateur(utilisateur)) {
			System.out.println("BLL utilisateur creerUtilisateur valid");
			adresseDAO.update(utilisateur.getAdresse());
			utilisateurDAO.update(utilisateur);
		} 
	}

	@Override
	public void supprimerUtilisateur(Utilisateur utilisateur) {
		System.out.println("BLL utilisateur supprimerUtilisateur");
		if (isValidUtilisateur(utilisateur)) {
			System.out.println("BLL utilisateur supprimerUtilisateur accepted");
			Adresse adresse = utilisateur.getAdresse();
			utilisateurDAO.delete(utilisateur);
			adresseDAO.delete(adresse);
			//@TODO : nettoyer article et enchères
		} 
	}
	
	@Override
	public Utilisateur consulterUtilisateurParPseudo(String pseudo) {
		Utilisateur utilisateur = utilisateurDAO.ReadByPseudo(pseudo);
		try {
			Adresse adresse = adresseDAO.read(utilisateur.getAdresse().getNoAdresse());
			utilisateur.setAdresse(adresse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return utilisateur;
	}

	@Override
	public void modifierMdp(String pseudo, String motDePasse) {
		System.out.println("BLL utilisateur modifierMdp");
		if (true) { //@TODO valider correctement le MdP
			System.out.println("BLL utilisateur modifierMdp valid");
			utilisateurDAO.updatePassword(pseudo, motDePasse);
		} 
		
		
		
	}
	
	@Override
	public String getMdpParPseudo(String pseudo) {
		System.out.println("BLL utilisateur getMdpParPseudo");
		return utilisateurDAO.readPasswordByPseudo(pseudo);
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
		if (utilisateur.getTelephone() != null) {
			if( utilisateur.getTelephone().length() > 15) {
				be.add(BusinessCode.VALIDATION_UTILISATEUR_PHONE_SIZE);
				return false;
			}
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
	
	private boolean validerNewUniqueEmail(Utilisateur utilisateur, BusinessException be) {
		int count = utilisateurDAO.uniqueNewEmail(utilisateur.getEmail(), utilisateur.getPseudo());
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
