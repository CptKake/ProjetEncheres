
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
	public List<Enchere> getEncheres() {
		List<Enchere> encheres = enchereDAO.findAll();
		return encheres;
	}

	@Override
	public void bid(Utilisateur user, int sum, Article art) {
		if (sum > art.getSellPrice() && sum <= user.getCredit()) {
			articleDAO.updateArticle(art);
		} else {
			System.err.println("Nbr crédits insuffisants ou offre trop basse");
		}
		
	}

	@Override
	public Article buy(Utilisateur user, Article art) {
		
		return null;
	}

	@Override
	public List<Article> getSells(Utilisateur user) {
		List<Article> articles = enchereDAO.findUsersAuction(user.getNbUser());
		
		return articles;
	}

	@Override
	public void sell(Article article) {
		articleDAO.createArticle(article);
	}

	@Override
	public void cancelAuction(Article article) {
		articleDAO.suppressArticle(article);
	}

	@Override
	public Optional<Enchere> getEnchereById(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
