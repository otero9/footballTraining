package footballtraining.app.controller.graphic;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import footballtraining.app.model.exercise.Exercise;
import footballtraining.app.model.exercise.ExerciseRepository;
import footballtraining.app.model.graphic.Graphic;
import footballtraining.app.model.user.User;
import footballtraining.app.model.user.UserRepository;

/**
 * The Class SelectGraphicImageController.
 */
@Controller
public class SelectGraphicImageController {
	
	private static final String SELECTGRAPHICIMAGE_PAGE = "graphic/selectGraphicImage";
	
	private static final String CREATEGRAPHIC_PAGE = "graphic/createGraphic/";

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ExerciseRepository exerciseRepository;
	
	private Long exerciseId;

	/**
	 * Función que permite hacer una petición GET a la página uploadDocument.
	 *
	 * @param principal Este parámetro representa una instancia del objeto Principal
	 * @param model Este parámetro representa una instancia del objeto Model
	 * @return Una cadena de texto, referencia a la página uploadDocument
	 */
	@RequestMapping(value = "/graphic/selectGraphicImage/{exerciseId}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String selectImage(Principal principal, @PathVariable Long exerciseId, Model model) {
		
		if (principal!=null){
			User user = userRepository.findOneByUserName(principal.getName());
			model.addAttribute("user", user);
			if (exerciseId !=null){
				Exercise exercise = exerciseRepository.getOne(exerciseId);
				if (exercise != null){
					model.addAttribute(new SelectGraphicImageForm());
					model.addAttribute("exerciseId", exerciseId);
					return SELECTGRAPHICIMAGE_PAGE;
				}
			}
		}
		
		String errorMessage = "Exercise doesn´t exists";
		model.addAttribute("errorMessage", errorMessage);
		return "error/general";
	}
	
	
}
