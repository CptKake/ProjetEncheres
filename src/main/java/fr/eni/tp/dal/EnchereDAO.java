package fr.eni.tp.dal;

import java.time.LocalDate;
import java.util.List;

import fr.eni.tp.bo.Enchere;

public interface EnchereDAO {

	Enchere readAuctions(int nbArticle);
	
	List<Enchere> findAll();
	
	Enchere findUserBid(int nbUser, LocalDate auctionDate);
	
	Enchere findByUser(int nbUser, int nbArticle);
	
	Enchere findUsersAuction(int nbUser);
	
	
	
	
}
