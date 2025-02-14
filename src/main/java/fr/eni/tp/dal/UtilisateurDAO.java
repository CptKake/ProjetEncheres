package fr.eni.tp.dal;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Utilisateur;

public interface UtilisateurDAO {

	void createUser(Utilisateur user);
	
	void deleteUser(String pseudo);
	
	void updateUser(Utilisateur user);
	
	
	int countByEmail(String email);
	
	int countByPseudo(String pseudo);
	
	void sellArticle(Article article);

	Utilisateur readUser(int noUtilisateur);

	Utilisateur readUser(String pseudo);
}
