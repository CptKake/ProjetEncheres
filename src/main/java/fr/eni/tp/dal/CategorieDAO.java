package fr.eni.tp.dal;

import fr.eni.tp.bo.Categorie;

public interface CategorieDAO {

	void readCategorie(String name);
	
	Categorie findByName(String name);
	
	
}
