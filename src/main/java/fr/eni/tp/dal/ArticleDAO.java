package fr.eni.tp.dal;

import fr.eni.tp.bo.Article;

public interface ArticleDAO {

	void createArticle (Article article);
	
	void suppressArticle ( Article article);
	
	void readArticle (int nbrArticle);
	
	
}
