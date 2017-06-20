package footballtraining.app.model.video;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import footballtraining.app.model.user.User;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

	Video findByVideoTitle(String videoTitle);
	
	List<Video> findAllByOrderByDateDesc();
	
	Page<Video> findAllByOrderByValorationVideoDescDateDescVideoIdDesc(Pageable pageable);

	Page<Video> findByVideoTitleContainingIgnoreCaseOrderByValorationVideoDescDateDescVideoIdDesc(String videoTitle, Pageable pageable);

	Page<Video> findByUserOrderByValorationVideoDescDateDescVideoIdDesc(User user, Pageable pageable);

}
