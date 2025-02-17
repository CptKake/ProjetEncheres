
package fr.eni.tp.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.tp.bll.EnchereService;
import fr.eni.tp.bll.UtilisateurService;
import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Categorie;
import jakarta.validation.Valid;

@Controller
public class EnchereController {

	private EnchereService enchereService;
	private UtilisateurService utilisateurService;

	public EnchereController(EnchereService enchereService, UtilisateurService utilisateurService) {
		this.enchereService = enchereService;
		this.utilisateurService = utilisateurService;
	}

	@GetMapping("/vendre")
	public String vendreArticle (Model model) {
		List<Categorie> categories = this.enchereService.getAllCategories(); 
		model.addAttribute("category", categories);
		
		model.addAttribute("article", new Article());
		
		return "vente";
	}
	
	@PostMapping("/vendre")
	public String creerVente (@ModelAttribute("article") @Valid Article art, BindingResult result,@AuthenticationPrincipal UserDetails userDetails, Model model) {
		
		if (result.hasErrors()) {
            return "vente";
        }

        try {
        	art.setUser(utilisateurService.profileByPseudo(userDetails.getUsername()));
        	art.setCategory(enchereService.getCatById(10));
            enchereService.createArticle(art);
            return "redirect:/encheres";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "vente";
        }

	}
	
	@PostMapping("/encherir")
	public String faireEnchere (@ModelAttribute("article") @Valid Article art, BindingResult result,@AuthenticationPrincipal UserDetails userDetails, Model model) {
		
		if (result.hasErrors()) {
            return "view-article-details";
        }

        try {
        	art.setUser(utilisateurService.profileByPseudo(userDetails.getUsername()));
        	art.setCategory(enchereService.getCatById(10));
            enchereService.createArticle(art);
            return "redirect:/encheres/details";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "view-article-details";
        }

	}
	
}
