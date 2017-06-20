package footballtraining.app.controller.document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.security.Principal;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import footballtraining.app.model.document.DocumentService;
import footballtraining.app.model.document.Form;
import footballtraining.app.model.document.FormValoration;
import footballtraining.app.model.exceptions.InstanceNotFoundException;
import footballtraining.app.model.user.User;
import footballtraining.app.model.user.UserService;
import footballtraining.app.util.PageWrapper;

/**
 * The Class FormController.
 */
@Controller
public class FormController {

	private static final String ARTICLEPAGE = "document/form";
	private static final String ERROR_PAGE = "error/general";

	@Autowired
	private UserService userService;

	@Autowired
	private DocumentService documentService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Función que permite hacer una petición GET a la página video con un id
	 * concreto.
	 *
	 * @param principal
	 *            Este parámetro representa una instancia del objeto Principal
	 * @param id
	 *            Este parámetro representa el id del video cuya página queremos
	 *            obtener
	 * @param model
	 *            Este parámetro representa una instancia del objeto Model
	 * @return Una cadena de texto, referencia a la página video
	 */
	@RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
	public String blog(Principal principal, @PathVariable Long id, Model model) {
		User user = null;
		if (principal != null) {
			user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		Form form;
		try {
			form = documentService.findFormByFormId(id);
			if (form != null) {
				model.addAttribute("form", form);
			} else {
				throw new InstanceNotFoundException("Form " + id + " not exists");
			}
			if ((user != null) && (user.getUserName()!=form.getUser().getUserName())) { 
				FormValoration formValoration = documentService.getValotarioFormByUser(user, form);
				if (formValoration!=null) {
					model.addAttribute("notVoted", false);
					model.addAttribute("voted", true);
				} else {
					model.addAttribute("notVoted", true);
					model.addAttribute("voted", false);
				}
			}else {
				model.addAttribute("notVoted", false);
				model.addAttribute("voted", false);
			}
		} catch (Exception e) {
			String errorMessage = "The form does not exists";
			logger.warn(errorMessage, e);
			model.addAttribute("errorMessage", errorMessage);
			return ERROR_PAGE;
		}

		return ARTICLEPAGE;
	}

	@RequestMapping(value = "form/deleteForm/{formId}", method = RequestMethod.POST)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String deleteForm(@PathVariable String formId, Principal principal, Model model) {
		User user = userService.findByUserName(principal.getName());
		model.addAttribute("user", user);
		try {
			Form form = documentService.findFormByFormId(Long.valueOf(formId));
			documentService.deleteForm(user.getUserId(), form.getFormId());
		} catch (Exception e) {
			String errorMessage = "Selected comment to delete is not existing";
			model.addAttribute("errorMessage", errorMessage);
			return ERROR_PAGE;
		}

		return "user/"+user.getUserId()+"/details";
	}

	@RequestMapping(value = "/form/download/{formId}", method = RequestMethod.GET)
	public @ResponseBody void downloadForm(HttpServletResponse response,
			@PathVariable("formId") String formId) {

		Form form;
		try {
			form = documentService.findFormByFormId(Long.valueOf(formId));
			File file = new File(form.getDocument().getDocumentName());

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			out.write(form.getDocument().getDocumentData());
			out.close();
			InputStream in = new ByteArrayInputStream(out.toByteArray());
			response.setContentType(form.getDocument().getDocumentMine());
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
			response.setHeader("Content-Length", String.valueOf(file.length()));
			FileCopyUtils.copy(in, response.getOutputStream());

		} catch (Exception e) {

		}
	}

	@RequestMapping(value = "/document/allForms", method = RequestMethod.GET)
	public String allForms(Principal principal, Model model, Pageable pageable,
			@RequestParam(required = false, name = "keywords") String keywords) {

		if (principal != null) {
			User user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		Page<Form> formPage;
		PageWrapper<Form> page;
		pageable = new PageRequest(pageable.getPageNumber(), 10);
		try {

			formPage = documentService.findAllForms(keywords, pageable);

			page = new PageWrapper<>(formPage, "/exercise/allExercises");

			model.addAttribute("forms", page.getContent());
			model.addAttribute("page", page);
			return "document/allForms";
		} catch (Exception e) {
			String errorMessage = "Not authorized page";
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}

	}

	@RequestMapping(value = "/document/allForms", method = RequestMethod.POST)
	public String allFormsFilter(Principal principal, Model model, Pageable pageable,
			@RequestParam(required = true, name = "keywords") String keywords) {

		if (principal != null) {

			User user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		Page<Form> formPage;
		PageWrapper<Form> page;
		pageable = new PageRequest(pageable.getPageNumber(), 10);
		try {

			formPage = documentService.findAllForms(keywords, pageable);

			page = new PageWrapper<>(formPage, "/exercise/allExercises");

			model.addAttribute("forms", page.getContent());
			model.addAttribute("page", page);
			return "document/allForms";
		} catch (Exception e) {
			String errorMessage = "Not authorized page";
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}

	}
	
	@RequestMapping(value = "/document/userForms/{userName}", method = RequestMethod.GET)
	public String userForms(Principal principal, Model model, Pageable pageable, @PathVariable String userName) {

		if (principal != null) {
			User user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		User userForm = null;
		try {
			userForm = userService.findByUserName(userName);
		} catch (Exception e) {
			String errorMessage = "User doesn't exists";
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}
		if (userForm!=null) {
			model.addAttribute("userForm", userForm);
			Page<Form> formPage;
			PageWrapper<Form> page;
			pageable = new PageRequest(pageable.getPageNumber(), 10);
			try {
				formPage = documentService.findAllUserForms(userForm, pageable);
				page = new PageWrapper<>(formPage, "/document/userForms/"+userName);

				model.addAttribute("forms", page.getContent());
				model.addAttribute("page", page);
				return "document/userForms";
			} catch (Exception e) {
				String errorMessage = "Not authorized page";
				model.addAttribute("errorMessage", errorMessage);
				return "error/general";
			}
		} else {
			String errorMessage = "User doesn't exists";
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}
	}
	
	@RequestMapping(value = "/valorateForm/{formId}/{valoration}", method = RequestMethod.POST)
	public String valorateForm(Principal principal, @PathVariable Long formId, @PathVariable Integer valoration, Model model) {
		User user = null;
		if ((principal != null)&&(valoration!=null)) {
			user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
			try {
			Form form = documentService.findFormByFormId(formId);
			
			if ((user!=null)&&(form!=null)) {
				
				FormValoration formValoration = new FormValoration(user, formId, valoration);
	
				documentService.valorateForm(form, formValoration);
				
			} else  {
				String errorMessage = "The form does not exists";
				logger.warn(errorMessage);
				model.addAttribute("errorMessage", errorMessage);
				return ERROR_PAGE;
			}
			
			} catch (Exception e) {
				String errorMessage = "Error occurred to vote this form";
				logger.warn(errorMessage, e);
				model.addAttribute("errorMessage", errorMessage);
				return ERROR_PAGE;
			}

			return "redirect:/form/"+formId;
		} else {
			String errorMessage = "You don't have permission";
			logger.warn(errorMessage);
			model.addAttribute("errorMessage", errorMessage);
			return ERROR_PAGE;
		}
		
	}

}
