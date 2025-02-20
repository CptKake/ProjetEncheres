package fr.eni.tp.dal;

import java.util.List;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Utilisateur;

public interface ArticleDAO {

	void createArticle (Article article);
	
	void suppressArticle ( Article article);
	
	Article readArticle (int nbrArticle);
	
	void updateArticle(Article article);
	
	List<Article> findAll();
	
	List<Article> findEnCours();
	
	List<Article> findUserSells(Utilisateur user);

	List<Article> findUserFutureSells(Utilisateur user);

	List<Article> findUserPastSells(Utilisateur user);

	List<Article> findUserBidded(Utilisateur user);

	List<Article> findWinned(Utilisateur user);
	
}
