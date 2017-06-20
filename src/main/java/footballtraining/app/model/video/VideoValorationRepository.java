package footballtraining.app.model.video;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import footballtraining.app.model.user.User;

@Repository
public interface VideoValorationRepository extends JpaRepository<VideoValoration, Long> {
	
	List<VideoValoration> findAllByVideoId(Long videoId);

	VideoValoration findByUserAndVideoId(User user, Long videoId);
}
