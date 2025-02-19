package fr.eni.tp.bll;

import fr.eni.tp.bo.Retrait;


public interface RetraitService {

	
	 Retrait getRetraitForArticle(int articleId);
	 
	 void saveRetrait(Retrait retrait, int articleId);
	 
	 void updateRetrait(Retrait retrait, int articleId);
	 
	 void deleteRetrait(int articleId);
}
