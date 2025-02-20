package fr.eni.tp.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.tp.bll.EnchereService;
import fr.eni.tp.bll.RetraitService;
import fr.eni.tp.bll.UtilisateurService;
import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Categorie;
import fr.eni.tp.bo.Enchere;
import fr.eni.tp.bo.Retrait;
import fr.eni.tp.bo.Utilisateur;

@Controller
@SessionAttributes({"catSession"})
public class ArticleController {

	private EnchereService enchereService;
	private UtilisateurService utilisateurService;
	private RetraitService retraitService;
	
	public ArticleController(EnchereService enchereService, UtilisateurService utilisateurService, RetraitService retraitService) {
		this.enchereService = enchereService;
		this.utilisateurService = utilisateurService;
		this.retraitService = retraitService;
	}

	@GetMapping("/encheres")
	public String afficherArticles(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		
		List<Categorie> categories = this.enchereService.getAllCategories(); 
		model.addAttribute("categories", categories);
		
		List<Article> articles = this.enchereService.allArticles();
		model.addAttribute("articles", articles);
		
		for (Article art : articles) {
			art.setUser(utilisateurService.profileByNbUser(art.getUser().getNbUser()));
		}
		if (userDetails != null) {
		  Utilisateur user = utilisateurService.profileByPseudo(userDetails.getUsername());
		    model.addAttribute("user", user);
		}
		
		return "view-articles";
	}
	
	@PostMapping("/encheres")
	public String trierArticles (@RequestParam("choixEnch") String choixEnch, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		
		Utilisateur user = null;
		
		List<Categorie> categories = this.enchereService.getAllCategories(); 
		model.addAttribute("categories", categories);
		
		if (userDetails != null) {
			user = utilisateurService.profileByPseudo(userDetails.getUsername());
		    model.addAttribute("user", user);
		}
		
		List<Article> articles = whichFilter(choixEnch, user);
		model.addAttribute("articles", articles);
		
		for (Article art : articles) {
			art.setUser(utilisateurService.profileByNbUser(art.getUser().getNbUser()));
		}
		
		
		return "view-articles";
	}
		
	@GetMapping("/encheres/details")
	public String afficherDetailArticle(@RequestParam(name="idArt",
			required = true) int idArt, Model model) {
		
		Article art = this.enchereService.readArticle(idArt);
		Categorie cat = this.enchereService.getCatById(art.getCategory().getNumber());
		Utilisateur user = this.utilisateurService.profileByNbUser(art.getUser().getNbUser());
		Retrait retrait = this.retraitService.getRetraitForArticle(idArt);
		Enchere ench = null;
		if (enchereService.findEncheresByArt(art).size() != 0) {
			ench = this.enchereService.bestEnchere(art);
			ench.setNbUser(utilisateurService.profileByNbUser(art.getUser().getNbUser()));
		}
		 
		model.addAttribute("cat", cat);
		model.addAttribute("user", user);
		model.addAttribute("art", art);
		model.addAttribute("retrait", retrait);
		model.addAttribute("ench", ench);
		
		return "view-article-details";
	}
	
	@ModelAttribute("catSession")
	public List<Categorie> chargerCatEnSession() {
		return this.enchereService.getAllCategories();	
	}

	public List<Article> whichFilter(String value, Utilisateur user) {
		List<Article> articles = null;
		switch (value) {
			
			case "open": {
				articles = this.enchereService.enchOpen();
				break;
			}
			case "enCours": {
				articles = this.enchereService.enchBidded(user);
				break;
			}
			case "win": {
				articles = this.enchereService.enchWinned(user);
				break;
			}
			case "enCoursVen": {
				articles = this.enchereService.ventesEnCours(user);
				break;
			}
			case "nonStart": {
				articles = this.enchereService.ventesFutures(user);
				break;
			}
			case "ended": {
				articles = this.enchereService.ventesTerminees(user);
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + value);
			}
		return articles;
	}
}
