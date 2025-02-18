
package fr.eni.tp.dal;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Enchere;
import fr.eni.tp.bo.Utilisateur;

@Repository
public class EnchereDAOImpl implements EnchereDAO {

	private static final String ENCHERES_USER = "select * from encheres WHERE no_utilisateur = :idUser";
	private static final String ENCHERES_GAGNEES = "SELECT 	av.no_article, av.nom_article, av.description, av.date_debut_encheres, "
													+ "av.date_fin_encheres, av.prix_initial, av.prix_vente, av.no_utilisateur, av.no_categorie "
													+ "FROM encheres e "
													+ "JOIN articles_vendus av "
													+ "ON e.no_article = av.no_article WHERE av.date_fin_encheres < CURRENT_DATE "
													+ "AND av.prix_vente = e.montant_enchere "
													+ "AND e.no_utilisateur = :idUser";
	private static final String ENCHERES_EN_COURS = "SELECT e.no_utilisateur, e.no_article, e.date_enchere, e.montant_enchere "
												+ "FROM encheres e JOIN articles_vendus av ON e.no_article = av.no_article "
												+ "WHERE av.date_fin_encheres > CURRENT_DATE AND e.no_utilisateur = :idUser";
	private static final String FIND_BEST_BID = "SELECT e.no_utilisateur, e.no_article, e.date_enchere, e.montant_enchere "
												+ "FROM encheres e JOIN articles_vendus av ON e.no_article = av.no_article "
												+ "WHERE av.no_article = :idArt "
												+ "ORDER BY e.montant_enchere DESC LIMIT 1";
	private static final String CREATE_ENCHERE = "INSERT INTO encheres (no_utilisateur, no_article, date_enchere, montant_enchere) "
												+ "VALUES 	(:idUser, :idArt, CURRENT_DATE, :sum)";
	private static final String DELETE_ENCHERE = "DELETE FROM encheres WHERE no_utilisateur = :idUser AND no_article = :idArt";
	private static final String UPDATE_ENCHERE = "UPDATE encheres SET date_enchere = CURRENT_DATE, montant_enchere = :sum "
												+ "WHERE no_utilisateur = :idUser AND no_article = :idArt";
	private static final String ENCHERES_BY_ART = "select * from encheres WHERE no_article = :idArt";
	
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public EnchereDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public List<Enchere> finduserBid(Utilisateur user) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUser", user.getNbUser());
		
		return namedParameterJdbcTemplate.query(ENCHERES_USER, map, new BeanPropertyRowMapper<Enchere>(Enchere.class));
	}

	@Override
	public List<Enchere> findEnCours(Utilisateur user) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUser", user.getNbUser());
		
		return namedParameterJdbcTemplate.query(ENCHERES_EN_COURS, map, new BeanPropertyRowMapper<Enchere>(Enchere.class));
	}

	@Override
	public List<Article> findWinByUser(Utilisateur user) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUser", user.getNbUser());
		
		return namedParameterJdbcTemplate.query(ENCHERES_GAGNEES, map, new BeanPropertyRowMapper<Article>(Article.class));
	}

	@Override
	public Enchere findBestBid(Article art) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idArt", art.getNumber());
		
		return namedParameterJdbcTemplate.queryForObject(FIND_BEST_BID, map, new BeanPropertyRowMapper<Enchere>(Enchere.class));
	}

	@Override
	public void create(Enchere enchere) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUser", enchere.getNbUser().getNbUser());
		map.addValue("idArt", enchere.getNbArticle().getNumber());
		map.addValue("sum", enchere.getBidAmount());
		
		namedParameterJdbcTemplate.update(CREATE_ENCHERE, map);
	}

	@Override
	public void delete(Enchere enchere) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUser", enchere.getNbUser());
		map.addValue("idArt", enchere.getNbArticle());
		
		namedParameterJdbcTemplate.update(DELETE_ENCHERE, map);
	}

	@Override
	public void update(Enchere enchere) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUser", enchere.getNbUser());
		map.addValue("idArt", enchere.getNbArticle());
		map.addValue("sum", enchere.getBidAmount());
		
		namedParameterJdbcTemplate.update(UPDATE_ENCHERE, map);
	}

	@Override
	public List<Enchere> findArtEncheres(Article art) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idArt", art.getNumber());
		
		return namedParameterJdbcTemplate.query(ENCHERES_BY_ART, map, new BeanPropertyRowMapper<Enchere>(Enchere.class));
	}

	
	

}
