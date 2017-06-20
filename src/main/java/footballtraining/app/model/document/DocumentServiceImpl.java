package footballtraining.app.model.document;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import footballtraining.app.model.exceptions.DeleteNotAvailableException;
import footballtraining.app.model.exceptions.InstanceNotFoundException;
import footballtraining.app.model.exceptions.InternalErrorException;
import footballtraining.app.model.exceptions.UserNotExistsException;
import footballtraining.app.model.user.User;
import footballtraining.app.model.user.UserService;

/**
 * The Class DocumentServiceImpl.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DocumentServiceImpl implements DocumentService{
	
	private static final String NOTEXISTFORMS = "El formulario no existe";
	private static final String NOTEXISTARTICLES = "El articulo no existe";
	private static final String NOTEXISTUSER = "El usuario no existe";
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private ArticleValorationRepository articleValorationRepository;
	
	@Autowired
	private FormValorationRepository formValorationRepository;
	
	@Autowired
	private FormRepository formRepository;
	
	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private UserService userService;

	
	@Transactional
	public Article saveArticle(Article article,Document document) throws UserNotExistsException{
		if (userService.find(article.getUser()) == null){
			throw new UserNotExistsException("El nombre de usuario no existe");	
		}
		Document document2 = documentRepository.save(document);
		article.setDocument(document2);
		return articleRepository.save(article);
	}

	@Transactional
	public Form saveForm(Form form, Document document) throws UserNotExistsException{
		if (userService.find(form.getUser()) == null){
			throw new UserNotExistsException("El nombre de usuario no existe");	
		}
		Document document2 = documentRepository.save(document);
		form.setDocument(document2);
		return formRepository.save(form);
	}

	@Transactional
	public void deleteArticle(Long userId, Long articleId) throws InternalErrorException {
		Article article = articleRepository.findOne(articleId);
		if (isOwnerArticle(userId, articleId)){
			List<ArticleValoration> valorations = articleValorationRepository.findAllByArticleId(articleId);
			if ((valorations!=null) && (!valorations.isEmpty())) {
				for (ArticleValoration valoration : valorations) {
					articleValorationRepository.delete(valoration);
				}
			}
			articleRepository.delete(article.getArticleId());
		}
		else{
			throw new DeleteNotAvailableException("No se puede eliminar video no perteneciente");
		}
	}

	@Transactional(readOnly=true)
	public boolean isOwnerArticle(Long userId, Long articleId) throws InstanceNotFoundException{
		Article article = articleRepository.findOne(articleId);
		User user = userService.findByUserId(userId);
		if (article == null){
			throw new InstanceNotFoundException(NOTEXISTARTICLES);
		}
		if (user==null){
			throw new InstanceNotFoundException(NOTEXISTUSER);
		}
		if (userId.equals(article.getUser().getUserId())){
			return true;
		}
		return false;
	}

	@Transactional(readOnly=true)
	public Article findArticleByArticleId(Long articleId) throws InstanceNotFoundException {
		return articleRepository.findOne(articleId);
	}

	@Transactional
	public void deleteForm(Long userId, Long formId) throws InternalErrorException {
		Form form = formRepository.findOne(formId);
		if (isOwnerForm(userId, formId)){
			List<FormValoration> valorations = formValorationRepository.findAllByFormId(formId);
			if ((valorations!=null) && (!valorations.isEmpty())) {
				for (FormValoration valoration : valorations) {
					formValorationRepository.delete(valoration);
				}
			}
			formRepository.delete(form.getFormId());
		}
		else{
			throw new DeleteNotAvailableException("No se puede eliminar video no perteneciente");
		}
	}

	@Transactional(readOnly=true)
	public boolean isOwnerForm(Long userId, Long formId) throws InstanceNotFoundException{
		Form form = formRepository.findOne(formId);
		User user = userService.findByUserId(userId);
		if (form == null){
			throw new InstanceNotFoundException(NOTEXISTFORMS);
		}
		if (user==null){
			throw new InstanceNotFoundException(NOTEXISTUSER);
		}
		if (userId.equals(form.getUser().getUserId())){
			return true;
		}
		return false;
	}
	

	@Transactional(readOnly=true)
	public Form findFormByFormId(Long formId) throws InstanceNotFoundException {
		return formRepository.findOne(formId);
	}
	
	@Transactional(readOnly=true)
	public Page<Article> findAllArticles(String keywords,Pageable pageable) {
		if ((keywords != null) && (!keywords.isEmpty())) {
			return articleRepository
					.findByArticleTitleContainingIgnoreCaseOrderByValorationArticleDescDateDescArticleIdDesc(
							keywords, pageable);
		} else {
			return articleRepository.findAllByOrderByValorationArticleDescDateDescArticleIdDesc(pageable);
		}
	}
	
	@Transactional(readOnly=true)
	public Page<Form> findAllForms(String keywords,Pageable pageable) {
		if ((keywords != null) && (!keywords.isEmpty())) {
			return formRepository
					.findByFormTitleContainingIgnoreCaseOrderByValorationFormDescDateDescFormIdDesc(
							keywords, pageable);
		} else {
			return formRepository.findAllByOrderByValorationFormDescDateDescFormIdDesc(pageable);
		}
	}
	
	@Transactional(readOnly=true)
	public Page<Article> findAllUserArticles(User user,Pageable pageable) throws UserNotExistsException {
		if (userService.find(user) == null){
			throw new UserNotExistsException("El nombre de usuario no existe");	
		}
		return articleRepository.findByUserOrderByValorationArticleDescDateDescArticleIdDesc(user, pageable);
	}
	
	@Transactional(readOnly=true)
	public Page<Form> findAllUserForms(User user,Pageable pageable) throws UserNotExistsException {
		if (userService.find(user) == null){
			throw new UserNotExistsException("El nombre de usuario no existe");	
		}
		return formRepository.findByUserOrderByValorationFormDescDateDescFormIdDesc(user, pageable);
	}
	
	@Transactional
	public void valorateArticle(Article article, ArticleValoration articleValoration) {
		
		articleValorationRepository.save(articleValoration);
		
		Integer v;
		if (article.getValorationArticle()>0) {
			v = (int) (article.getValorationArticle() * article.getValorationCountArticle()) + articleValoration.getValorationValue();
		} else {
			v = articleValoration.getValorationValue();
		}
		
		Float newValoration = (float) ( v  / (article.getValorationCountArticle() + 1));
		
		article.setValorationArticle(newValoration);
		article.setValorationCountArticle(article.getValorationCountArticle()+1);
		articleRepository.save(article);
		
	}
	
	@Transactional
	public void valorateForm(Form form, FormValoration formValoration) {
		
		formValorationRepository.save(formValoration);
		
		Integer v;
		if (form.getValorationForm()>0) {
			v = (int) (form.getValorationForm() * form.getValorationCountForm()) + formValoration.getValorationValue();
		} else {
			v = formValoration.getValorationValue();
		}
		
		Float newValoration = (float) ( v  / (form.getValorationCountForm() + 1));
		
		form.setValorationForm(newValoration);
		form.setValorationCountForm(form.getValorationCountForm()+1);
		formRepository.save(form);
		
	}
	
	@Transactional(readOnly=true)
	public ArticleValoration getValotarioArticleByUser(User user, Article article) {
		
		return articleValorationRepository.findByUserAndArticleId(user, article.getArticleId());
		
	}
	
	@Transactional(readOnly=true)
	public FormValoration getValotarioFormByUser(User user, Form form) {
		
		return formValorationRepository.findByUserAndFormId(user, form.getFormId());
		
	}
	
}
