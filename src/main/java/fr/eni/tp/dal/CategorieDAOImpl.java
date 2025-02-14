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

import fr.eni.tp.bo.Categorie;

@Repository
public class CategorieDAOImpl implements CategorieDAO {

	private static final String FIND_BY_LIBELLE = "SELECT * FROM categories WHERE libelle = :libelle ";
	private static final String FIND_BY_ID = "SELECT * FROM categories WHERE no_categorie = :id ";
	private static final String CREATE_CAT = "INSERT INTO categories (libelle) VALUES (:libelle) ";
	private static final String FIND_ALL = "SELECT * FROM categories";
	
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public CategorieDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	
	@Override
	public List<Categorie> findAll() {
		return namedParameterJdbcTemplate.query(FIND_ALL, new CatRowMapper());
	}
	
	@Override
	public Categorie readCategorie(String name) {
		MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("libelle", name);
        
		return namedParameterJdbcTemplate.queryForObject(FIND_BY_LIBELLE, map, new CatRowMapper());
	}

	@Override
	public Categorie findById(int id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", id);
        
		return namedParameterJdbcTemplate.queryForObject(FIND_BY_ID, map, new CatRowMapper());
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

class CatRowMapper implements RowMapper<Categorie> {
	
    @Override
    public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Categorie cat = new Categorie();
        cat.setNumber(rs.getInt("no_categorie"));
        cat.setName(rs.getString("libelle"));
       // TODO créer une fonction get cat by id
       //art.setCategory(rs.getInt("no_categorie"));
        
        return cat;
    }

}
