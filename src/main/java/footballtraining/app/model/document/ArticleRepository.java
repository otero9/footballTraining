package footballtraining.app.model.document;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import footballtraining.app.model.user.User;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

	Article findByArticleTitle(String articleTitle);
	
	List<Article> findAllByOrderByDateDesc();
	
	Page<Article> findAllByOrderByValorationArticleDescDateDescArticleIdDesc(Pageable pageable);

	Page<Article> findByArticleTitleContainingIgnoreCaseOrderByValorationArticleDescDateDescArticleIdDesc(String artileTitle, Pageable pageable);

	Page<Article> findByUserOrderByValorationArticleDescDateDescArticleIdDesc(User user, Pageable pageable);
}
