package fr.eni.tp.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Utilisateur;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {
	
	private static final String INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (:pseudo, :nom, :prenom, :email, :telephone, :rue, :code_postal, :ville, :mot_de_passe, 0, '0') RETURNING no_utilisateur";
	private static final String SELECT_BY_PSEUDO = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE pseudo = :pseudo ";
	private static final String SELECT_BY_NO_UTILISATEUR = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE no_utilisateur = :no_utilisateur";
	private static final String DELETE = "DELETE FROM UTILISATEURS WHERE pseudo= :pseudo ";
	private static final String UPDATE = "UPDATE UTILISATEURS SET pseudo = :pseudo, nom = :nom, prenom = :prenom, email = :email, telephone = :telephone, rue = :rue, credit = :credit, code_postal = :code_postal, ville = :ville, mot_de_passe = :mot_de_passe WHERE no_utilisateur= :no_utilisateur ";
	private static final String COUNT_PSEUDO = "SELECT COUNT(*) FROM UTILISATEURS WHERE pseudo LIKE (:pseudo)";
	private static final String COUNT_EMAIL = "SELECT COUNT(*) FROM UTILISATEURS WHERE email LIKE (:email)";
	private static final String COUNT_BY_NB_USER = "SELECT COUNT(*) FROM UTILISATEURS WHERE no_utilisateur = :no_utilisateur";
	private static final String UPDATE_CREDIT = "UPDATE UTILISATEURS SET credit = :credit WHERE no_utilisateur = :idUser ";
	
	
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	
	public UtilisateurDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	
	// création d'un user dans la BDD
	@Override
	public void createUser(Utilisateur user) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", user.getPseudo());
		namedParameters.addValue("nom", user.getLastName());
		namedParameters.addValue("prenom", user.getFirstName());
		namedParameters.addValue("email", user.getEmail());
		namedParameters.addValue("telephone", user.getPhone());
		namedParameters.addValue("rue", user.getStreet());
		namedParameters.addValue("code_postal", user.getPostalCode());
		namedParameters.addValue("ville", user.getCity());
		namedParameters.addValue("mot_de_passe", user.getPassword());
	
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(INSERT, namedParameters, keyHolder);
		
		if(keyHolder != null && keyHolder.getKey() != null) {
		
			user.setNbUser(keyHolder.getKey().intValue());
		}
		
	}

	
	// Suppression d'un user
	@Override
	public void deleteUser(String pseudo) {
		 MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", pseudo);

        namedParameterJdbcTemplate.update(DELETE, namedParameters);
	    
	}

	// MAJ des données d'un user
	@Override
	public void updateUser(Utilisateur user) {
		 if (countByNbUser(user.getNbUser()) == 0) {
		        throw new IllegalArgumentException("Utilisateur inexistant.");
		    }
		 
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", user.getPseudo());
        namedParameters.addValue("nom", user.getLastName());
        namedParameters.addValue("prenom", user.getFirstName());
        namedParameters.addValue("email", user.getEmail());
        namedParameters.addValue("telephone", user.getPhone());
        namedParameters.addValue("rue", user.getStreet());
        namedParameters.addValue("code_postal", user.getPostalCode());
        namedParameters.addValue("ville", user.getCity());
        namedParameters.addValue("mot_de_passe", user.getPassword());
        namedParameters.addValue("no_utilisateur", user.getNbUser());
        namedParameters.addValue("credit", user.getCredit());

        namedParameterJdbcTemplate.update(UPDATE, namedParameters);
	}


	// afficher détails d'un user
	@Override
	public Utilisateur readUserByNbUser(int noUtilisateur) {
	    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	    namedParameters.addValue("no_utilisateur", noUtilisateur);

	    return namedParameterJdbcTemplate.queryForObject(SELECT_BY_NO_UTILISATEUR, namedParameters, new UtilisateurRowMapper());
	}
	
	@Override
	public Utilisateur readUserByPseudo(String pseudo) {
	    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	    namedParameters.addValue("pseudo", pseudo);

	    return namedParameterJdbcTemplate.queryForObject(SELECT_BY_PSEUDO, namedParameters, new UtilisateurRowMapper());
	}


	@Override
	public int countByNbUser(int noUtilisateur) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("no_utilisateur", noUtilisateur);

        return namedParameterJdbcTemplate.queryForObject(COUNT_BY_NB_USER, namedParameters, Integer.class);
    }

	
	// vérifier si email présent 1 fois (pour vérif email unique)
	@Override
	public int countByEmail(String email) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("email", email);

        return namedParameterJdbcTemplate.queryForObject(COUNT_EMAIL, namedParameters, Integer.class);
	}

	// vérifier si pseudo présent 1 fois (pour vérif pseudo unique)
	@Override
	public int countByPseudo(String pseudo) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", pseudo);

        return namedParameterJdbcTemplate.queryForObject(COUNT_PSEUDO, namedParameters, Integer.class);
	}

	@Override
	public void sellArticle(Article article) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateCredit(Utilisateur user) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("credit", user.getCredit());
		map.addValue("idUser", user.getNbUser());
		
		namedParameterJdbcTemplate.update(UPDATE_CREDIT , map);
	}
	
	// RowMapper pour récup lecture des données d'un user
	private static class UtilisateurRowMapper implements RowMapper<Utilisateur> {
		
        @Override
        public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
            Utilisateur user = new Utilisateur();
            user.setNbUser(rs.getInt("no_utilisateur"));
            user.setPseudo(rs.getString("pseudo"));
            user.setLastName(rs.getString("nom"));
            user.setFirstName(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("telephone"));
            user.setStreet(rs.getString("rue"));
            user.setPostalCode(rs.getString("code_postal"));
            user.setCity(rs.getString("ville"));
            user.setCredit(rs.getInt("credit"));
            user.setAdmin(rs.getByte("administrateur"));
            return user;
        }
    }


	
		
	


	
	

}
