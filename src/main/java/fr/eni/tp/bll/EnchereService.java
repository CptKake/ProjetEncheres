package fr.eni.tp.bll;

import java.util.List;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Enchere;
import fr.eni.tp.bo.Utilisateur;

public interface EnchereService {

	List<Enchere> userEncheres(Utilisateur user);
	
	List<Enchere> encheresEnCours(Utilisateur user);
	
	void createEnchere(Enchere enchere);
	
	void deleteEnchere(Enchere enchere);
	
	void updateEnchere(Enchere enchere);
	
	List<Article> encheresWinByUser(Utilisateur user);
	
	Enchere bestEnchere(Article art);
	
	void createArticle (Article article);
	
	void deleteArticle ( Article article);
	
	void updateArticle(Article article);
	
	Article readArticle (int nbrArticle);
	
	List<Article> allArticles();
	
	List<Article> VentesEnCours();
	
	List<Article> UserVentes(Utilisateur user);
	
}
