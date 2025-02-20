package fr.eni.tp.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Categorie;
import fr.eni.tp.bo.Utilisateur;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

	private static final String CREATE_ART = "INSERT INTO articles_vendus (nom_article, description, date_debut_encheres, date_fin_encheres, "
											+ "prix_initial, prix_vente, no_utilisateur, no_categorie) "
											+ "VALUES (:name, :description, :bidS, :bidE, "
											+ ":initPrice, :sellPrice, :userId, :catId) RETURNING no_article";
	private static final String UPDATE_ART = "UPDATE articles_vendus SET nom_article = :name, description = :description, "
											+ "date_debut_encheres = :bidS, date_fin_encheres = :bidE, prix_initial = :initPrice, "
											+ "prix_vente = :sellPrice, no_categorie = :catId WHERE no_article = :artId";
	private static final String FIND_BY_ID = "SELECT * FROM articles_vendus WHERE no_article = :id";
	private static final String DELETE_ART_BY_ID = "delete from articles_vendus where no_article = :id";
	private static final String FIND_ALL = "select * from articles_vendus";
	private static final String FIND_EN_OPEN = "SELECT * FROM articles_vendus WHERE date_fin_encheres > CURRENT_DATE";
	private static final String FIND_SELLS_PRESENT = "SELECT * FROM articles_vendus WHERE no_utilisateur = :idUser AND date_fin_encheres > CURRENT_DATE AND date_debut_encheres < CURRENT_DATE";
	private static final String FIND_SELLS_PAST = "SELECT * FROM articles_vendus WHERE no_utilisateur = :idUser AND date_fin_encheres < CURRENT_DATE ";
	private static final String FIND_SELLS_FUTURE = "SELECT * FROM articles_vendus WHERE no_utilisateur = :idUser AND date_debut_encheres < CURRENT_DATE";
	private static final String FIND_WINNED = "SELECT av.no_article, av.nom_article, av.description, av.date_debut_encheres, "
												+ "av.date_fin_encheres, av.prix_initial, av.prix_vente, av.no_utilisateur, av.no_categorie "
												+ "FROM articles_vendus av JOIN encheres e ON e.no_article = av.no_article "
												+ "WHERE e.montant_enchere = av.prix_vente AND date_fin_encheres < CURRENT_DATE "
												+ "AND e.no_utilisateur = :idUser";
	private static final String FIND_OPEN_BIDDED = "SELECT 	av.no_article, av.nom_article, av.description, av.date_debut_encheres, "
												+ "av.date_fin_encheres, av.prix_initial, av.prix_vente, av.no_utilisateur, av.no_categorie "
												+ "FROM articles_vendus av JOIN encheres e ON e.no_article = av.no_article "
												+ "WHERE e.no_utilisateur = :idUser AND date_fin_encheres > CURRENT_DATE";	
	
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	public ArticleDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public void createArticle(Article art) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("name", art.getName());
		map.addValue("description", art.getDescription());
		map.addValue("bidS", art.getBidStart());
		map.addValue("bidE", art.getBidEnd());
		map.addValue("initPrice", art.getInitPrice());
		map.addValue("sellPrice", art.getInitPrice());
		map.addValue("userId", art.getUser().getNbUser());
		map.addValue("catId", art.getCategory().getNumber());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(CREATE_ART, map, keyHolder);
		
		if(keyHolder != null && keyHolder.getKey() != null) {
			art.setNumber(keyHolder.getKey().intValue());
		}
	}

	@Override
	public void suppressArticle(Article art) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", art.getNumber());
		
		namedParameterJdbcTemplate.update(DELETE_ART_BY_ID, map);
	}

	@Override
	public Article readArticle(int nbrArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", nbrArticle);
		
		return namedParameterJdbcTemplate.queryForObject(FIND_BY_ID, map, new ArticleRowMapper());
	}

	@Override
	public void updateArticle(Article art) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("name", art.getName());
		map.addValue("description", art.getDescription());
		map.addValue("bidS", art.getBidStart());
		map.addValue("bidE", art.getBidEnd());
		map.addValue("initPrice", art.getInitPrice());
		map.addValue("sellPrice", art.getSellPrice());
		map.addValue("catId", art.getCategory().getNumber());
		map.addValue("artId", art.getNumber());
		 namedParameterJdbcTemplate.update(UPDATE_ART, map);
	}

	@Override
	public List<Article> findAll() {
		return namedParameterJdbcTemplate.query(FIND_ALL, new ArticleRowMapper());
	}

	@Override
	public List<Article> findEnCours() {
		return namedParameterJdbcTemplate.query(FIND_EN_OPEN, new ArticleRowMapper());
	}

	@Override
	public List<Article> findUserBidded(Utilisateur user) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUser", user.getNbUser());
		
		return namedParameterJdbcTemplate.query(FIND_OPEN_BIDDED, map, new ArticleRowMapper());
	}
	
	@Override
	public List<Article> findWinned(Utilisateur user) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUser", user.getNbUser());
		
		return namedParameterJdbcTemplate.query(FIND_WINNED, map, new ArticleRowMapper());
	}
	
	@Override
	public List<Article> findUserSells(Utilisateur user) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUser", user.getNbUser());
		
		return namedParameterJdbcTemplate.query(FIND_SELLS_PRESENT, map, new ArticleRowMapper());
	}
	
	@Override
	public List<Article> findUserFutureSells(Utilisateur user) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUser", user.getNbUser());
		
		return namedParameterJdbcTemplate.query(FIND_SELLS_FUTURE, map, new ArticleRowMapper());
	}
	
	@Override
	public List<Article> findUserPastSells(Utilisateur user) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUser", user.getNbUser());
		
		return namedParameterJdbcTemplate.query(FIND_SELLS_PAST, map, new ArticleRowMapper());
	}
	
}

class ArticleRowMapper implements RowMapper<Article> {

	@Override
    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Article art = new Article();
        art.setNumber(rs.getInt("no_article"));
        art.setName(rs.getString("nom_article"));
        art.setDescription(rs.getString("description"));
        art.setBidStart(rs.getDate("date_debut_encheres").toLocalDate());
        art.setBidEnd(rs.getDate("date_fin_encheres").toLocalDate());
        art.setInitPrice(rs.getInt("prix_initial"));
        art.setSellPrice(rs.getInt("prix_vente"));
        //art.setCategory(categorieDAO.findById(rs.getInt("no_categorie")));
        
        Categorie cat = new Categorie();
        cat.setNumber(rs.getInt("no_categorie"));
        art.setCategory(cat);
        
        Utilisateur user = new Utilisateur();
        user.setNbUser(rs.getInt("no_utilisateur"));
        art.setUser(user);
        
        return art;
    }
	
}


