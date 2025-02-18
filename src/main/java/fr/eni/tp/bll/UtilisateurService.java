package fr.eni.tp.bll;

import fr.eni.tp.bo.Utilisateur;

public interface UtilisateurService {

	void createAccount(Utilisateur user);
	
	void deleteAccount(String pseudo);
	
	void modifyAccount(Utilisateur user);
	
	Utilisateur profileByPseudo(String pseudo);

	Utilisateur profileByNbUser(int nbUser);


}
