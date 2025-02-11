package fr.eni.tp.dal;

import java.util.List;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Enchere;

public interface EnchereDAO {

	Enchere readAuctions(int nbArticle);
	
	List<Enchere> findAll();
	
	Enchere findUserBid(int nbUser);
	
	Enchere winByUser(int nbUser, int nbArticle);
	
	List<Article> findUsersAuction(int nbUser);
	
	
	
	
}
