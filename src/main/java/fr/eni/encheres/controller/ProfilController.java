package fr.eni.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;

@Controller
@RequestMapping("/profil")
public class ProfilController {

	private UtilisateurService utilisateurService;
	
	/**
	 * @param utilisateurService
	 */
	public ProfilController(UtilisateurService utilisateurService) {
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
	
	@GetMapping("/modifier")
	public String modifierProfil(Model model) {
		System.out.println("view-profil-modifier");
		Utilisateur personne;
		personne = utilisateurService.findByEmail("coach@campus-eni.fr");
		
		System.out.println(personne);
		// Ajout de l'instance dans le modèle
		model.addAttribute("personne", personne);
		return "view-profil-modifier";
	}
}
