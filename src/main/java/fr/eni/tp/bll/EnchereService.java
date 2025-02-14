package fr.eni.tp.bll;

import java.util.List;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Categorie;
import fr.eni.tp.bo.Enchere;
import fr.eni.tp.bo.Utilisateur;

public interface EnchereService {

	List<Enchere> userEncheres(Utilisateur user);
	
	List<Enchere> encheresEnCours(Utilisateur user);
	
	void createEnchere(Enchere enchere, Article art);
	
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

	List<Categorie> getAllCategories();

	Categorie getCatById(int id);
	
	Categorie getCatByName(String libelle);
}
