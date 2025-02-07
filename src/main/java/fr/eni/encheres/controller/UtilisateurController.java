package fr.eni.encheres.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;

@Controller
@RequestMapping({"/","/profil"})
@SessionAttributes("membreEnSession")
public class UtilisateurController {

	private UtilisateurService utilisateurService;
	
	/**
	 * @param utilisateurService
	 */
	public UtilisateurController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	@GetMapping
	String afficherProfil(Model model) {
		System.out.println("passe");
		
		return "view-encheres";
	}
	
	@GetMapping("/creer")
	public String creerProfil(Model model) {
		System.out.println("view-profil-creer");
		Utilisateur personne = new Utilisateur();
		model.addAttribute("personne", personne);
		return "view-profil-creer";
	}
	
	@PostMapping("/creer")
	public String creerProfil(@ModelAttribute("personne") Utilisateur utilisateur) {
		System.out.println("Post utilisateur");
		utilisateurService.add(utilisateur);
		return "redirect:/profil";
	}
	
	@GetMapping("/modifier")
	public String modifierProfil(Model model) {
		System.out.println("view-profil-modifier");
		Utilisateur personne;
		//TODO : changer la valeur en dur 
		personne = utilisateurService.findByEmail("coach@campus-eni.fr");
		
		System.out.println(personne);
		// Ajout de l'instance dans le modèle
		model.addAttribute("personne", personne);
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
		System.out.println(membreEnSession);
		return "redirect:/";
	}
	
	@ModelAttribute("membreEnSession")
	public Utilisateur getUser() {
		return new Utilisateur();
	}
	

}
