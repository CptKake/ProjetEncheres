package fr.eni.tp.bll;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.eni.tp.bo.Utilisateur;
import fr.eni.tp.dal.UtilisateurDAO;

@Service
public class AccountServiceImpl implements UtilisateurService {

	private UtilisateurDAO userDAO;
    private final PasswordEncoder passwordEncoder;
	
	public AccountServiceImpl(UtilisateurDAO userDAO, PasswordEncoder passwordEncoder) {
		this.userDAO = userDAO;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void createAccount(Utilisateur user) {
		if (userDAO.countByEmail(user.getEmail()) > 0) {
            throw new IllegalArgumentException("L'email est déjà utilisé.");
        }
        if (userDAO.countByPseudo(user.getPseudo()) > 0) {
            throw new IllegalArgumentException("Le pseudo est déjà utilisé.");
        }

        // Hachage du mot de passe avant de sauvegarder l'utilisateur
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Création de l'utilisateur
        userDAO.createUser(user);
		
		
	}

	@Override
	public void deleteAccount(String pseudo) {
		 // Vérification de l'existence de l'utilisateur
        if (userDAO.countByPseudo(pseudo) == 0) {
            throw new IllegalArgumentException("Pseudo inexistant dans la BDD.");
        }
			userDAO.deleteUser(pseudo);
	
		
		
	}


	@Override
	public void modifyAccount(Utilisateur user) {
		
		if (userDAO.countByNbUser(user.getNbUser()) == 0) {
            throw new IllegalArgumentException("Utilisateur inexistant.");
        }
		 String encodedPassword = passwordEncoder.encode(user.getPassword());
	        user.setPassword(encodedPassword);
		userDAO.updateUser(user);
	}

	
	@Override
	public Utilisateur profileByPseudo(String pseudo) {
		Utilisateur user = userDAO.readUserByPseudo(pseudo);
		return user;
	}
	
	@Override
	public Utilisateur profileByNbUser(int nbUser) {
		Utilisateur user = userDAO.readUserByNbUser(nbUser);
		return user;
	}

	@Override
	public void updateCredits(Utilisateur user) {
		userDAO.updateCredit(user);
	}

}
