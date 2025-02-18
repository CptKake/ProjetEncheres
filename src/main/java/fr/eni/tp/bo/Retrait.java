package fr.eni.tp.bo;

public class Retrait {

	private int number;
	private String street;
	private String postalCode;
	private String city;
	
	public Retrait(String street, String postalCode, String city) {
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
	}

	public Retrait() {
	}

	

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}
	
	@Override
	public String toString() {
		return "Retrait [number=" + number + ", street=" + street + ", postalCode=" + postalCode + ", city=" + city
				+ "]";
	}
	
}
