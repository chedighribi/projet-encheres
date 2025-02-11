package fr.eni.encheres.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.core.net.SyslogOutputStream;
import fr.eni.encheres.bll.AdresseService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Adresse;
import fr.eni.encheres.bo.Utilisateur;
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
	public String index(Model model) {
		System.out.println("lancement index");
		System.out.println("---------------------");
		System.out.println(model);
		return "redirect:/articles";
	}

	private Utilisateur getMembreDetails(String pseudo) {
		System.out.println("getMembreDetails : " + pseudo);
		Utilisateur utilisateur = utilisateurService.consulterUtilisateurParPseudo(pseudo);
		System.out.println("---------------------");
		System.out.println(utilisateur);
		return utilisateur;
	}

	private Utilisateur getMembreEnSessionDetails(String pseudo) {
		System.out.println("getMembreEnSessionDetails : " + pseudo);
		return getMembreDetails(pseudo);
	}

	@GetMapping("/profil")
	String afficherProfil(@ModelAttribute("membreEnSession") Utilisateur membreEnSession, Model model) {
		logger.trace("Controller utilisateur afficherProfil");
		logger.trace(membreEnSession.getPseudo());
		model.addAttribute(getMembreEnSessionDetails(membreEnSession.getPseudo()));
		return "view-profil";
	}

	@GetMapping("/profil/{utilisateurPseudo}")
	String afficherProfil(String utilisateurPseudo, Model model) {
		logger.trace("Controller utilisateur afficherProfil");
		logger.trace(utilisateurPseudo);
		model.addAttribute(getMembreDetails(utilisateurPseudo));
		return "view-profil";
	}

	@GetMapping("/creer")
	public String creerProfil(Model model) {
		System.out.println("view-profil-creer");
		Utilisateur personne = new Utilisateur();
		model.addAttribute("personne", personne);
		return "view-profil-creer";
	}

	@PostMapping("/creer")
	public String creerProfil(@Valid @ModelAttribute("personne") Utilisateur utilisateur, BindingResult bindingResult, Model model) {
		logger.trace("Post utilisateur creerProfil");
		if (bindingResult.hasErrors()) {
			logger.warn("Erreur de saisie ");
			logger.warn(" " + bindingResult);
			return "view-profil-creer";
		} else {
			logger.trace("creer utilisateur");
			utilisateur.setMotDePasse(PasswordEncoderFactories	.createDelegatingPasswordEncoder()
																.encode(utilisateur.getMotDePasse()));
			utilisateurService.creerUtilisateur(utilisateur);
			// Authenticate the user
			Authentication authentication =  new UsernamePasswordAuthenticationToken(
					utilisateur.getPseudo(), utilisateur.getMotDePasse());
			
			SecurityContext securityContext = SecurityContextHolder.getContext();
			
			securityContext.setAuthentication(authentication);
			System.out.println("##############################");
			System.out.println(securityContext.getAuthentication().getName());
			// Create a new session and add the security  
			SecurityContextHolder.setContext(securityContext);
			model.addAttribute("SPRING_SECURITY_CONTEXT", securityContext);
//			.setContext( session = request.getSession(true);
			
//			SecurityContextHolder.getContext(). .setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		}

		return "redirect:/profil/session";
	}

	@GetMapping("/modifier")
	public String modifierProfil(Model model) {
		System.out.println("view-profil-modifier");
		System.out.println(model);
		System.out.println("---------------------");
		Utilisateur membreEnSession = (Utilisateur) model.getAttribute("membreEnSession");
		model.addAttribute("personne", getMembreEnSessionDetails(membreEnSession.getPseudo()));
		return "view-profil-modifier";
	}

	@PostMapping("/modifier")
	public String modifierProfil(@Valid @ModelAttribute("personne") Utilisateur utilisateur,
			@Valid @ModelAttribute("adresse") Adresse adresse, BindingResult bindingResult) {
		System.out.println("view-profil-modifier");
		System.out.println(utilisateur);
		System.out.println(adresse);
		System.out.println("---------------------");
//		Utilisateur membreEnSession = (Utilisateur) model.getAttribute("membreEnSession");
//		model.addAttribute("personne", getMembreEnSessionDetails(membreEnSession.getPseudo()));
		return "view-profil-modifier";
	}

	@GetMapping("/session")
	public String connexion(@ModelAttribute("membreEnSession") Utilisateur membreEnSession, Principal principal) {
		Utilisateur aCharger = utilisateurService.findByEmail(principal.getName());
		System.out.println("---------------------");
		System.out.println(aCharger);
		if (aCharger != null) {
			membreEnSession.setPseudo(aCharger.getPseudo());
			membreEnSession.setCredit(aCharger.getCredit());

		}
		System.out.println("___________________");
		System.out.println(membreEnSession);
		return "redirect:/";
	}

	@ModelAttribute("membreEnSession")
	public Utilisateur getUser() {
		return new Utilisateur();
	}

}
