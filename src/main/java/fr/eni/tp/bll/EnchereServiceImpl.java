
 package fr.eni.tp.bll;
 

import java.time.LocalDate;
import java.util.List;

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
	public void createEnchere(Enchere enchere, Article art) {
		if (enchere.getBidAmount() > art.getSellPrice() && LocalDate.now().isBefore(art.getBidEnd())) {
			enchereDAO.create(enchere);
			art.setSellPrice(enchere.getBidAmount());
			articleDAO.updateArticle(art);
		} else {
			System.err.println("montant enchère trop bas, ou enchère terminée");
		}
		
	}

	@Override
	public void deleteEnchere(Enchere enchere) {
		enchereDAO.delete(enchere);
	}

	@Override
	public void updateEnchere(Enchere enchere) {
		
		
	}

	@Override
	public List<Article> encheresWinByUser(Utilisateur user) {
		List<Article> ventesGagnees = enchereDAO.findWinByUser(user);
		return ventesGagnees;
	}

	@Override
	public Enchere bestEnchere(Article art) {
		Enchere bestEnchere = enchereDAO.findBestBid(art);
		return bestEnchere;
	}

	@Override
	public void createArticle(Article article) {
		articleDAO.createArticle(article);
		
	}

	@Override
	public void deleteArticle(Article article) {
		if (LocalDate.now().isBefore(article.getBidStart())) {
			articleDAO.suppressArticle(article);
		}
	}

	@Override
	public void updateArticle(Article art) {
		if (LocalDate.now().isBefore(art.getBidStart())) {
			articleDAO.updateArticle(art);
		}
	}

	@Override
	public Article readArticle(int nbrArticle) {
		Article art = articleDAO.readArticle(nbrArticle);
		return art;
	}

	@Override
	public List<Article> allArticles() {
		List<Article> articles = articleDAO.findAll();
		return articles;
	}

	@Override
	public List<Article> VentesEnCours() {
		List<Article> articles = articleDAO.findEnCours();
		return articles;
	}

	@Override
	public List<Article> UserVentes(Utilisateur user) {
		List<Article> articles = articleDAO.findUserSells(user);
		return articles;
	}

}
