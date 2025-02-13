
 package fr.eni.tp.bll;
 

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Enchere;
import fr.eni.tp.bo.Utilisateur;
import fr.eni.tp.dal.ArticleDAO;
import fr.eni.tp.dal.EnchereDAO;

@Service
public class EnchereServiceImpl implements EnchereService  {

	private EnchereDAO enchereDAO;
	private ArticleDAO articleDAO;
	
	public EnchereServiceImpl(EnchereDAO enchereDAO, ArticleDAO articleDAO) {
		this.enchereDAO = enchereDAO;
		this.articleDAO = articleDAO;
	}

	@Override
	public List<Enchere> userEncheres(Utilisateur user) {
		List<Enchere> encheres = enchereDAO.finduserBid(user);
		return encheres;
	}

	@Override
	public List<Enchere> encheresEnCours(Utilisateur user) {
		List<Enchere> encheres = enchereDAO.findEnCours(user);
		return encheres;
	}

	@Override
	public void createEnchere(Enchere enchere) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEnchere(Enchere enchere) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEnchere(Enchere enchere) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Article> encheresWinByUser(Utilisateur user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enchere bestEnchere(Article art) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createArticle(Article article) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteArticle(Article article) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateArticle(Article article) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Article readArticle(int nbrArticle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> allArticles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> VentesEnCours() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> UserVentes(Utilisateur user) {
		// TODO Auto-generated method stub
		return null;
	}

}
