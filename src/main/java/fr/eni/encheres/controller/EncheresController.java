package fr.eni.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/encheres")
public class EncheresController {
	@GetMapping
	public String afficherEncheres(Model model) {
		return "view-encheres";
	}
}
