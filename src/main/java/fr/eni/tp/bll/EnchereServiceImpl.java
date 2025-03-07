
 package fr.eni.tp.bll;
 

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.tp.bo.Article;
import fr.eni.tp.bo.Categorie;
import fr.eni.tp.bo.Enchere;
import fr.eni.tp.bo.Utilisateur;
import fr.eni.tp.dal.ArticleDAO;
import fr.eni.tp.dal.CategorieDAO;
import fr.eni.tp.dal.EnchereDAO;

@Service
public class EnchereServiceImpl implements EnchereService  {

	private EnchereDAO enchereDAO;
	private ArticleDAO articleDAO;
	private CategorieDAO categorieDAO;

	public EnchereServiceImpl(EnchereDAO enchereDAO, ArticleDAO articleDAO, CategorieDAO categorieDAO) {
		this.enchereDAO = enchereDAO;
		this.articleDAO = articleDAO;
		this.categorieDAO = categorieDAO;
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
	public List<Categorie> getAllCategories(){
		List<Categorie> categories = categorieDAO.findAll();
		
		return categories;
	}
	
	@Override
	public Categorie getCatById(int id) {
		Categorie cat = categorieDAO.findById(id);
		
		return cat;
	}
	
	
	@Override
	public void deleteEnchere(Enchere enchere) {
		enchereDAO.delete(enchere);
	}

	@Override
	public void updateEnchere(Enchere enchere) {
		enchereDAO.update(enchere);
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
	public void deleteArticle(int idArt) {
		 Article art = articleDAO.readArticle(idArt);
		if (LocalDate.now().isBefore(art.getBidStart())) {
			
			    if (art != null) {
			        articleDAO.suppressArticle(art);
			    }
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
	public List<Article> enchOpen() {
		List<Article> articles = articleDAO.findEnCours();
		return articles;
		
	}@Override
	public List<Article> enchBidded(Utilisateur user) {
		List<Article> articles = articleDAO.findUserBidded(user);
		return articles;
		
	}@Override
	public List<Article> enchWinned(Utilisateur user) {
		List<Article> articles = articleDAO.findWinned(user);
		return articles;
	}
	
	@Override
	public List<Article> ventesEnCours(Utilisateur user) {
		List<Article> articles = articleDAO.findUserSells(user);
		return articles;
	}

	@Override
	public List<Article> ventesFutures(Utilisateur user) {
		List<Article> articles = articleDAO.findUserFutureSells(user);
		return articles;
	}
	
	@Override
	public List<Article> ventesTerminees(Utilisateur user) {
		List<Article> articles = articleDAO.findUserPastSells(user);
		return articles;
	}
	
	@Override
	public Categorie getCatByName(String libelle) {
		Categorie cat = categorieDAO.readCategorie(libelle);
		
		return cat;
	}

	@Override
	public List<Enchere> findEncheresByArt(Article art) {
		List<Enchere> encheres = enchereDAO.findArtEncheres(art);
		return encheres;
	}

}
