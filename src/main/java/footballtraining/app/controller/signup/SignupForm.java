package footballtraining.app.controller.signup;

import java.util.Date;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import footballtraining.app.model.user.User;


/**
 * The Class SignupForm.
 */
public class SignupForm {

	private static final String NOT_BLANK_MESSAGE = "Rellene el campo";
	
	private static final String EMAIL_MESSAGE = "No es un email valido";
	
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Email(message = SignupForm.EMAIL_MESSAGE)
	private String email;
	
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String password;
	
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String userName;
	
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String name;
	
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String surnames;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthdate;
	
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String country;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurnames() {
		return surnames;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Función que devuelve un account con los campos correctos a partir de los datos introducidos en un formulario.
	 *
	 * @return El account con los datos introducidos en el formulario
	 */
	public User createAccount() {
		return new User(getUserName(), getEmail(), getName(), getSurnames(), getPassword(), getBirthdate(), getCountry(), "esp,ing", false);
	}
	
}
