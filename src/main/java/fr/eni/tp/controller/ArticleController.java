package fr.eni.tp.controller;

	import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.tp.bll.EnchereService;
import fr.eni.tp.bo.Article;

@Controller
public class ArticleController {

	private EnchereService enchereService;
	
	public ArticleController(EnchereService enchereService) {
		this.enchereService = enchereService;
	}



	@GetMapping("/encheres")
	public String afficherArticles(Model model) {
		
		List<Article> articles = this.enchereService.allArticles();
		model.addAttribute("articles", articles);
		
		return "view-articles";
	}
		
	@GetMapping("/encheres/details")
	public String afficherDetailArticle(@RequestParam(name="idArt",
			required = true) int idArt, Model model) {
		
		System.out.println("idArt = " + idArt);
		Article art = this.enchereService.readArticle(idArt);
		
		model.addAttribute("art", art);
		
		return "view-article-details";
	}
	
}
