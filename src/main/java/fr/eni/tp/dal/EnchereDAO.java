package fr.eni.tp.dal;

import java.util.List;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Enchere;

public interface EnchereDAO {

	Enchere readAuction(int nbArticle);
	
	//voir les encheres en cours d'un user
	List<Enchere> findAll();
	
	Enchere findUserBid(int nbUser);
	
	//vente remportée par le user
	Enchere winByUser(int nbUser, int nbArticle);
	
	//voir les ventes d'un user
	List<Article> findUsersAuction(int nbUser);
	
	
	
	
}
