package fr.eni.tp.dal;

import fr.eni.tp.bo.Categorie;

public interface CategorieDAO {

	Categorie readCategorie(String name);
	
	Categorie findById(int id);
	
	void createCategorie(Categorie cat);
}
