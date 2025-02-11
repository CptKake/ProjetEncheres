package fr.eni.tp.dal;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Utilisateur;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {
	
	private static final String INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe) VALUES (:pseudo, :nom, :prenom, :email, :telephone, :rue, :code_postal, :ville, :mot_de_passe)";
	private static final String SELECT_BY_PSEUDO = "SELECT pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE pseudo = :pseudo";
	private static final String DELETE = "DELETE FROM UTILISATEURS WHERE pseudo= :pseudo ";
	private static final String UPDATE = "UPDATE UTILISATEURS SET pseudo = :pseudo, nom = :nom, prenom = :prenom, email = :email, telephone = :telephone, rue = :rue, code_postal = :code_postal, ville = :ville, mot_de_passe = :mot_de_passe WHERE pseudo= :pseudo ";
	private static final String COUNT_PSEUDO = "SELECT COUNT(*) FROM UTILISATEURS WHERE pseudo LIKE (:pseudo)";
	private static final String COUNT_EMAIL = "SELECT COUNT(*) FROM UTILISATEURS WHERE email LIKE (:email)";
	
	
	
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	
	public UtilisateurDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public void createUser(Utilisateur user) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", user.getPseudo());
		namedParameters.addValue("nom", user.getLastName());
		namedParameters.addValue("prenom", user.getFirstName());
		namedParameters.addValue("email", user.getEmail());
		namedParameters.addValue("telephone", user.getPhone());
		namedParameters.addValue("code_postal", user.getPostalCode());
		namedParameters.addValue("ville", user.getCity());
		namedParameters.addValue("mot_de_passe", user.getPassword());
	
	
		namedParameterJdbcTemplate.update(INSERT, namedParameters);
	
	}

	@Override
	public void deleteUser(String pseudo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(Utilisateur user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Utilisateur readUser(String pseudo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countByEmail(String email) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countByPseudo(String pseudo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void sellArticle(Article article) {
		// TODO Auto-generated method stub
		
	}

}
