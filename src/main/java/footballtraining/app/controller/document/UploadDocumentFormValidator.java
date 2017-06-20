package footballtraining.app.controller.document;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * The Class UploadDocumentFormValidator.
 */
@Component
public class UploadDocumentFormValidator  implements Validator {
	
	/**
     * Función que asigna una clase al validador
     * @return Un booleano comprobando si se puede asignar la clase al validador
     */
	@Override
	public boolean supports(Class<?> clazz) {
		return UploadDocumentForm.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {

	}

}
