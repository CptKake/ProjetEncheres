package fr.eni.tp.dal;


import fr.eni.tp.bo.Retrait;


public interface RetraitDAO {

	Retrait takeAdress(int articleId);

	void saveRetrait(Retrait retrait, int articleId);

	void updateRetrait(Retrait retrait, int articleId);

	void deleteRetrait(int articleId);
	
	
	
}
