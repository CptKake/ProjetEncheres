package fr.eni.tp.bll;

import java.util.List;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Categorie;
import fr.eni.tp.bo.Enchere;
import fr.eni.tp.bo.Utilisateur;

public interface EnchereService {

	List<Enchere> userEncheres(Utilisateur user);
	
	List<Enchere> encheresEnCours(Utilisateur user);
	
	List<Enchere> findEncheresByArt(Article art);
	
	void createEnchere(Enchere enchere, Article art);
	
	void deleteEnchere(Enchere enchere);
	
	void updateEnchere(Enchere enchere);
	
	List<Article> encheresWinByUser(Utilisateur user);
	
	Enchere bestEnchere(Article art);
	
	void createArticle (Article article);
	
	void deleteArticle ( int idArt);
	
	void updateArticle(int idArt);
	
	Article readArticle (int nbrArticle);
	
	List<Article> allArticles();
		
	List<Categorie> getAllCategories();

	Categorie getCatById(int id);
	
	Categorie getCatByName(String libelle);

	List<Article> ventesEnCours(Utilisateur user);

	List<Article> ventesFutures(Utilisateur user);

	List<Article> ventesTerminees(Utilisateur user);

	List<Article> enchOpen();

	List<Article> enchBidded(Utilisateur user);

	List<Article> enchWinned(Utilisateur user);
}
