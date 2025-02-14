package fr.eni.tp.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import fr.eni.tp.bll.UtilisateurService;
import fr.eni.tp.bo.Utilisateur;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/profil")
public class UserController {

	private final UtilisateurService utilisateurService;

	public UserController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}
	
	@GetMapping("/inscription")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Utilisateur());
        return "inscription";
    }

    @PostMapping("/inscription")
    public String registerUser(@ModelAttribute("user") @Valid Utilisateur user, BindingResult result, Model model) {
       
    	 if (result.hasErrors()) {
             return "inscription";
         }

         try {
             utilisateurService.createAccount(user);
             return "redirect:/encheres";
         } catch (IllegalArgumentException e) {
             model.addAttribute("error", e.getMessage());
             return "inscription";
         }
        
    }
	
    
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "inscription";
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
	
	@PostMapping("/modifier")
	public String modifyProfile(@ModelAttribute("user") @Valid Utilisateur user, BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        return "modifier-profile";
	    }
	    this.utilisateurService.modifyAccount(user);
	    return "redirect:/encheres";
	}
		
	
	@GetMapping("/modifier")
	public String viewModify(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Récupérer le pseudo de l'utilisateur connecté
        String pseudo = userDetails.getUsername();

        // Utiliser la méthode readUser pour obtenir les informations de l'utilisateur
        Utilisateur user = utilisateurService.profileByPseudo(pseudo);

        // Ajouter les informations de l'utilisateur au modèle
        model.addAttribute("user", user);

        // Retourner le nom de la vue
        return "modifier-profile";
	}
	
	@PostMapping("/supprimer")
	public String deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
	    // Récupérer le pseudo de l'utilisateur connecté
	    String pseudo = userDetails.getUsername();

	    // Supprimer l'utilisateur
	    utilisateurService.deleteAccount(pseudo);

	    // Rediriger vers une page de confirmation ou d'accueil
	    return "redirect:/encheres";
	}

	    
	
}
