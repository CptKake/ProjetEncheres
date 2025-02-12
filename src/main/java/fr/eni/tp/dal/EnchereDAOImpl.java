package fr.eni.tp.dal;

import java.util.List;

import org.springframework.stereotype.Repository;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Enchere;

@Repository
public class EnchereDAOImpl implements EnchereDAO {

	
	
	
	@Override
	public Enchere readAuction(int nbArticle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Enchere> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enchere findUserBid(int nbUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enchere winByUser(int nbUser, int nbArticle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> findUsersAuction(int nbUser) {
		// TODO Auto-generated method stub
		return null;
	}

}
