package footballtraining.app.controller.user;

import java.io.IOException;
import java.security.Principal;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import footballtraining.app.model.exceptions.SaveNotAvailableException;
import footballtraining.app.model.user.User;
import footballtraining.app.model.user.UserService;

/**
 * The Class UserController.
 */
@Controller
public class UserController {

	private static final String ERROR_PAGE = "error/general";
	private static final String ERROR_MESSAGE = "errorMessage";
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private UserConfigurationFormValidator userConfigurationValidator;
	
	/**
	 * Function usada para loggear una excepción y mostrar en una página el error ocurrido.
	 *
	 * @param errorMessage Este parámetro represernat el mensaje para loggear y mostrar
	 * @param model  Este parámetro representa una instancia del objeto Model
	 * @param e Este parámetro representa la excepción que queremos loggear
	 * @return Una cadena que representa la página de error que queremos mostrar
	 */
	public String logAndDisplayException(String errorMessage, Model model, Throwable e){
		logger.warn(errorMessage, e);
		model.addAttribute(ERROR_MESSAGE, errorMessage);
		return ERROR_PAGE;
	}

	/**
	 * Función que permite hacer una petición GET a la página configuration.
	 *
	 * @param principal Este parámetro representa una instancia del objeto Principal
	 * @param model Este parámetro representa una instancia del objeto Model
	 * @return Una cadena de texto, referencia a la página configuration
	 * @throws IOException Exepcion IO
	 */
	@RequestMapping(value = "user/configuration", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String configurationUser(Principal principal, Model model) throws IOException {
		User user = userService.findByUserName(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute(new UserConfigurationForm());
		return "user/configuration";
	}	
	
	@RequestMapping(value = "user/configuration", method = RequestMethod.POST)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String configurationUser(@Valid @ModelAttribute UserConfigurationForm userConfigurationForm,
			Errors errors, RedirectAttributes ra, Principal principal, Model model)
					throws IOException {
		User user = userService.findByUserName(principal.getName());
		model.addAttribute("user", user);
		if (!userConfigurationForm.getEmail().isEmpty())
			userConfigurationValidator.validate(userConfigurationForm, errors);
		if (errors.hasErrors()) {
			return "user/configuration";
		}
		if (!userConfigurationForm.getName().isEmpty())
			user.setName(userConfigurationForm.getName());
		if (!userConfigurationForm.getSurnames().isEmpty())
			user.setSurnames(userConfigurationForm.getSurnames());
		if (!userConfigurationForm.getCountry().isEmpty())
			user.setCountry(userConfigurationForm.getCountry());
		if (!userConfigurationForm.getPassword().isEmpty())
			user.setPassword(userConfigurationForm.getPassword());
		if (!userConfigurationForm.getPassword().isEmpty())
			user.setPassword(userConfigurationForm.getPassword());
		if (!userConfigurationForm.getEmail().isEmpty())
			user.setEmail(userConfigurationForm.getEmail());	

		try {
			user = userService.update(user);

		} catch (SaveNotAvailableException e) {
			String errorMessage="It is not possible to update the user";
			return logAndDisplayException(errorMessage, model, e);
		}
		return "redirect:/user/configuration";
	}
	
	@RequestMapping(value = "user/{userId}/details", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public String acccountDetails(@PathVariable("userId") Long userId, Model model, Principal principal) {
		if (principal != null) {
			User user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		User owner = userService.findByUserId(userId);
		model.addAttribute("owner", owner);
		return "user/details";
	}
	
}
