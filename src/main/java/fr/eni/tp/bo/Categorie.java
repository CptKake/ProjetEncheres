package fr.eni.tp.bo;

public class Categorie {

	private int number;
	private String name;
	
	
	public Categorie(String name) {
		this.name = name;
	}


	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
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
	
	
	
}
