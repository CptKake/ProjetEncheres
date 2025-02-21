
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
	
	@GetMapping("/modifvente")
	public String modifierArticle(@RequestParam("idArt") int idArt, Model model) {
	    
		List<Categorie> categories = enchereService.getAllCategories();
		
		Article art = this.enchereService.readArticle(idArt);
		Utilisateur user = this.utilisateurService.profileByNbUser(art.getUser().getNbUser());
		Retrait retrait = this.retraitService.getRetraitForArticle(idArt);
		art.setRetrait(retrait); 
		 
		System.err.println(art);
		System.err.println(categories );
			
		model.addAttribute("categories", categories);
		model.addAttribute("user", user);
		model.addAttribute("article", art);
		model.addAttribute("retrait", retrait);

	    return "modifier-vente";
	}
	
	@PostMapping("/vendre/update")
	public String updateArticle(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("idArt") int idArt, @ModelAttribute("article") @Valid Article art, BindingResult result, Model model) {
		art.setUser(utilisateurService.profileByPseudo(userDetails.getUsername()));
		System.err.println(art.getUser());
		if (result.hasErrors()) {
	        List<Categorie> categories = enchereService.getAllCategories();
	        model.addAttribute("categories", categories);
	        return "modifier-vente";
	    }

	    try {
	        // Récupérer l'article à partir de l'identifiant
	        Article existingArt = enchereService.readArticle(idArt);

	        // Mettre à jour les champs de l'article existant avec les nouvelles valeurs
	        existingArt.setName(art.getName());
	        existingArt.setDescription(art.getDescription());
	        existingArt.setBidStart(art.getBidStart());
	        existingArt.setBidEnd(art.getBidEnd());
	        existingArt.setInitPrice(art.getInitPrice());
	        existingArt.setSellPrice(art.getSellPrice());
	        existingArt.setCategory(art.getCategory());
	        existingArt.setUser(art.getUser());
	        
	        
	        enchereService.updateArticle(existingArt);
	        
	        Retrait retrait = retraitService.getRetraitForArticle(idArt);
	        if (retrait != null) {
	            retrait.setStreet(art.getRetrait().getStreet());
	            retrait.setPostalCode(art.getRetrait().getPostalCode());
	            retrait.setCity(art.getRetrait().getCity());
	            retraitService.updateRetrait(retrait, existingArt.getNumber());
	            
	            //la modif est bonne jusque là / KO dans DAO
	            System.err.println("article :" + existingArt);
	        }
	        List<Categorie> categories = enchereService.getAllCategories();
	        model.addAttribute("categories", categories);
	        Article updatedArt = this.enchereService.readArticle(existingArt.getNumber());
			Categorie cat = this.enchereService.getCatById(existingArt.getCategory().getNumber());
			Utilisateur user = this.utilisateurService.profileByNbUser(existingArt.getUser().getNbUser());
			Retrait newRetrait = this.retraitService.getRetraitForArticle(existingArt.getNumber());
			Enchere ench = null;
			
			 
			model.addAttribute("cat", cat);
			model.addAttribute("user", user);
			model.addAttribute("art", updatedArt);
			model.addAttribute("retrait", newRetrait);
			model.addAttribute("ench", ench);
			
	        return "view-article-details";
	    } catch (IllegalArgumentException e) {
	        model.addAttribute("error", e.getMessage());
	        List<Categorie> categories = enchereService.getAllCategories();
	        model.addAttribute("categories", categories);
	     
	        return "modifier-vente";
	    }
	
	}
	
	@PostMapping("/vendre/delete")
	public String deleteArt (@RequestParam("idArt") int idArt) {
		Article art = enchereService.readArticle(idArt);
		
		enchereService.deleteArticle(idArt);
	
		return "redirect:/encheres";
		
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
				
				//vérification présence d'enchère antérieures
	        	if (encheres.size() != 0) {
	        		System.err.println("##### PASSAGE PAR ENCHERE PRECEDENTE #####");
	        		
	        		//récupération utilisateur avec la plus haute enchère
	        		Enchere bestBid = enchereService.bestEnchere(art);
	        		Utilisateur bestBidder = utilisateurService.profileByNbUser(bestBid.getNbUser().getNbUser());
	        		
	        		//Deletion ancienne enchère user 
	        		Utilisateur enchereUser;
					for (Enchere enchere : encheres) {
						enchereUser = utilisateurService.profileByNbUser(enchere.getNbUser().getNbUser());
						if (enchereUser.getNbUser() == user.getNbUser()) {	
							System.err.println("##### PASSAGE PAR DELETION ENCHERE #####");
							enchereService.deleteEnchere(enchere);
						}
					}
	        		//remboursement de l'enchère précedente
					System.err.println("##### PASSAGE PAR REMBOURSEMENT #####");
	        		bestBidder.setCredit(bestBidder.getCredit() + art.getSellPrice());
	        		utilisateurService.updateCredits(bestBidder);
	        		//Création de la nouvelle enchère
	        		System.err.println("##### PASSAGE PAR CREATION ENCHERE #####");
	        		enchereService.createEnchere(bid, art);	
				} else {
					System.err.println("##### PAS D'ENCHERE PRECEDENTE #####");
					
					//Deletion ancienne enchère user 
					for (Enchere enchere : encheres) {
						if (enchere.getNbUser() == user) {
							enchereService.deleteEnchere(enchere);
						}
					}
					//Création de la nouvelle enchère
					System.err.println("##### PASSAGE PAR CREATION ENCHERE #####");
	        		enchereService.createEnchere(bid, art);
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
