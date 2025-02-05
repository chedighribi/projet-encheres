package fr.eni.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profil")
public class ProfilController {

	@GetMapping
	String afficherProfil(Model model) {
		System.out.println("passe");
		
		return "view-encheres";
	}
	
	@GetMapping("/creer")
	public String creerProfil(Model model) {
		System.out.println("view-profil-creer");
		return "view-profil-creer";
	}
	
	@GetMapping("/modifier")
	public String modifierProfil(Model model) {
		System.out.println("view-profil-modifier");
		return "view-profil-modifier";
	}
}
