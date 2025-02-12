package fr.eni.tp.bll;

import org.springframework.stereotype.Service;

import fr.eni.tp.bo.Utilisateur;
import fr.eni.tp.dal.UtilisateurDAO;

@Service
public class AccountServiceImpl implements UtilisateurService {

	private UtilisateurDAO userDAO;
	
	public AccountServiceImpl(UtilisateurDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public void createAccount(Utilisateur user) {
		// verification pseudo et email unique
		int valide = userDAO.countByEmail(user.getEmail()) + userDAO.countByPseudo(user.getPseudo());
		//création utilisateur en passant par la DAO si email et mdp sont uniques
		if (valide == 0) {
			userDAO.createUser(user);
		} else {
			System.err.println("mot de passe ou email déja existant");
		}
		
	}

	@Override
	public void deleteAccount(String pseudo) {
		// verification utilisateur existant
		int valide = userDAO.countByPseudo(pseudo);
		// suppression du compte si pseudo existant en BDD
		if (valide == 1) {
			userDAO.deleteUser(pseudo);
		} else {
			System.err.println("pseudo inexistant dans la BDD");
		}
		
	}

	@Override
	public int getCredits(Utilisateur user) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void modifyAccount(Utilisateur user) {
		userDAO.updateUser(user);
	}

	@Override
	public Utilisateur profileByPseudo(String pseudo) {
		Utilisateur user = userDAO.readUser(pseudo);
		return user;
	}

}
