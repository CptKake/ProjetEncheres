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
	private int userNumber;
	private int categoryNumber;
	
	
	public Article(String name, String description, LocalDate bidStart, LocalDate bidEnd, int initPrice) {
		this.name = name;
		this.description = description;
		this.bidStart = bidStart;
		this.bidEnd = bidEnd;
		this.initPrice = initPrice;
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
	 * @return the userNumber
	 */
	public int getUserNumber() {
		return userNumber;
	}


	/**
	 * @param userNumber the userNumber to set
	 */
	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}


	/**
	 * @return the categoryNumber
	 */
	public int getCategoryNumber() {
		return categoryNumber;
	}


	/**
	 * @param categoryNumber the categoryNumber to set
	 */
	public void setCategoryNumber(int categoryNumber) {
		this.categoryNumber = categoryNumber;
	}


	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}
	
	
	
}
