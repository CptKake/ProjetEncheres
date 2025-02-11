package fr.eni.tp.bll;

import fr.eni.tp.bo.Utilisateur;

public interface AccountService {

	void createAccount(Utilisateur user);
	
	void deleteAccount(String pseudo);
	
	int getCredits(Utilisateur user);
	
	void modifyAccount(Utilisateur user);
	
	Utilisateur profileByPseudo(String pseudo);
	
}
