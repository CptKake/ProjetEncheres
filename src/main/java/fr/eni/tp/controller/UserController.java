package fr.eni.tp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import fr.eni.tp.bll.UtilisateurService;
import fr.eni.tp.bo.Utilisateur;

@Controller
@RequestMapping("/profil")
public class UserController {

	private final UtilisateurService utilisateurService;

	public UserController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}
	
	
	@GetMapping
	 public String userProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Récupérer le pseudo de l'utilisateur connecté
        String pseudo = userDetails.getUsername();

        // Utiliser la méthode readUser pour obtenir les informations de l'utilisateur
        Utilisateur user = utilisateurService.profileByPseudo(pseudo);

        // Ajouter les informations de l'utilisateur au modèle
        model.addAttribute("user", user);

        // Retourner le nom de la vue
        return "profil";
    }
		
	
	

	    
	
}
