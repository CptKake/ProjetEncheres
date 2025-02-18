
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
import fr.eni.tp.bo.Utilisateur;
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
		model.addAttribute("categories", categories);
		
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
            enchereService.createArticle(art);
            return "redirect:/encheres";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "vente";
        }

	}
	
	@PostMapping("/encherir")
	public String faireEnchere (@ModelAttribute("article") @Valid Article art, BindingResult result, @AuthenticationPrincipal UserDetails userDetails, Model model) {
		
		if (result.hasErrors()) {
            return "view-article-details";
        }

        try {
        	/*
        	//vérification enchère meilleures que les précédentes
        	if (enchere > art.getSellPrice()) {
				Utilisateur user = utilisateurService.profileByPseudo(userDetails.getUsername());
				user.setCredit()
			}
        	*/
        	//vérification présence d'enchère antérieures
        	if (enchereService.bestEnchere(art) != null) {
        		//récupération utilisateur avec la plus haute enchère
        		Utilisateur bestBidder = enchereService.bestEnchere(art).getNbUser();
        		//remboursement de l'enchère précedente
        		bestBidder.setCredit(bestBidder.getCredit() + enchereService.bestEnchere(art).getBidAmount());
        		utilisateurService.modifyAccount(bestBidder);
			}
        	
        	
        	
            return "redirect:/encheres/details";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "view-article-details";
        }

	}
	
}
