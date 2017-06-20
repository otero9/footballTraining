package footballtraining.app.model.video;

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
import footballtraining.app.model.exceptions.SaveNotAvailableException;
import footballtraining.app.model.exceptions.UserNotExistsException;
import footballtraining.app.model.user.User;
import footballtraining.app.model.user.UserService;

/**
 * The Class VideoService.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class VideoServiceImpl implements VideoService{

	@Autowired
	private VideoRepository videoRepository;
	
	@Autowired
	private VideoValorationRepository videoValorationRepository;
	
	@Autowired
	private UserService userService;
	
	private static final String NOTEXISTVIDEOS = "El video no existe";
	private static final String NOTEXISTUSER = "El usuario no existe";
	
	
	 /**
	 * Función que almacena los datos relativos a un blog en base de datos.
	 *
	 * @param blog Este parámetro representa el objeto Blog que se quiere almacenar
	 * @return El blog resultante, despues de almacenarlo
	 * @exception SaveNotAvailableException excepción SaveNotAvailable
	 */	
	@Transactional
	public Video save(Video video) throws SaveNotAvailableException {
		if (userService.find(video.getUser()) == null){
			throw new UserNotExistsException("El nombre de usuario no existe");	
		}
		videoRepository.save(video);
		video.getUser().addVideo(video);
		return video;
	}
	
	/**
	 * Función que elimina los datos relativos a un blog en base de datos.
	 *
	 * @param accountId Representa el id de cuenta
	 * @param blogId Este parámetro representa el identificador de objeto Blog que se quiere eliminar
	 * @throws InternalErrorException the internal error exception
	 */
	@Transactional
	public void delete(Long userId, Long videoId) throws InternalErrorException {
		Video video = videoRepository.findOne(videoId);
		if (isOwnerVideo(userId, videoId)){
			List<VideoValoration> valorations = videoValorationRepository.findAllByVideoId(videoId);
			if ((valorations!=null) && (!valorations.isEmpty())) {
				for (VideoValoration valoration : valorations) {
					videoValorationRepository.delete(valoration);
				}
			}
			videoRepository.delete(video.getVideoId());
		}
		else{
			throw new DeleteNotAvailableException("No se puede eliminar video no perteneciente");
		}
	}
	
	/**
	 * Función que permite saber si una cuenta es la propiestaria de un blog.
	 *
	 * @param accountId Este parámetro representa el id de la cuenta
	 * @param blogId Este parámetro representa el id del blog
	 * @return Un booleano, devolviendo true si la cuenta es la propiestaria del blog y false en caso contrario
	 * @throws InstanceNotFoundException excepción de instancia no encontrada
	 */
	@Transactional(readOnly=true)
	public boolean isOwnerVideo(Long userId, Long videoId) throws InstanceNotFoundException{
		Video video = videoRepository.findOne(videoId);
		User user = userService.findByUserId(userId);
		if (video == null){
			throw new InstanceNotFoundException(NOTEXISTVIDEOS);
		}
		if (user==null){
			throw new InstanceNotFoundException(NOTEXISTUSER);
		}
		if (userId.equals(video.getUser().getUserId())){
			return true;
		}
		return false;
	}
	
	@Transactional(readOnly=true)
	public Video find(Long videoId) throws InstanceNotFoundException{
		return videoRepository.findOne(videoId);
	}
	
	@Transactional(readOnly=true)
	public VideoValoration getValotarioVideoByUser(User user, Video video) {
		
		return videoValorationRepository.findByUserAndVideoId(user, video.getVideoId());
		
	}
	
	@Transactional
	public void valorateVideo(Video video, VideoValoration videoValoration) {
		
		videoValorationRepository.save(videoValoration);
		
		Integer v;
		if (video.getValorationVideo()>0) {
			v = (int) (video.getValorationVideo() * video.getValorationCountVideo()) + videoValoration.getValorationValue();
		} else {
			v = videoValoration.getValorationValue();
		}
		
		Float newValoration = (float) ( v  / (video.getValorationCountVideo() + 1));
		
		video.setValorationVideo(newValoration);
		video.setValorationCountVideo(video.getValorationCountVideo()+1);
		videoRepository.save(video);
		
	}
	
	@Transactional(readOnly=true)
	public Page<Video> findAllUserVideos(User user,Pageable pageable) throws UserNotExistsException {
		if (userService.find(user) == null){
			throw new UserNotExistsException("El nombre de usuario no existe");	
		}
		return videoRepository.findByUserOrderByValorationVideoDescDateDescVideoIdDesc(user, pageable);
	}
	
	@Transactional(readOnly=true)
	public Page<Video> findAllVideos(String keywords,Pageable pageable) {
		if ((keywords != null) && (!keywords.isEmpty())) {
			return videoRepository
					.findByVideoTitleContainingIgnoreCaseOrderByValorationVideoDescDateDescVideoIdDesc(
							keywords, pageable);
		} else {
			return videoRepository.findAllByOrderByValorationVideoDescDateDescVideoIdDesc(pageable);
		}
	}
	
	
	@Transactional(readOnly=true)
	public Video findVideoByVideoId(Long videoId) throws InstanceNotFoundException {
		return videoRepository.findOne(videoId);
	}
	
}
