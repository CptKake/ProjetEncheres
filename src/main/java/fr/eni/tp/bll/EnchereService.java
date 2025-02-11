package fr.eni.tp.bll;

import java.util.List;
import java.util.Optional;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Enchere;
import fr.eni.tp.bo.Utilisateur;

public interface EnchereService {

	List<Enchere> getEncheres();
	
	void bid(Utilisateur user, int sum, Article art);
	
	Article buy(Utilisateur user, Article art);

	List<Article> getSells(Utilisateur user);
	
	void sell(Article article);
	
	void cancelAuction(Article article);

	Optional<Enchere> getEnchereById(int id);
	
}
