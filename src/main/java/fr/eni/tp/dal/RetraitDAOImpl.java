package fr.eni.tp.dal;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import fr.eni.tp.bo.Retrait;
import fr.eni.tp.bo.Utilisateur;

public class RetraitDAOImpl implements RetraitDAO {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	
	public RetraitDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}



	@Override
	public Retrait takeAdress(Utilisateur user) {
		// TODO Auto-generated method stub
		return null;
	}

}
