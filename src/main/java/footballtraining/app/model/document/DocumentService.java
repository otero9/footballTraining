package footballtraining.app.model.document;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import footballtraining.app.model.exceptions.InstanceNotFoundException;
import footballtraining.app.model.exceptions.InternalErrorException;
import footballtraining.app.model.exceptions.SaveNotAvailableException;
import footballtraining.app.model.exceptions.UserNotExistsException;
import footballtraining.app.model.user.User;

/**
 * The Intarface DocumentService.
 */
public interface DocumentService {

	Article saveArticle(Article article,Document document) throws SaveNotAvailableException;
	
	void deleteArticle(Long userId, Long articleId) throws InternalErrorException;
	
	boolean isOwnerArticle(Long userId, Long articleId) throws InstanceNotFoundException;
	
	Article findArticleByArticleId(Long articleId) throws InstanceNotFoundException;
	
	Form saveForm(Form form, Document document) throws SaveNotAvailableException;
	
	void deleteForm(Long userId, Long formId) throws InternalErrorException;
	
	boolean isOwnerForm(Long userId, Long formId) throws InstanceNotFoundException;
	
	Form findFormByFormId(Long formId) throws InstanceNotFoundException;
	
	Page<Form> findAllForms(String keywords,Pageable pageable);
	
	Page<Article> findAllArticles(String keywords,Pageable pageable);
	
	Page<Article> findAllUserArticles(User user,Pageable pageable) throws UserNotExistsException;
	
	Page<Form> findAllUserForms(User user,Pageable pageable) throws UserNotExistsException;
	
	void valorateArticle(Article article, ArticleValoration articleValoration);
	
	void valorateForm(Form form, FormValoration formValoration);
	
	ArticleValoration getValotarioArticleByUser(User user, Article article);
	
	FormValoration getValotarioFormByUser(User user, Form form);
	
}
