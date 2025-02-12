package fr.eni.tp.bo;

import java.time.LocalDate;

public class Article {

	private int number;
	private String name;
	private String description;
	private LocalDate bidStart;
	private LocalDate bidEnd;
	private int initPrice;
	private int sellPrice;
	private Utilisateur user;
	private Categorie category;
	
	public Article(String name, String description, LocalDate bidStart, LocalDate bidEnd, int initPrice) {
		this.name = name;
		this.description = description;
		this.bidStart = bidStart;
		this.bidEnd = bidEnd;
		this.initPrice = initPrice;
	}

	
	
	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the bidStart
	 */
	public LocalDate getBidStart() {
		return bidStart;
	}

	/**
	 * @param bidStart the bidStart to set
	 */
	public void setBidStart(LocalDate bidStart) {
		this.bidStart = bidStart;
	}

	/**
	 * @return the bidEnd
	 */
	public LocalDate getBidEnd() {
		return bidEnd;
	}

	/**
	 * @param bidEnd the bidEnd to set
	 */
	public void setBidEnd(LocalDate bidEnd) {
		this.bidEnd = bidEnd;
	}

	/**
	 * @return the initPrice
	 */
	public int getInitPrice() {
		return initPrice;
	}

	/**
	 * @param initPrice the initPrice to set
	 */
	public void setInitPrice(int initPrice) {
		this.initPrice = initPrice;
	}

	/**
	 * @return the sellPrice
	 */
	public int getSellPrice() {
		return sellPrice;
	}

	/**
	 * @param sellPrice the sellPrice to set
	 */
	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	/**
	 * @return the user
	 */
	public Utilisateur getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Utilisateur user) {
		this.user = user;
	}

	/**
	 * @return the category
	 */
	public Categorie getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Categorie category) {
		this.category = category;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}
	
	
	
	
	
	
}
