package fr.eni.tp.dal;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Retrait;
import fr.eni.tp.bo.Utilisateur;

@Repository
public class RetraitDAOImpl implements RetraitDAO {

	
    private static final String SELECT_RETRAIT = "SELECT rue, code_postal, ville FROM retraits WHERE no_article = :no_article";
    private static final String INSERT_RETRAIT = "INSERT INTO retraits (no_article, rue, code_postal, ville) VALUES (:no_article, :rue, :code_postal, :ville)";
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	
	public RetraitDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}



	// pour récupérer l'adresse en se basant sur le no Article
	@Override
	public Retrait takeAdress(int articleId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("no_article", articleId);
		return namedParameterJdbcTemplate.queryForObject(SELECT_RETRAIT, namedParameters, (rs, rowNum) -> {
            return new Retrait(rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"));
        });
	}

	
	// Pour enregistrer l'adresse de retrait dans la BDD
   @Override
    public void saveRetrait(Retrait retrait, int articleId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("no_article", articleId);
        namedParameters.addValue("rue", retrait.getStreet());
        namedParameters.addValue("code_postal", retrait.getPostalCode());
        namedParameters.addValue("ville", retrait.getCity());

        namedParameterJdbcTemplate.update(INSERT_RETRAIT, namedParameters);
    }
}
