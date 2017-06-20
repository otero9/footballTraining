package footballtraining.app.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import footballtraining.app.model.user.UserRepository;

/**
 * The Class UserConfigurationFormValidator.
 */
@Component
public class UserConfigurationFormValidator implements Validator {

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return UserConfigurationForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserConfigurationForm signupForm = (UserConfigurationForm) target;
	
		if (userRepository.findOneByEmail(signupForm.getEmail()) != null){
			errors.rejectValue("email", "Already exists account with the same 'email'");
		}

	}
}
