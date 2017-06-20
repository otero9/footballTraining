package footballtraining.app.controller.video;

import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import footballtraining.app.model.exceptions.InstanceNotFoundException;
import footballtraining.app.model.exceptions.SaveNotAvailableException;
import footballtraining.app.model.user.User;
import footballtraining.app.model.user.UserRepository;
import footballtraining.app.model.user.UserService;
import footballtraining.app.model.video.Video;
import footballtraining.app.model.video.VideoRepository;
import footballtraining.app.model.video.VideoServiceImpl;
import footballtraining.app.model.video.VideoValoration;
import footballtraining.app.model.video.VideoValorationRepository;
import footballtraining.app.util.PageWrapper;

/**
 * The Class VideoController.
 */
@Controller
public class VideoController {
	
	private static final String VIDEOPAGE = "video/video"; 
	private static final String ERROR_PAGE = "error/general";
	private static final String UPLOADVIDEO_PAGE = "video/uploadVideo";
	
	@Autowired
	private UploadVideoFormValidator uploadVideoValidator;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private VideoServiceImpl videoService;
	
	@Autowired
	private VideoRepository videoRepository;
	
	@Autowired
	private VideoValorationRepository videoValorationRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Función que permite hacer una petición GET a la página uploadVideo.
	 *
	 * @param principal Este parámetro representa una instancia del objeto Principal
	 * @param model Este parámetro representa una instancia del objeto Model
	 * @return Una cadena de texto, referencia a la página uploadVideo
	 */
	@RequestMapping(value = "/video/uploadVideo", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String getUploadVideo(Principal principal, Model model) {
		User user = userService.findByUserName(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute(new UploadVideoForm());
		return UPLOADVIDEO_PAGE;
	}
	
	@RequestMapping(value = "/video/uploadVideo", method = RequestMethod.POST)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String uploadVideo(@Valid @ModelAttribute UploadVideoForm uploadVideoForm, Errors errors, RedirectAttributes ra,
			Principal principal, Model model) {
		User user = null;
		if (principal.getName()!=null) {
			user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		else {
			String errorMessage = "Not has permission";
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}
		uploadVideoValidator.validate(uploadVideoForm, errors);
		if (errors.hasErrors()) {
			return UPLOADVIDEO_PAGE;
		}
		Video video = uploadVideoForm.uploadVideo();
		try {
			if ((video.getVideoURL() != null) && (!video.getVideoURL().isEmpty())) {

				String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";

				Pattern compiledPattern = Pattern.compile(pattern);
				Matcher matcher = compiledPattern.matcher(video.getVideoURL());
				String id = null;
				if (matcher.find()) {
					id = matcher.group();
				}

				if ((id != null) && (!id.isEmpty())) {
					video.setVideoURL("https://www.youtube.com/embed/"+id);
					video.setUser(user);
					video = videoService.save(video);
					return "redirect:/video/" + video.getVideoId();
				}
			}

		} catch (SaveNotAvailableException e) {
			String errorMessage = "Video cannot be uploaded";
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.warn(errorMessage, e);
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}
		return "error/general";
	}
	
	/**
	 * Función que permite hacer una petición GET a la página video con un id concreto.
	 *
	 * @param principal Este parámetro representa una instancia del objeto Principal
	 * @param id Este parámetro representa el id del video cuya página queremos obtener
	 * @param model Este parámetro representa una instancia del objeto Model
	 * @return Una cadena de texto, referencia a la página video
	 */
	@RequestMapping(value = "/video/{id}", method = RequestMethod.GET)
	public String video(Principal principal, @PathVariable Long id, Model model) {
		
		User user = null;
		if (principal != null) {
			user = userRepository.findOneByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		
		Video video;
		try {
			video = videoService.find(id);
			if (video != null){
				model.addAttribute("video", video);
			}else{
				throw new InstanceNotFoundException("Video "+ id + " not exists");
			}
			if ((user != null) && (user.getUserName()!=video.getUser().getUserName())) { 
				VideoValoration videoValoration = videoValorationRepository.findByUserAndVideoId(user, id);
				if (videoValoration!=null) {
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
			String errorMessage = "The video does not exists";
			logger.warn(errorMessage, e);
			model.addAttribute("errorMessage", errorMessage);
			return ERROR_PAGE;
		}

		return VIDEOPAGE;
	}
	
	@RequestMapping(value = "/valorateVideo/{videoId}/{valoration}", method = RequestMethod.POST)
	public String valorateVideo(Principal principal, @PathVariable Long videoId, @PathVariable Integer valoration, Model model) {
		User user = null;
		if ((principal != null)&&(valoration!=null)) {
			user = userRepository.findOneByUserName(principal.getName());
			model.addAttribute("user", user);
			
			Video video = videoRepository.getOne(videoId);
			
			if ((user!=null)&&(video!=null)) {
				try {
					VideoValoration videoValoration = new VideoValoration(user, videoId, valoration);
					videoValorationRepository.save(videoValoration);
					Integer v;
					if (video.getValorationVideo()>0) {
						v = (int) (video.getValorationVideo() * video.getValorationCountVideo()) + valoration;
					} else {
						v = valoration;
					}
					 
					Float newValoration = (float) (v / (video.getValorationCountVideo() + 1));
					video.setValorationVideo(newValoration);
					video.setValorationCountVideo(video.getValorationCountVideo()+1);
					videoRepository.save(video);
				} catch (Exception e) {
					String errorMessage = "Error occurred to vote this video";
					logger.warn(errorMessage, e);
					model.addAttribute("errorMessage", errorMessage);
					return ERROR_PAGE;
				}
			} else  {
				String errorMessage = "The video does not exists";
				logger.warn(errorMessage);
				model.addAttribute("errorMessage", errorMessage);
				return ERROR_PAGE;
			}

			return "redirect:/video/"+videoId;
		} else {
			String errorMessage = "You don't have permission";
			logger.warn(errorMessage);
			model.addAttribute("errorMessage", errorMessage);
			return ERROR_PAGE;
		}
		
	}
	
	@RequestMapping(value = "/video/allVideos", method = RequestMethod.GET)
	public String allVideos(Principal principal, Model model, Pageable pageable,
			@RequestParam(required = false, name = "keywords") String keywords) {

		if (principal != null) {

			User user = userRepository.findOneByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		Page<Video> videoPage;
		PageWrapper<Video> page;
		pageable = new PageRequest(pageable.getPageNumber(), 6);
		try {

			if ((keywords != null) && (!keywords.isEmpty())) {
				model.addAttribute("keywords", keywords);
				videoPage = videoRepository
						.findByVideoTitleContainingIgnoreCaseOrderByValorationVideoDescDateDescVideoIdDesc(keywords,
								pageable);
			} else {
				model.addAttribute("keywords", "");
				videoPage = videoRepository.findAllByOrderByValorationVideoDescDateDescVideoIdDesc(pageable);
			}

			page = new PageWrapper<>(videoPage, "/exercise/allExercises");

			model.addAttribute("videos", page.getContent());
			model.addAttribute("page", page);
			return "video/allVideos";
		} catch (Exception e) {
			String errorMessage = "Not authorized page";
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}

	}

	@RequestMapping(value = "/video/allVideos", method = RequestMethod.POST)
	public String allVideosFilter(Principal principal, Model model, Pageable pageable,
			@RequestParam(required = true, name = "keywords") String keywords) {

		if (principal != null) {

			User user = userRepository.findOneByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		Page<Video> videoPage;
		PageWrapper<Video> page;
		pageable = new PageRequest(pageable.getPageNumber(), 6);
		try {

			if ((keywords != null) && (!keywords.isEmpty())) {
				model.addAttribute("keywords", keywords);
				videoPage = videoRepository
						.findByVideoTitleContainingIgnoreCaseOrderByValorationVideoDescDateDescVideoIdDesc(keywords,
								pageable);
			} else {
				model.addAttribute("keywords", "");
				videoPage = videoRepository.findAllByOrderByValorationVideoDescDateDescVideoIdDesc(pageable);
			}

			page = new PageWrapper<>(videoPage, "/video/allVideos");

			model.addAttribute("videos", page.getContent());
			model.addAttribute("page", page);
			return "video/allVideos";
		} catch (Exception e) {
			String errorMessage = "Not authorized page";
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}
	}
	
	@RequestMapping(value = "/video/userVideos/{userName}", method = RequestMethod.GET)
	public String userVideos(Principal principal, Model model, Pageable pageable, @PathVariable String userName) {

		if (principal != null) {
			User user = userRepository.findOneByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		User userVideo = null;
		try {
			userVideo = userRepository.findOneByUserName(userName);
		} catch (Exception e) {
			String errorMessage = "User doesn't exists";
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}
		if (userVideo!=null) {
			model.addAttribute("userVideo", userVideo);
			Page<Video> videoPage;
			PageWrapper<Video> page;
			pageable = new PageRequest(pageable.getPageNumber(), 9);
			try {
				videoPage = videoRepository.findByUserOrderByValorationVideoDescDateDescVideoIdDesc(userVideo, pageable);
				page = new PageWrapper<>(videoPage, "/video/userVideos/"+userName);

				model.addAttribute("videos", page.getContent());
				model.addAttribute("page", page);
				return "video/userVideos";
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
	
	@RequestMapping(value = "video/deleteVideo/{videoId}", method = RequestMethod.POST)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String deleteVideo(@PathVariable String videoId, Principal principal, Model model) {
		User user = userRepository.findOneByUserName(principal.getName());
		model.addAttribute("user", user);
		try {
			Video video = videoService.find(Long.valueOf(videoId));
			videoService.delete(user.getUserId(), video.getVideoId());
		} catch (Exception e) {
			String errorMessage = "Selected comment to delete is not existing";
			model.addAttribute("errorMessage", errorMessage);
			return ERROR_PAGE;
		}

		return "user/"+user.getUserId()+"/details";
	}
	
}
