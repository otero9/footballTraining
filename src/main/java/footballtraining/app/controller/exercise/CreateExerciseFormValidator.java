package footballtraining.app.controller.exercise;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * The Class CreateExerciseFormValidator.
 */
@Component
public class CreateExerciseFormValidator implements Validator {
	
	/**
     * Función que asigna una clase al validador
     * @return Un booleano comprobando si se puede asignar la clase al validador
     */
	@Override
	public boolean supports(Class<?> clazz) {
		return CreateExerciseForm.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		CreateExerciseForm createExerciseForm = (CreateExerciseForm) target;
		if ((createExerciseForm.getVideoURL()!=null) && (!createExerciseForm.getVideoURL().isEmpty())) {
			
			String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

		    Pattern compiledPattern = Pattern.compile(pattern);
		    Matcher matcher = compiledPattern.matcher(createExerciseForm.getVideoURL());
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
