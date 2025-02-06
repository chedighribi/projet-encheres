package fr.eni.encheres.controller.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
public class LoginController {
	@GetMapping("/login")
	String login() {
		return "view-login";
	}
	
}
