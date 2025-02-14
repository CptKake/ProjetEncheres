package fr.eni.tp.bo;

public class Categorie {

	private int number;
	private String name;
	
	
	
	
	public Categorie() {
	}




	public Categorie(String name) {
		this.name = name;
	}


	
	
	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
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
