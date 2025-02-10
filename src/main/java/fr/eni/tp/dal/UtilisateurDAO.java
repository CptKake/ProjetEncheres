package fr.eni.tp.dal;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Utilisateur;

public interface UtilisateurDAO {

	void createUser(Utilisateur user);
	
	void deleteUser(Utilisateur user);
	
	void updateUser(Utilisateur user);
	
	Utilisateur readUser(String pseudo);
	
	int countByEmail(String email);
	
	int countByPseudo(String pseudo);
	
	void sellArticle(Article article);
}
