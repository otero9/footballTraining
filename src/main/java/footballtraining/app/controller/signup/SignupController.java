package footballtraining.app.controller.signup;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import footballtraining.app.model.exceptions.SaveNotAvailableException;
import footballtraining.app.model.user.User;
import footballtraining.app.model.user.UserService;


/**
 * The Class SignupController.
 */
@Controller
public class SignupController {

	private static final String SIGNUP_VIEW_NAME = "signup/signup";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SignupFormValidator signupValidator;
	
	/**
	 * Función que permite hacer una petición GET a la página signup,para registrase.
	 *
	 * @param model  Este parámetro representa una instancia del objeto Model
	 * @return Una cadena de texto, que representa la página signup.
	 */
	@RequestMapping(value = "signup")
	public String signup(Model model) {
		model.addAttribute(new SignupForm());
		return SIGNUP_VIEW_NAME;
	}
	/**
	 * Función que permite hacer una petición POST a la página signup, para crear un nuevo usuario (account).
	 *
	 * @param signupForm Este parámetro representa una instancia del objeto SignupForm
	 * @param errors Este parámetro representa una instancia del objeto Errors
	 * @param ra Este parámetro representa una instancia del objeto RedirectAttributes
	 * @param model  Este parámetro representa una instancia del objeto Model
	 * @return Una cadena de texto, con una redirección a la página raíz
	 */
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra, Model model) {
		signupValidator.validate(signupForm, errors);
		if (errors.hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}
		User user;
		try {
			user = userService.save(signupForm.createAccount());
			userService.signin(user);
		} catch (SaveNotAvailableException e) {
			String errorMessage = "Account cannot be created";
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.warn(errorMessage, e);
			model.addAttribute(errorMessage);
			return "error/general";
		}
		// see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
		//MessageHelper.addSuccessAttribute(ra, "signup.success");
		return "redirect:/";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
}
