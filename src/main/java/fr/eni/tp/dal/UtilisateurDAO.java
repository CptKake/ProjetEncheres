package fr.eni.tp.dal;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Utilisateur;

public interface UtilisateurDAO {

	void createUser(Utilisateur user);
	
	void deleteUser(String pseudo);
	
	void updateUser(Utilisateur user);
	
	
	int countByEmail(String email);
	
	int countByPseudo(String pseudo);
	
	int countByNbUser(int noUtilisateur);
	
	void sellArticle(Article article);

	Utilisateur readUserByNbUser(int noUtilisateur);

	Utilisateur readUserByPseudo(String pseudo);
}
