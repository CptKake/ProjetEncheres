package fr.eni.tp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes({"userEnSession"})
public class LoginController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}

}

