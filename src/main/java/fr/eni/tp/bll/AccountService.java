package fr.eni.tp.bll;

import fr.eni.tp.bo.Utilisateur;

public interface AccountService {

	Utilisateur createAccount();
	
	void deleteAccount(Utilisateur user);
	
	int getCredits(Utilisateur user);
	
	void login(String pseudo, String password);
	
	void logout();
	
	Utilisateur modifyAccount();
	
	Utilisateur profileById(int userNumber);
	
}
