package fr.eni.tp.bo;
 
 
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD

import jakarta.validation.constraints.Email;
||||||| 18cb1d7

=======
 
import fr.eni.tp.configuration.PasswordMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
>>>>>>> alex
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
 
public class Utilisateur {
 
	private int nbUser;
<<<<<<< HEAD

	@NotBlank(message = "Le pseudo est obligatoire")
||||||| 18cb1d7
<<<<<<< HEAD
	@NotBlank
||||||| c131493
=======
	
	@NotBlank
>>>>>>> 7aace31e825866b6997054ba4800caa64f693196
=======
 
	@NotBlank(message = "Le pseudo est obligatoire")
>>>>>>> alex
	private String pseudo;
<<<<<<< HEAD

	@NotBlank(message = "Le nom est obligatoire")
||||||| 18cb1d7
<<<<<<< HEAD
	@NotBlank
||||||| c131493
=======
	
	@NotBlank
>>>>>>> 7aace31e825866b6997054ba4800caa64f693196
=======
 
	@NotBlank(message = "Le nom est obligatoire")
>>>>>>> alex
	private String lastName;
<<<<<<< HEAD

	@NotBlank(message = "Le prénom est obligatoire")
||||||| 18cb1d7
<<<<<<< HEAD
	@NotBlank
||||||| c131493
=======
	
	@NotBlank
>>>>>>> 7aace31e825866b6997054ba4800caa64f693196
=======
 
	@NotBlank(message = "Le prénom est obligatoire")
>>>>>>> alex
	private String firstName;
<<<<<<< HEAD

	@NotBlank(message = "L'email est obligatoire")
	@Email(message = "L'email doit être valide")
||||||| 18cb1d7
<<<<<<< HEAD
	@NotBlank
||||||| c131493
=======
	
	@NotBlank
>>>>>>> 7aace31e825866b6997054ba4800caa64f693196
=======
 
	@NotBlank(message = "L'email est obligatoire")
	@Email(message = "L'email doit être valide")
>>>>>>> alex
	private String email;
	private String phone;
<<<<<<< HEAD

||||||| 18cb1d7
<<<<<<< HEAD
=======
 
>>>>>>> alex
	@NotBlank
	private String street;
<<<<<<< HEAD

||||||| 18cb1d7
<<<<<<< HEAD
=======
 
>>>>>>> alex
	@NotBlank
	private String postalCode;
<<<<<<< HEAD

||||||| 18cb1d7
<<<<<<< HEAD
	@NotBlank
||||||| c131493
=======
=======
 
>>>>>>> alex
	
	@NotBlank
	private String city;
<<<<<<< HEAD

	@NotBlank(message = "Le mot de passe est obligatoire")
	
||||||| 18cb1d7
<<<<<<< HEAD
	@NotBlank
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
||||||| c131493
=======
	
	@NotBlank
	@Pattern(regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
>>>>>>> 7aace31e825866b6997054ba4800caa64f693196
=======
 
	@NotBlank(message = "Le mot de passe est obligatoire")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$" )
>>>>>>> alex
	private String password;
	private int credit;
	private Byte admin;
	
	
	
 
	private List<Enchere> auctions;
	private List<Article> articles;
	
	//pour confimation du mdp
	
	private String confirmPassword;
 
	public String getConfirmPassword() {
	    return confirmPassword;
	}
 
	public void setConfirmPassword(String confirmPassword) {
	    this.confirmPassword = confirmPassword;
	}
	
	@Constraint(validatedBy = PasswordMatchValidator.class)
	@Target({ ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface PasswordMatch {
	    String message() default "Les mots de passe ne correspondent pas";
	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
	}
	// Constructors
	
 
	
	public Utilisateur() {
		 auctions = new ArrayList<Enchere>() ;
		 articles = new ArrayList<Article>();
	}
	
	public Utilisateur(int nbUser, String pseudo, String lastName, String firstName, String email, String phone, String street,
			String postalCode, String city, int credit, Byte admin) {
		this();
		this.nbUser = nbUser;
		this.pseudo = pseudo;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.phone = phone;
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
		this.credit = credit;
		this.admin = admin;
	}
	
	
	
	
	//Getters / Setters
	
	
	
	
	/**
	 * @return the nbUser
	 */
	public int getNbUser() {
		return nbUser;
	}
	/**
	 * @param nbUser the nbUser to set
	 */
	public void setNbUser(int nbUser) {
		this.nbUser = nbUser;
	}
	/**
	 * @return the pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}
	/**
	 * @param pseudo the pseudo to set
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the credit
	 */
	public int getCredit() {
		return credit;
	}
	/**
	 * @param credit the credit to set
	 */
	public void setCredit(int credit) {
		this.credit = credit;
	}
	/**
	 * @return the admin
	 */
	public Byte getAdmin() {
		return admin;
	}
	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(Byte admin) {
		this.admin = admin;
	}
	
	
 
	/**
	 * @return the auctions
	 */
	public List<Enchere> getAuctions() {
		return auctions;
	}
 
	/**
	 * @param auctions the auctions to set
	 */
	public void setAuctions(List<Enchere> auctions) {
		this.auctions = auctions;
	}
 
	/**
	 * @return the articles
	 */
	public List<Article> getArticles() {
		return articles;
	}
 
	/**
	 * @param articles the articles to set
	 */
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
 
	@Override
	public String toString() {
		return "User [nbUser=" + nbUser + ", pseudo=" + pseudo + ", lastName=" + lastName + ", firstName=" + firstName
				+ ", email=" + email + ", phone=" + phone + ", street=" + street + ", postalCode=" + postalCode
				+ ", city=" + city + ", credit=" + credit + ", admin=" + admin + "]";
	}
 
 
	
	
	
	
}
