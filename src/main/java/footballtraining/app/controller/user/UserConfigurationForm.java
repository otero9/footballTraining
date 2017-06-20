package footballtraining.app.controller.user;

import org.hibernate.validator.constraints.Email;

public class UserConfigurationForm {
	
	private static final String EMAIL_MESSAGE = "No es un email valido";

	private String password;

	@Email(message = UserConfigurationForm.EMAIL_MESSAGE)
	private String email;
	
	private String name;
	
	private String surnames;
	
	private String country;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
