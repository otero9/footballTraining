package footballtraining.app.controller.document;

import java.security.Principal;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import footballtraining.app.model.document.Article;
import footballtraining.app.model.document.Document;
import footballtraining.app.model.document.DocumentService;
import footballtraining.app.model.document.Form;
import footballtraining.app.model.user.User;
import footballtraining.app.model.user.UserService;

/**
 * The Class UploadDocumentController.
 */
@Controller
public class UploadDocumentController {
	
	private static final String UPLOADDOCUMENT_PAGE = "document/uploadDocument";
	
	@Autowired
	private UploadDocumentFormValidator uploadDocumentValidator;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DocumentService documentService;
	
	
	/**
	 * Función que permite hacer una petición GET a la página uploadDocument.
	 *
	 * @param principal Este parámetro representa una instancia del objeto Principal
	 * @param model Este parámetro representa una instancia del objeto Model
	 * @return Una cadena de texto, referencia a la página uploadDocument
	 */
	@RequestMapping(value = "/document/uploadDocument", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String uploadDocument(Principal principal, Model model) {
		User user = userService.findByUserName(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute(new UploadDocumentForm());
		return UPLOADDOCUMENT_PAGE;
	}
	
	@RequestMapping(value = "/document/uploadDocument", method = RequestMethod.POST)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String uploadDocument(@Valid @ModelAttribute UploadDocumentForm uploadDocumentForm, Errors errors, RedirectAttributes ra,
			Principal principal, Model model, @RequestParam("file") MultipartFile file) {
		User user = userService.findByUserName(principal.getName());
		model.addAttribute("user", user);
		uploadDocumentValidator.validate(uploadDocumentForm, errors);
		if (errors.hasErrors()) {
			return UPLOADDOCUMENT_PAGE;
		}
	    try {
	    	Document document = null;
	    	try{
	            document = new Document();
	            document.setDocumentCreated(new Date());
	            document.setDocumentName(file.getOriginalFilename());
	            document.setDocumentData(file.getBytes());
	            document.setDocumentMine(file.getContentType());
	    	}catch (Exception e) {
				String errorMessage = "Document cannot be uploaded";
				Logger logger = LoggerFactory.getLogger(this.getClass());
				logger.warn(errorMessage, e);
				model.addAttribute("errorMessage", errorMessage);
				return "error/general";
	        }
            if (document != null){
            	if (uploadDocumentForm.getDocumentType().equals("Form")){
            		Form form = uploadDocumentForm.uploadForm();
            		form.setUser(user);
            		form = documentService.saveForm(form,document);
            		user.addForm(form);
            		userService.update(user);
            		return "redirect:/form/"+form.getFormId();
            	}else{
            		Article article = uploadDocumentForm.uploadArticle();
            		article.setUser(user);
            		article = documentService.saveArticle(article,document);
            		user.addArticle(article);
            		userService.update(user);
            		return "redirect:/article/"+article.getArticleId();
            	}
            }
        } catch (Exception e) {
			String errorMessage = "Document cannot be created";
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.warn(errorMessage, e);
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
        }
	   
    	String errorMessage = "Document cannot be created";
		model.addAttribute("errorMessage", errorMessage);
		return "error/general";
	}

}
