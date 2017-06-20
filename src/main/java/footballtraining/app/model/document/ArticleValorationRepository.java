package footballtraining.app.model.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import footballtraining.app.model.user.User;

@Repository
public interface ArticleValorationRepository extends JpaRepository<ArticleValoration, Long> {

	List<ArticleValoration> findAllByArticleId(Long articleId);
	
	ArticleValoration findByUserAndArticleId(User user, Long articleId);

}
