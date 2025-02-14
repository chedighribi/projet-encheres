package fr.eni.encheres.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import ch.qos.logback.classic.Logger;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bll.UtilisateurServiceImpl;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.exceptions.BusinessCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping({ "/", "/profil" })
@SessionAttributes("membreEnSession")
public class UtilisateurController {

	private Logger logger = (Logger) LoggerFactory.getLogger("fr.eni.encheres.dev");

	private UtilisateurService utilisateurService;

	/**
	 * @param utilisateurService
	 */
	public UtilisateurController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	@GetMapping("/")
	public String index(@ModelAttribute("membreEnSession") Utilisateur membreEnSession, Model model) {
		logger.trace("lancement index");
		logger.trace("---------------------");
		logger.trace(model.toString());
		return "redirect:/articles";
	}

	private Utilisateur getMembreDetails(String pseudo) {
		logger.trace("getMembreDetails : " + pseudo);
		Utilisateur utilisateur = utilisateurService.consulterUtilisateurParPseudo(pseudo);
		logger.trace("---------------------");
		logger.trace(utilisateur.toString());
		return utilisateur;
	}

	private Utilisateur getMembreEnSessionDetails(String pseudo) {
		logger.trace("getMembreEnSessionDetails : " + pseudo);
		return getMembreDetails(pseudo);
	}

	@GetMapping("/error")
	String afficherErreur(@ModelAttribute("membreEnSession") Utilisateur membreEnSession, Model model) {
		logger.trace("Controller utilisateur afficherErreur");
		logger.trace(membreEnSession.getPseudo());
		model.addAttribute(getMembreEnSessionDetails(membreEnSession.getPseudo()));
		return "error";
	}
	
	@GetMapping("/profil")
	String afficherProfil(@ModelAttribute("membreEnSession") Utilisateur membreEnSession, Model model) {
		logger.trace("Controller utilisateur afficherProfil");
		logger.trace(membreEnSession.getPseudo());
		model.addAttribute(getMembreEnSessionDetails(membreEnSession.getPseudo()));
		return "view-profil";
	}

	@GetMapping("/profil/view")
	String afficherProfil(@RequestParam(name = "utilisateurPseudo", required = true) String utilisateurPseudo, Model model) {
		logger.trace("Controller utilisateur afficherProfil");
		logger.trace("Controller utilisateur afficherProfil");
		logger.trace(utilisateurPseudo);
		logger.trace(utilisateurPseudo);
		try {
			model.addAttribute(getMembreDetails(utilisateurPseudo));
			return "view-profil";
		} catch (Exception e) {
			model.addAttribute("messageErreur", BusinessCode.MESSAGE_ERREUR_PROFILE_NON_TROUVE);
			return "view-profil";
		}
	}

	@GetMapping("/creer")
	public String creerProfil(Model model) {
		logger.trace("view-profil-creer");
		Utilisateur personne = new Utilisateur();
		model.addAttribute("personne", personne);
		return "view-profil-creer";
	}

	@PostMapping("/creer")
	public String creerProfil(@Valid @ModelAttribute("personne") Utilisateur utilisateur, BindingResult bindingResult, HttpServletRequest request) {
		logger.trace("Post utilisateur creerProfil");
		if (bindingResult.hasErrors()) {
			logger.warn("Erreur de saisie ");
			logger.warn(" " + bindingResult);
			return "view-profil-creer";
		} else {
			logger.trace("creer utilisateur");
			String motDePasseClair = utilisateur.getMotDePasse();
			utilisateur.setMotDePasse(PasswordEncoderFactories	.createDelegatingPasswordEncoder()
																.encode(utilisateur.getMotDePasse()));
			utilisateurService.creerUtilisateur(utilisateur);
			logger.trace(utilisateur.getPseudo() + " -- " + utilisateur.getMotDePasse());
			try {
				request.login(utilisateur.getEmail(), motDePasseClair);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}

		return "redirect:/profil/session";
	}

	@GetMapping("/modifier")
	public String modifierProfil(Model model) {
		logger.trace("view-profil-modifier");
		logger.trace(model.toString());
		logger.trace("---------------------");
		Utilisateur membreEnSession = (Utilisateur) model.getAttribute("membreEnSession");
		model.addAttribute("personne", getMembreEnSessionDetails(membreEnSession.getPseudo()));
		return "view-profil-modifier";
	}

	@PostMapping("/modifier")
	public String modifierProfil(@Valid @ModelAttribute("personne") Utilisateur utilisateur, BindingResult bindingResult) {
		logger.trace("Post utilisateur modifierProfil");
		logger.trace("Post utilisateur modifierProfil");
		logger.trace(utilisateur.toString());
		logger.trace("---------------------");
		if (bindingResult.hasErrors()) {
			logger.warn("Erreur de saisie ");
			logger.warn(" " + bindingResult);
			return "view-profil-modifier";
		} else {
			logger.trace("modifier utilisateur");
			utilisateurService.modifierUtilisateur(utilisateur);
		}
		return "redirect:/profil";
	}

	@GetMapping("/profil/modifier/password")
	public String modifierPassword(Model model) {
		logger.trace(" Controller utilisateur modifierPassword");
		logger.trace(model.toString());
		logger.trace("---------------------");
		Utilisateur membreEnSession = (Utilisateur) model.getAttribute("membreEnSession");
		Utilisateur utilisateur = getMembreEnSessionDetails(membreEnSession.getPseudo());
		model.addAttribute("personne", utilisateur);
		model.addAttribute("oldMotDePasse", utilisateur.getMotDePasse());
		model.addAttribute("newMotDePasse", "");
		model.addAttribute("confirmation", "");
		return "view-password-modifier";
	}

	@PostMapping("/profil/modifier/password")
	public String modifierPassword(@RequestParam(name = "oldMotDePasse", required = true) String oldMotDePasse,
			@Valid @RequestParam(name = "motDePasse", required = true) String motDePasse, Model model) {
		logger.trace("Post utilisateur modifierPassword");
		logger.trace("Post utilisateur modifierProfil");
		logger.trace(oldMotDePasse);
		logger.trace(motDePasse);
		logger.trace("---------------------");
		if (false /*bindingResult.hasErrors()*/) {
			logger.warn("Erreur de saisie ");
//			logger.warn(" " + bindingResult);
//			logger.trace("Erreur de saisie " + bindingResult);
//			return "view-password-modifier";
		} else {
			logger.trace("modifier password");
			logger.trace("modifier password");
			Utilisateur membreEnSession = (Utilisateur) model.getAttribute("membreEnSession");
			String motDePasseActuel = utilisateurService.getMdpParPseudo(membreEnSession.getPseudo());
			
			logger.trace("membreEnSession " + membreEnSession);
			logger.trace("oldMotDePasse clair " + oldMotDePasse);
			logger.trace("motDePasseActuel " + motDePasseActuel);
			logger.trace("test => " + PasswordEncoderFactories.createDelegatingPasswordEncoder().matches(oldMotDePasse, motDePasseActuel));
			
			if (PasswordEncoderFactories.createDelegatingPasswordEncoder().matches(oldMotDePasse, motDePasseActuel)) {
				logger.trace("modification du mot de passe possible " + motDePasse);
				utilisateurService.modifierMdp(membreEnSession.getPseudo(),PasswordEncoderFactories	.createDelegatingPasswordEncoder()
						.encode(motDePasse));				
			} else {
				logger.trace("L'ancien mot de passe est erroné");
				return "redirect:/profil/modifier/password";
//				return "view-password-modifier";
			}
		}
		return "redirect:/profil";
	}
	
	private boolean hasRole(String role) {
		logger.trace("controller utilisateur hasRole");  
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
		  SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		  boolean hasRole = false;
		  for (GrantedAuthority authority : authorities) {
		     hasRole = authority.getAuthority().equals(role);
		     if (hasRole) {
			  break;
		     }
		  }
		  return hasRole;
		}  
	
	@GetMapping("/profil/supprimer/utilisateur")
	public String supprimerProfil(@RequestParam(name = "utilisateurPseudo", required = true) String utilisateurPseudo, Model model, Authentication authentication) {
		logger.trace("Controller utilisateur supprimerProfil");
		logger.trace(model.toString());
		logger.trace(utilisateurPseudo);
		logger.trace("---------------------");
		
		Utilisateur membreEnSession = (Utilisateur) model.getAttribute("membreEnSession");
		Utilisateur utilisateur = getMembreEnSessionDetails(membreEnSession.getPseudo());
		if (utilisateur.getPseudo().equals("coach_admin")) {
			logger.trace("a le role Admin");
			Utilisateur utilisateurASupprimer = utilisateurService.consulterUtilisateurParPseudo(utilisateurPseudo);
			utilisateurService.supprimerUtilisateur(utilisateurASupprimer);
			return "redirect:/voirListeUtilisateurs";
		} else if (membreEnSession.getPseudo().equals(utilisateurPseudo)) {			
			utilisateurService.supprimerUtilisateur(utilisateur);
			return "redirect:/logout";
		} else {
			return "redirect:/profil/modifier";
//			return "redirect:/error";
		}
	}
	
	@GetMapping("/profil/desactiver/utilisateur")
	public String desactiverProfil(@RequestParam(name = "utilisateurPseudo", required = true) String utilisateurPseudo, Model model, Authentication authentication) {
		logger.trace("Controller utilisateur desactiverProfil");
		logger.trace("----- NOT IMPLEMENTED YET  -----");
		//@TODO : implémenter la déconnexion
		model.addAttribute("customError", "La fonction n'est pas encore implémenté");
		return "error";
	}
	
	@GetMapping("/profil/anonymiser/utilisateur")
	public String anonymiseProfil(@RequestParam(name = "utilisateurPseudo", required = true) String utilisateurPseudo, Model model, Authentication authentication) {
		logger.trace("Controller utilisateur anonymiseProfil");
		logger.trace("----- NOT IMPLEMENTED YET  -----");
		//@TODO : implémenter la Anonymisation
		model.addAttribute("customError", "La fonction n'est pas encore implémenté");
		return "error";
	}
	
	@GetMapping("/voirListeUtilisateurs")
	public String voirListeUtilisateurs(Model model) {
		logger.trace("controller utilisateur voirListeUtilisateurs");
		List<Utilisateur> listeUtilisateurs = utilisateurService.consulterUtilisateurs();
		logger.trace(listeUtilisateurs.toString());
		model.addAttribute("listeUtilisateurs", listeUtilisateurs);
		return "view-liste-utilisateurs";
	}
	
	@GetMapping("/session")
	public String connexion(@ModelAttribute("membreEnSession") Utilisateur membreEnSession, Principal principal) {
		Utilisateur aCharger = utilisateurService.consulterUtilisateurParPseudo(principal.getName());
		logger.trace("---------------------");
		logger.trace(aCharger.toString());
		if (aCharger != null) {
			membreEnSession.setPseudo(aCharger.getPseudo());
			membreEnSession.setNom(aCharger.getNom());
			membreEnSession.setPrenom(aCharger.getPrenom());
			membreEnSession.setEmail(aCharger.getEmail());
			membreEnSession.setTelephone(aCharger.getTelephone());
			membreEnSession.setAdresse(aCharger.getAdresse());
			membreEnSession.setCredit(aCharger.getCredit());
		}
		logger.trace("___________________");
		logger.trace(membreEnSession.toString());
		return "redirect:/";
	}

	@ModelAttribute("membreEnSession")
	public Utilisateur getUser() {
		return new Utilisateur();
	}

}
