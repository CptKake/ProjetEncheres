
package fr.eni.tp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.tp.bll.EnchereService;
import fr.eni.tp.bll.UtilisateurService;
import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Categorie;
import fr.eni.tp.bo.Enchere;
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
	public String faireEnchere (@RequestParam("idBid") int montant, @RequestParam(name="idArt") int idArt, @AuthenticationPrincipal UserDetails userDetails, Model model) {

        try {
    		//récupération du user qui encherit
			Utilisateur user = utilisateurService.profileByPseudo(userDetails.getUsername());
        	Article art = enchereService.readArticle(idArt);
        	
        	Enchere bid = new Enchere();
        	bid.setAuctionDate(LocalDate.now());
        	bid.setBidAmount(montant);
        	bid.setNbArticle(art);
        	bid.setNbUser(user);
        	
        	//vérification enchère meilleures que les précédentes et toujours en cours
        	if (bid.getBidAmount() > art.getSellPrice() && user.getCredit() >= bid.getBidAmount() && bid.getAuctionDate().isBefore(art.getBidEnd())) {
        		System.err.println("##### ASSEZ DE CREDITS ET DATE BONNE #####");
        		//déduction des crédits de l'enchère
				user.setCredit(user.getCredit() - bid.getBidAmount());
				utilisateurService.updateCredits(user);
				
				List<Enchere> encheres = enchereService.findEncheresByArt(art);
				System.err.println("##### ENCHERES.size = " + encheres.size());
				
				//vérification présence d'enchère antérieures
	        	if (encheres.size() != 0) {
	        		System.err.println("##### PASSAGE PAR ENCHERE PRECEDENTE #####");
	        		
	        		//récupération utilisateur avec la plus haute enchère
	        		Enchere bestBid = enchereService.bestEnchere(art);
	        		System.err.println("##### best bid amount = " + bestBid.getBidAmount());
	        		Utilisateur bestBidder = bestBid.getNbUser();
	        		System.err.println("##### best bidder pseudo= " + bestBidder.getPseudo());
	        		
	        		//remboursement de l'enchère précedente
	        		bestBidder.setCredit(bestBidder.getCredit() + enchereService.bestEnchere(art).getBidAmount());
	        		utilisateurService.updateCredits(bestBidder);
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
        	
            return "redirect:/encheres";
        } catch (IllegalArgumentException e) {
        	e.printStackTrace();
        	
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
