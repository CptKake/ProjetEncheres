package fr.eni.tp.bll;

import fr.eni.tp.bo.Categorie;
import fr.eni.tp.bo.Utilisateur;

public interface AdminService {

	void banAccount(Utilisateur user);
	
	void deleteAccount(Utilisateur user);
	
	Categorie createCat(Categorie category);
	
	void modifyCat(Categorie category);
	
	void deleteCat(Categorie category);
	
}
