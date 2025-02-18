package fr.eni.tp.bll;

import fr.eni.tp.bo.Retrait;


public interface RetraitService {

	
	 Retrait getRetraitForArticle(int articleId);
	 
	 void saveRetrait(Retrait retrait, int articleId);
}
