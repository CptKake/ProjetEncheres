package fr.eni.tp.dal;

import java.util.List;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Enchere;
import fr.eni.tp.bo.Utilisateur;

public interface EnchereDAO {
	
	//voir les encheres en cours d'un user
	List<Enchere> finduserBid(Utilisateur user);
	
	List<Enchere> findEnCours(Utilisateur user);
	
	//vente remportée par le user
	List<Article> findWinByUser(Utilisateur user);
	
	Enchere findBestBid(Article art);
	
	void create(Enchere enchere);
	
	void delete(Enchere enchere);
	
	void update(Enchere enchere);

}
