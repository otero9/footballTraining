package footballtraining.app.controller.video;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import footballtraining.app.model.video.VideoRepository;

/**
 * The Class UploadVideoFormValidator.
 */
@Component
public class UploadVideoFormValidator implements Validator {

	@Autowired
	private VideoRepository videoRepository;

	/**
	 * Función que asigna una clase al validador
	 * 
	 * @return Un booleano comprobando si se puede asignar la clase al validador
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return UploadVideoForm.class.isAssignableFrom(clazz);
	}

	/**
	 * Función que valida los campos del formulario signupForm
	 * 
	 * @param target
	 *            Este parámetro representa una instancia del objeto Object
	 * @param errors
	 *            Este parámetro representa una instancia del objeto Errors
	 */
	@Override
	public void validate(Object target, Errors errors) {
		UploadVideoForm videoForm = (UploadVideoForm) target;
		if (videoRepository.findByVideoTitle(videoForm.getVideoTitle()) != null) {
			errors.rejectValue("videoTitle", "videoTitle.message");
		}
		if ((videoForm.getVideoURL() != null) && (!videoForm.getVideoURL().isEmpty())) {

			String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

			Pattern compiledPattern = Pattern.compile(pattern);
			Matcher matcher = compiledPattern.matcher(videoForm.getVideoURL());
			String id = null;
			if (matcher.find()) {
				id = matcher.group();
			}

			if ((id != null) && (!id.isEmpty())) {

			} else {
				errors.rejectValue("videoURL", "error");
			}
		}
	}

}
