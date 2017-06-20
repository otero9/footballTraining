package footballtraining.app.controller.graphic;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * The Class SelectGraphicImageFormValidator.
 */
@Component
public class SelectGraphicImageFormValidator implements Validator {
	
	/**
     * Función que asigna una clase al validador
     * @return Un booleano comprobando si se puede asignar la clase al validador
     */
	@Override
	public boolean supports(Class<?> clazz) {
		return SelectGraphicImageForm.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		
	}

}