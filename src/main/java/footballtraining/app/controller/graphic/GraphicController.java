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
import footballtraining.app.model.graphic.GraphicRepository;
import footballtraining.app.model.user.User;
import footballtraining.app.model.user.UserRepository;

/**
 * The Class UploadDocumentController.
 */
@Controller
public class GraphicController {
	
	private static final String CREATEGRAPHIC_PAGE = "graphic/createGraphic";
	private static final String MODIFYGRAPHIC_PAGE = "graphic/modifyGraphic";

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GraphicRepository graphicRepository;
	
	@Autowired
	private ExerciseRepository exerciseRepository;

	/**
	 * Función que permite hacer una petición GET a la página uploadDocument.
	 *
	 * @param principal Este parámetro representa una instancia del objeto Principal
	 * @param model Este parámetro representa una instancia del objeto Model
	 * @return Una cadena de texto, referencia a la página uploadDocument
	 */
	@RequestMapping(value = "/graphic/createGraphic/{exerciseId}/{img}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String createGraphic(Principal principal, @PathVariable Long exerciseId, @PathVariable String img, Model model) {
		if (principal!=null){
			User user = userRepository.findOneByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		if (exerciseId !=null){
			Exercise exercise = exerciseRepository.getOne(exerciseId);
			if (exercise != null){
				model.addAttribute("exerciseId", exerciseId);
				if (img!=null){
					model.addAttribute("img", img);
				}
				return CREATEGRAPHIC_PAGE ;
			}
		}
			
		String errorMessage = "Exercise doesn´t exists";
		model.addAttribute("errorMessage", errorMessage);
		return "error/general";
		
	}
	
	@RequestMapping(value="/graphic/createGraphic/{exerciseId}",method=RequestMethod.POST,  consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String  saveGraphic(@RequestBody String graphicData, HttpServletRequest request, Principal principal, @PathVariable Long exerciseId, Model model) {
		
		if (principal!=null){
			User user = userRepository.findOneByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		
		try{
			if (exerciseId!=null){
				Exercise exercise = exerciseRepository.getOne(exerciseId);
				if (exercise != null){
					Graphic graphic = new Graphic(exercise, graphicData);
					graphic = graphicRepository.save(graphic);
					exercise.setGraphic(graphic);
					return "redirect:/exercise/"+exerciseId;
				}else{
					throw new Exception("Exercise doesn´t exists");
				}
				
			}else{
				throw new Exception("Exercise doesn´t exists");
			}
			
		}catch(Exception e){
			String errorMessage = "Graphic cannot be created";
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.warn(errorMessage, e);
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}
	}
	
	@RequestMapping(value = "/graphic/modifyGraphic/{graphicId}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String modifyGraphic(Principal principal, @PathVariable Long graphicId, Model model) {
		
		if (principal!=null){
			User user = userRepository.findOneByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		if (graphicId !=null){
			Graphic graphic = graphicRepository.getOne(graphicId);
			model.addAttribute("graphic", graphic);
			if (graphic != null){
				model.addAttribute("exerciseId", graphic.getExercise().getExerciseId());

				return MODIFYGRAPHIC_PAGE ;
			}
		}
			
		String errorMessage = "Exercise doesn´t exists";
		model.addAttribute("errorMessage", errorMessage);
		return "error/general";
		
	}
	
	@RequestMapping(value="/graphic/updateGraphic/{exerciseId}",method=RequestMethod.POST,  consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String  updateGraphic(@RequestBody String graphicData, HttpServletRequest request, Principal principal, @PathVariable Long exerciseId, Model model) {
		
		if (principal!=null){
			User user = userRepository.findOneByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		
		try{
			if (exerciseId!=null){
				Exercise exercise = exerciseRepository.getOne(exerciseId);
				if (exercise != null){
					Graphic graphic = exercise.getGraphic();
					graphic.setGraphicData(graphicData);
					graphic = graphicRepository.save(graphic);
					return "redirect:/exercise/"+exerciseId;
				}else{
					throw new Exception("Exercise doesn´t exists");
				}
				
			}else{
				throw new Exception("Exercise doesn´t exists");
			}
			
		}catch(Exception e){
			String errorMessage = "Graphic cannot be created";
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.warn(errorMessage, e);
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}
		
	}
	
}
