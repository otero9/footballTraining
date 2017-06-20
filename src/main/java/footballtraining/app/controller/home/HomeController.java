package footballtraining.app.controller.home;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import footballtraining.app.model.user.User;
import footballtraining.app.model.user.UserService;

/**
 * The Class HomeController.
 */
@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * Función que permite hacer una petición GET a la página raíz.
	 *
	 * @param model Este parámetro representa una instancia del objeto Model
	 * @param principal Este parámetro representa una instancia del objeto Principal
	 * @return Una cadena de texto, referencia a la página homeSignedIn o homeNotSignedIn
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, Principal principal) {
		if (principal!=null){
			User user = userService.findByUserName(principal.getName());
			
			if (user!=null){
				model.addAttribute("user", user);
			}
			
		}
			return "/home/home";
	}
	
}
