
package fr.eni.tp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.tp.bll.EnchereService;
import fr.eni.tp.bll.RetraitService;
import fr.eni.tp.bll.UtilisateurService;
import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Categorie;
import fr.eni.tp.bo.Enchere;
import fr.eni.tp.bo.Retrait;
import fr.eni.tp.bo.Utilisateur;
import jakarta.validation.Valid;

@Controller
public class EnchereController {

	private EnchereService enchereService;
	private UtilisateurService utilisateurService;
	private RetraitService retraitService;

	public EnchereController(EnchereService enchereService, UtilisateurService utilisateurService, RetraitService retraitService) {
		this.enchereService = enchereService;
		this.utilisateurService = utilisateurService;
		this.retraitService = retraitService;
	}

	@GetMapping("/vendre")
	public String vendreArticle (Model model, @AuthenticationPrincipal UserDetails userDetails) {
		List<Categorie> categories = this.enchereService.getAllCategories(); 
		model.addAttribute("categories", categories);
		
		Utilisateur user = utilisateurService.profileByPseudo(userDetails.getUsername());
		
		
		 Article article = new Article();
		 Retrait retrait = new Retrait(user.getStreet(), user.getPostalCode(), user.getCity());
		 article.setRetrait(retrait); 
		 
		model.addAttribute("article", article);
		
		return "vente";
	}
	
	@GetMapping("/vendre/{idArt}")
	public String modifierArticle(@PathVariable("idArt") int idArt, Model model) {
	    Article article = enchereService.readArticle(idArt);
	    List<Categorie> categories = enchereService.getAllCategories();
System.err.println(article );
System.err.println(categories );
	    model.addAttribute("article", article);
	    model.addAttribute("categories", categories);

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
            retraitService.saveRetrait(art.getRetrait(), art.getNumber());
            return "redirect:/encheres";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "vente";
        }

	}
	
	@PostMapping("/encherir")
	public String faireEnchere (@RequestParam("idBid") int montant, @RequestParam(name="idArt",
			required = true) int idArt, @AuthenticationPrincipal UserDetails userDetails, Model model) {

        try {
    		//récupération du user qui encherit
			Utilisateur user = utilisateurService.profileByPseudo(userDetails.getUsername());
        	Article art = enchereService.readArticle(idArt);
			
        	Enchere bid = new Enchere();
        	bid.setAuctionDate(LocalDate.now());
        	bid.setBidAmount(montant);
        	bid.setNbArticle(art);
        	bid.setNbUser(user);
        	
        	System.err.println("bid date = " + bid.getAuctionDate());
        	System.err.println("bid montant = " + bid.getBidAmount());
        	System.err.println("bid art = " + bid.getNbArticle());
        	System.err.println("bid user = " + bid.getNbUser());
        	//vérification enchère meilleures que les précédentes et toujours en cours
        	if (bid.getBidAmount() > art.getSellPrice() && user.getCredit() >= bid.getBidAmount() && bid.getAuctionDate().isBefore(art.getBidEnd())) {
        		System.err.println("##### ASSEZ DE CREDITS ET DATE BONNE #####");
        		//déduction des crédits de l'enchère
				user.setCredit(user.getCredit() - bid.getBidAmount());
				utilisateurService.modifyAccount(user);
				System.err.println("COUCOU");
				
				//vérification présence d'enchère antérieures
	        	if (Objects.isNull(enchereService.bestEnchere(art))) {
	        		System.err.println("##### PASSAGE PAR ENCHERE PRECEDENTE #####");
	        		
	        		//récupération utilisateur avec la plus haute enchère
	        		Utilisateur bestBidder = enchereService.bestEnchere(art).getNbUser();
	        		//remboursement de l'enchère précedente
	        		bestBidder.setCredit(bestBidder.getCredit() + enchereService.bestEnchere(art).getBidAmount());
	        		utilisateurService.modifyAccount(bestBidder);
	        		//Création de la nouvelle enchère
	        		enchereService.createEnchere(bid, art);
	        		//màj du prix de vente de l'art
	        		art.setSellPrice(bid.getBidAmount());
	        		enchereService.updateArticle(art);
				} else {
					System.err.println("##### PAS D'ENCHERE PRECEDENTE #####");
					
					//Création de la nouvelle enchère
	        		enchereService.createEnchere(bid, art);
	        		//màj du prix de vente de l'art
	        		art.setSellPrice(bid.getBidAmount());
	        		enchereService.updateArticle(art);
				}
			} else {
				System.err.println("##### PAS ASSEZ DE CREDITS #####");
				
				Categorie cat = this.enchereService.getCatById(art.getCategory().getNumber());
	    		Utilisateur seller = this.utilisateurService.profileByNbUser(art.getUser().getNbUser());
	    		
	    		model.addAttribute("cat", cat);
	    		model.addAttribute("user", seller);
	    		model.addAttribute("art", art);
	    		
				return "view-article-details";
			}
        	
        	
        	
    		Categorie cat = this.enchereService.getCatById(art.getCategory().getNumber());
    		Utilisateur seller = this.utilisateurService.profileByNbUser(art.getUser().getNbUser());
    		
    		model.addAttribute("cat", cat);
    		model.addAttribute("user", seller);
    		model.addAttribute("art", art);
        	
            return "redirect:/encheres/details";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            Article art = enchereService.readArticle(idArt);
            Categorie cat = this.enchereService.getCatById(art.getCategory().getNumber());
    		Utilisateur seller = this.utilisateurService.profileByNbUser(art.getUser().getNbUser());
    		
    		model.addAttribute("cat", cat);
    		model.addAttribute("user", seller);
    		model.addAttribute("art", art);
    		
            return "view-article-details";
        }

	}
	
}
