package footballtraining.app.controller.exercise;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * The Class ModifyExerciseFormValidator.
 */
@Component
public class ModifyExerciseFormValidator  implements Validator {
	
	/**
     * Función que asigna una clase al validador
     * @return Un booleano comprobando si se puede asignar la clase al validador
     */
	@Override
	public boolean supports(Class<?> clazz) {
		return ModifyExerciseForm.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		ModifyExerciseForm modifyExerciseForm = (ModifyExerciseForm) target;
		if ((modifyExerciseForm.getVideoURL()!=null) && (!modifyExerciseForm.getVideoURL().isEmpty())) {
			
			String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

		    Pattern compiledPattern = Pattern.compile(pattern);
		    Matcher matcher = compiledPattern.matcher(modifyExerciseForm.getVideoURL());
		    String id = null;
		    if(matcher.find()){
		        id =matcher.group();
		    }
		    
			if ((id != null) && (!id.isEmpty())) {
				
			}else{
				errors.rejectValue("videoURL", "error");
			}
		}
		
	}
}
