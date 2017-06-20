package footballtraining.app.controller.exercise;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import footballtraining.app.model.exercise.Category;
import footballtraining.app.model.exercise.Exercise;
import footballtraining.app.model.exercise.ExerciseServiceImpl;
import footballtraining.app.model.exercise.ExerciseType;
import footballtraining.app.model.user.User;
import footballtraining.app.model.user.UserRepository;

/**
 * The Class CreateExerciseController.
 */
@Controller
public class CreateExerciseController {

	private static final String CREATEEXERCISE_PAGE = "exercise/createExercise";
	
	private static final String SELECTGRAPHICIMAGE_PAGE = "graphic/selectGraphicImage/";
	
	@Autowired
	private CreateExerciseFormValidator createExerciseValidator;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ExerciseServiceImpl exerciseService;
	
	/**
	 * Función que permite hacer una petición GET a la página createExercise.
	 *
	 * @param principal Este parámetro representa una instancia del objeto Principal
	 * @param model Este parámetro representa una instancia del objeto Model
	 * @return Una cadena de texto, referencia a la página createExercise
	 */
	@RequestMapping(value = "/exercise/createExercise", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String createExercise(Principal principal, Model model) {
		User user = userRepository.findOneByUserName(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute(new CreateExerciseForm());
		return CREATEEXERCISE_PAGE;
	}
	
	@RequestMapping(value = "/exercise/createExercise", method = RequestMethod.POST)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String createExercise(@Valid @ModelAttribute CreateExerciseForm createExerciseForm, Errors errors, RedirectAttributes ra,
			Principal principal, Model model) {
		User user = userRepository.findOneByUserName(principal.getName());
		model.addAttribute("user", user);
		createExerciseValidator.validate(createExerciseForm, errors);
		if (errors.hasErrors()) {
			return CREATEEXERCISE_PAGE;
		}
		ExerciseDTO exerciseDTO = createExerciseForm.createExercise();
		List<Category> categories = new ArrayList<Category>();
		List<ExerciseType> exerciseTypes = new ArrayList<ExerciseType>();
		Exercise exercise = null;
		try{
			if ((exerciseDTO.getVideoURL()!=null) && (!exerciseDTO.getVideoURL().isEmpty())) {
				String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

			    Pattern compiledPattern = Pattern.compile(pattern);
			    Matcher matcher = compiledPattern.matcher(createExerciseForm.getVideoURL());
			    String id = null;
			    if(matcher.find()){
			        id = matcher.group();
			    }
			    
				if ((id != null) && (!id.isEmpty())) {
					exerciseDTO.setVideoURL("https://www.youtube.com/embed/"+id);
				}
			}
			for (String categoryName : exerciseDTO.getCategories()){
				categories.add(exerciseService.findByCategoryName(categoryName));
			}
			for (String exerciseTypeName : exerciseDTO.getExerciseTypes()){
				exerciseTypes.add(exerciseService.findByExerciseTypeName(exerciseTypeName));
			}
			exercise = new Exercise(user, exerciseDTO.getExerciseTitle(), exerciseDTO.getExerciseDescription(), exerciseDTO.getExerciseObjective(), exerciseDTO.getLoads(), exerciseDTO.getBreaks(), exerciseDTO.getVideoURL(), Float.valueOf(0), 0);
			if (!categories.isEmpty()){
				exercise.getCategories().addAll(categories);
			}
			if (!exerciseTypes.isEmpty()){
				exercise.getExerciseTypes().addAll(exerciseTypes);
			}
			exercise = exerciseService.save(exercise);
			if ((createExerciseForm != null) && (exercise !=null) && (createExerciseForm.isAddGraphic())) {
				return "redirect:/" + SELECTGRAPHICIMAGE_PAGE + exercise.getExerciseId();
			}
			return "redirect:/exercise/"+exercise.getExerciseId();
		} catch (Exception e) {
			String errorMessage = "Exercise cannot be created";
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.warn(errorMessage, e);
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}

	}
	
}
