package fr.eni.tp.bo;

import java.time.LocalDate;

public class Enchere {

	private LocalDate auctionDate;
	private int bidAmount;
	
	
	
	// Constructors
	
	
	public Enchere(LocalDate auctionDate, int bidAmount) {
		this.auctionDate = auctionDate;
		this.bidAmount = bidAmount;
	}
	
	public Enchere() {
	}
	
	
	// Getters / Setters
	
	/**
	 * @return the auctionDate
	 */
	public LocalDate getAuctionDate() {
		return auctionDate;
	}
	/**
	 * @param auctionDate the auctionDate to set
	 */
	public void setAuctionDate(LocalDate auctionDate) {
		this.auctionDate = auctionDate;
	}
	/**
	 * @return the bidAmount
	 */
	public int getBidAmount() {
		return bidAmount;
	}
	/**
	 * @param bidAmount the bidAmount to set
	 */
	public void setBidAmount(int bidAmount) {
		this.bidAmount = bidAmount;
	}
	
}
