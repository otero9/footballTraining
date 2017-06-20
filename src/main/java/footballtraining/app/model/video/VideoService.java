package footballtraining.app.model.video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import footballtraining.app.model.exceptions.InstanceNotFoundException;
import footballtraining.app.model.exceptions.InternalErrorException;
import footballtraining.app.model.exceptions.SaveNotAvailableException;
import footballtraining.app.model.exceptions.UserNotExistsException;
import footballtraining.app.model.user.User;

/**
 * The Interface VideoService.
 */
public interface VideoService {

	Video save(Video video) throws SaveNotAvailableException;
	
	void delete(Long userId, Long videoId) throws InternalErrorException;
	
	boolean isOwnerVideo(Long userId, Long videoId) throws InstanceNotFoundException;
	
	Video find(Long videoId) throws InstanceNotFoundException;
	
	VideoValoration getValotarioVideoByUser(User user, Video video) ;
	 
	void valorateVideo(Video video, VideoValoration videoValoration);
	
	Page<Video> findAllUserVideos(User user,Pageable pageable) throws UserNotExistsException;
	
	Page<Video> findAllVideos(String keywords,Pageable pageable);
	
	Video findVideoByVideoId(Long videoId) throws InstanceNotFoundException;
	
}
