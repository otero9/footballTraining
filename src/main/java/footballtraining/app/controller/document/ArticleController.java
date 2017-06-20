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
import footballtraining.app.model.document.Article;
import footballtraining.app.model.document.ArticleValoration;
import footballtraining.app.model.document.DocumentService;
import footballtraining.app.model.exceptions.InstanceNotFoundException;
import footballtraining.app.model.user.User;
import footballtraining.app.model.user.UserService;
import footballtraining.app.util.PageWrapper;

/**
 * The Class ArticleController.
 */
@Controller
public class ArticleController {

	private static final String ARTICLEPAGE = "document/article";
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
	@RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
	public String blog(Principal principal, @PathVariable Long id, Model model) {
		User user = null;
		if (principal != null) {
			user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		Article article;
		try {
			article = documentService.findArticleByArticleId(id);
			if (article != null) {
				model.addAttribute("article", article);
			} else {
				throw new InstanceNotFoundException("Article " + id + " not exists");
			}
			if ((user != null) && (user.getUserName()!=article.getUser().getUserName())) { 
				ArticleValoration articleValoration = documentService.getValotarioArticleByUser(user, article);
				if (articleValoration!=null) {
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
			String errorMessage = "The article does not exists";
			logger.warn(errorMessage, e);
			model.addAttribute("errorMessage", errorMessage);
			return ERROR_PAGE;
		}

		return ARTICLEPAGE;
	}

	@RequestMapping(value = "article/deleteArticle/{articleId}", method = RequestMethod.POST)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String deleteArticle(@PathVariable String articleId, Principal principal, Model model) {
		User user = userService.findByUserName(principal.getName());
		model.addAttribute("user", user);
		try {
			Article article = documentService.findArticleByArticleId(Long.valueOf(articleId));
			documentService.deleteArticle(user.getUserId(), article.getArticleId());
		} catch (Exception e) {
			String errorMessage = "Selected comment to delete is not existing";
			model.addAttribute("errorMessage", errorMessage);
			return ERROR_PAGE;
		}

		return "user/"+user.getUserId()+"/details";
	}

	@RequestMapping(value = "/article/download/{articleId}", method = RequestMethod.GET)
	public @ResponseBody void downloadArticle(HttpServletResponse response,
			@PathVariable("articleId") String articleId) {

		Article article;
		try {
			article = documentService.findArticleByArticleId(Long.valueOf(articleId));
			File file = new File(article.getDocument().getDocumentName());

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			out.write(article.getDocument().getDocumentData());
			out.close();
			InputStream in = new ByteArrayInputStream(out.toByteArray());
			response.setContentType(article.getDocument().getDocumentMine());
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
			response.setHeader("Content-Length", String.valueOf(file.length()));
			FileCopyUtils.copy(in, response.getOutputStream());

		} catch (Exception e) {

		}
	}

	@RequestMapping(value = "/document/allArticles", method = RequestMethod.GET)
	public String allArticles(Principal principal, Model model, Pageable pageable,
			@RequestParam(required = false, name = "keywords") String keywords) {

		if (principal != null) {
			User user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		Page<Article> articlePage;
		PageWrapper<Article> page;
		pageable = new PageRequest(pageable.getPageNumber(), 10);
		try {

			articlePage = documentService.findAllArticles(keywords, pageable);

			page = new PageWrapper<>(articlePage, "/exercise/allExercises");

			model.addAttribute("articles", page.getContent());
			model.addAttribute("page", page);
			return "document/allArticles";
		} catch (Exception e) {
			String errorMessage = "Not authorized page";
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}

	}

	@RequestMapping(value = "/document/allArticles", method = RequestMethod.POST)
	public String allArticlesFilter(Principal principal, Model model, Pageable pageable,
			@RequestParam(required = true, name = "keywords") String keywords) {

		if (principal != null) {

			User user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		Page<Article> articlePage;
		PageWrapper<Article> page;
		pageable = new PageRequest(pageable.getPageNumber(), 10);
		try {

			articlePage = documentService.findAllArticles(keywords, pageable);

			page = new PageWrapper<>(articlePage, "/exercise/allExercises");

			model.addAttribute("articles", page.getContent());
			model.addAttribute("page", page);
			return "document/allArticles";
		} catch (Exception e) {
			String errorMessage = "Not authorized page";
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}

	}
	
	@RequestMapping(value = "/document/userArticles/{userName}", method = RequestMethod.GET)
	public String userArticles(Principal principal, Model model, Pageable pageable, @PathVariable String userName) {

		if (principal != null) {
			User user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		User userArticle = null;
		try {
			userArticle = userService.findByUserName(userName);
		} catch (Exception e) {
			String errorMessage = "User doesn't exists";
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}
		if (userArticle!=null) {
			model.addAttribute("userArticle", userArticle);
			Page<Article> articlePage;
			PageWrapper<Article> page;
			pageable = new PageRequest(pageable.getPageNumber(), 10);
			try {
				articlePage = documentService.findAllUserArticles(userArticle, pageable);
				page = new PageWrapper<>(articlePage, "/document/userArticles/"+userName);

				model.addAttribute("articles", page.getContent());
				model.addAttribute("page", page);
				return "document/userArticles";
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
	
	@RequestMapping(value = "/valorateArticle/{articleId}/{valoration}", method = RequestMethod.POST)
	public String valorateArticle(Principal principal, @PathVariable Long articleId, @PathVariable Integer valoration, Model model) {
		User user = null;
		if ((principal != null)&&(valoration!=null)) {
			user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
			try {
			Article article = documentService.findArticleByArticleId(articleId);
			
			if ((user!=null)&&(article!=null)) {
				
				ArticleValoration articleValoration = new ArticleValoration(user, articleId, valoration);
	
				documentService.valorateArticle(article, articleValoration);
				
			} else  {
				String errorMessage = "The article does not exists";
				logger.warn(errorMessage);
				model.addAttribute("errorMessage", errorMessage);
				return ERROR_PAGE;
			}
			
			} catch (Exception e) {
				String errorMessage = "Error occurred to vote this article";
				logger.warn(errorMessage, e);
				model.addAttribute("errorMessage", errorMessage);
				return ERROR_PAGE;
			}

			return "redirect:/article/"+articleId;
		} else {
			String errorMessage = "You don't have permission";
			logger.warn(errorMessage);
			model.addAttribute("errorMessage", errorMessage);
			return ERROR_PAGE;
		}
		
	}

}
