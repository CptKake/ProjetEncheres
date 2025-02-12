package fr.eni.tp.dal;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import fr.eni.tp.bo.Categorie;

public class CategorieDAOImpl implements CategorieDAO {

	private static final String FIND_BY_LIBELLE = "SELECT * FROM categories WHERE libelle = :libelle ";
	private static final String FIND_BY_ID = "SELECT * FROM categories WHERE no_categorie = :id ";
	private static final String CREATE_CAT = "INSERT INTO categories (libelle) VALUES (:libelle) ";
	
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public CategorieDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	
	
	@Override
	public Categorie readCategorie(String name) {
		MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("libelle", name);
        
		return namedParameterJdbcTemplate.queryForObject(FIND_BY_LIBELLE, map, new BeanPropertyRowMapper<Categorie>(Categorie.class));
	}

	@Override
	public Categorie findById(int id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", id);
        
		return namedParameterJdbcTemplate.queryForObject(FIND_BY_ID, map, new BeanPropertyRowMapper<Categorie>(Categorie.class));
	}

	@Override
	public void createCategorie(Categorie cat) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("libelle", cat.getName());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(CREATE_CAT, map, keyHolder);
		
		if(keyHolder != null && keyHolder.getKey() != null) {
			cat.setNumber(keyHolder.getKey().intValue());
		}
		
	}

}
