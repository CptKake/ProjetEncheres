package fr.eni.tp.bll;

import java.util.List;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Categorie;
import fr.eni.tp.bo.Enchere;
import fr.eni.tp.bo.Utilisateur;

public interface AuctionService {

	List<Enchere> getEncheres();
	
	void bid(int sum);
	
	Article buy(Utilisateur user);

	List<Article> getSells(Utilisateur user);
	
	Article sell(Utilisateur user, Categorie category, Article article);
	
	void cancelAuction(Article article);
	
}
