package footballtraining.app.model.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentValorationRepository extends JpaRepository<CommentValoration, Long> {

}
