package footballtraining.app.controller.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import footballtraining.app.model.user.UserRepository;

/**
 * The Class SignupFormValidator.
 */
@Component
public class SignupFormValidator implements Validator {

	@Autowired
	private UserRepository userRepository;
	/**
     * Función que asigna una clase al validador
     * @return Un booleano comprobando si se puede asignar la clase al validador
     */
	@Override
	public boolean supports(Class<?> clazz) {
		return SignupForm.class.isAssignableFrom(clazz);
	}
	
	/**
     * Función que valida los campos del formulario signupForm
     * @param target Este parámetro representa una instancia del objeto Object
     * @param errors Este parámetro representa una instancia del objeto Errors
     */
	@Override
	public void validate(Object target, Errors errors) {
		SignupForm signupForm = (SignupForm) target;
		if (userRepository.findOneByEmail(signupForm.getEmail()) != null){
			errors.rejectValue("email", "Already exists account with the same 'email'");
		}
		if (userRepository.findOneByUserName(signupForm.getUserName()) != null){
			errors.rejectValue("userName", "Already exists account with the same 'userName'");
		}
	}
	
}

