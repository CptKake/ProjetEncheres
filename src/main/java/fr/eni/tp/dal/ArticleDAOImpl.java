package fr.eni.tp.dal;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Utilisateur;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

	private static final String CREATE_ART = "INSERT INTO articles_vendus (nom_article, description, date_debut_encheres, date_fin_encheres, "
											+ "prix_initial, prix_vente, no_utilisateur, no_categorie) "
											+ "VALUES (:name, :description, :bidS, :bidE, "
											+ ":initPrice, :sellPrice, :userId, :catId)";
	private static final String UPDATE_ART = "UPDATE articles_vendus SET nom_article = :name, description = :description, "
											+ "date_debut_encheres = :bidS, date_fin_encheres = :bidE, prix_initial = :initPrice, "
											+ "prix_vente = :sellPrice, no_categorie = :catId WHERE no_article = :artId";
	private static final String FIND_BY_ID = "SELECT * FROM articles_vendus WHERE no_article = :id";
	private static final String DELETE_ART_BY_ID = "delete from articles_vendus where no_article = :id";
	private static final String FIND_ALL = "select * from articles_vendus";
	private static final String FIND_EN_COURS = "SELECT * FROM articles_vendus WHERE date_fin_encheres > CURRENT_DATE";
	private static final String FIND_SELLS = "SELECT * FROM articles_vendus WHERE no_utilisateur = :idUser";
	
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
		
		return namedParameterJdbcTemplate.queryForObject(FIND_BY_ID, map, new BeanPropertyRowMapper<Article>(Article.class));
	}

	@Override
	public void updateArticle(Article art) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("name", art.getName());
		map.addValue("description", art.getDescription());
		map.addValue("bidS", art.getBidStart());
		map.addValue("bidE", art.getBidEnd());
		map.addValue("initPrice", art.getInitPrice());
		map.addValue("sellPrice", art.getInitPrice());
		map.addValue("catId", art.getCategory().getNumber());
		map.addValue("artId", art.getNumber());
		 namedParameterJdbcTemplate.update(UPDATE_ART, map);
	}

	@Override
	public List<Article> findAll() {
		return namedParameterJdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Article.class));
	}

	@Override
	public List<Article> findEnCours() {
		return namedParameterJdbcTemplate.query(FIND_EN_COURS, new BeanPropertyRowMapper<>(Article.class));
	}

	@Override
	public List<Article> findUserSells(Utilisateur user) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUser", user.getNbUser());
		
		return namedParameterJdbcTemplate.query(FIND_SELLS, map, new BeanPropertyRowMapper<>(Article.class));
	}

}
