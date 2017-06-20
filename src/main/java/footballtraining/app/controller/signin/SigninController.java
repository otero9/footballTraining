package footballtraining.app.controller.signin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Class SigninController.
 */
@Controller
public class SigninController {

	/**
	 * Función que permite hacer una petición GET a la página signin para loggearse.
	 *
	 * @return Una cadena de texto, que representa la página signin.
	 */
	@RequestMapping(value = "signin")
	public String signin() {
		return "signin/signin";
	}
}