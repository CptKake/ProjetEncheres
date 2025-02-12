package fr.eni.tp.dal;

import java.util.List;

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
												+ "WHERE av.date_fin_encheres > CURRENT_DATE";
	
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public EnchereDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public Enchere readAuction(Enchere enchere) {
		return null;
	}

	@Override
	public List<Enchere> finduserBid(Utilisateur user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Enchere> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Enchere> findEnCours(Utilisateur user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> findWinByUser(Utilisateur user) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
