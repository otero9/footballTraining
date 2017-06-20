package footballtraining.app.controller.video;

import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import footballtraining.app.model.exceptions.SaveNotAvailableException;
import footballtraining.app.model.user.User;
import footballtraining.app.model.user.UserRepository;
import footballtraining.app.model.user.UserService;
import footballtraining.app.model.video.Video;
import footballtraining.app.model.video.VideoServiceImpl;

/**
 * The Class UploadVideoController.
 */
@Controller
public class UploadVideoController {

//	private static final String UPLOADVIDEO_PAGE = "video/uploadVideo";
//	
//	@Autowired
//	private UploadVideoFormValidator uploadVideoValidator;
//	
//	@Autowired
//	private UserService userService;
//	
//	@Autowired
//	private VideoServiceImpl videoService;
//	
//	/**
//	 * Función que permite hacer una petición GET a la página uploadVideo.
//	 *
//	 * @param principal Este parámetro representa una instancia del objeto Principal
//	 * @param model Este parámetro representa una instancia del objeto Model
//	 * @return Una cadena de texto, referencia a la página uploadVideo
//	 */
//	@RequestMapping(value = "/video/uploadVideo", method = RequestMethod.GET)
//	@ResponseStatus(value = HttpStatus.OK)
//	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
//	public String getUploadVideo(Principal principal, Model model) {
//		User user = userService.findByUserName(principal.getName());
//		model.addAttribute("user", user);
//		model.addAttribute(new UploadVideoForm());
//		return UPLOADVIDEO_PAGE;
//	}
//	
//	@RequestMapping(value = "/video/uploadVideo", method = RequestMethod.POST)
//	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
//	public String uploadVideo(@Valid @ModelAttribute UploadVideoForm uploadVideoForm, Errors errors, RedirectAttributes ra,
//			Principal principal, Model model) {
//		User user = userService.findByUserName(principal.getName());
//		model.addAttribute("user", user);
//		uploadVideoValidator.validate(uploadVideoForm, errors);
//		if (errors.hasErrors()) {
//			return UPLOADVIDEO_PAGE;
//		}
//		Video video = uploadVideoForm.uploadVideo();
//		try {
//			if ((video.getVideoURL() != null) && (!video.getVideoURL().isEmpty())) {
//
//				String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
//
//				Pattern compiledPattern = Pattern.compile(pattern);
//				Matcher matcher = compiledPattern.matcher(video.getVideoURL());
//				String id = null;
//				if (matcher.find()) {
//					id = matcher.group();
//				}
//
//				if ((id != null) && (!id.isEmpty())) {
//					video.setVideoURL("https://www.youtube.com/embed/"+id);
//				}
//			}
//			video.setUser(user);
//			video = videoService.save(video);
//			return "redirect:/video/" + video.getVideoId();
//		} catch (SaveNotAvailableException e) {
//			String errorMessage = "Video cannot be uploaded";
//			Logger logger = LoggerFactory.getLogger(this.getClass());
//			logger.warn(errorMessage, e);
//			model.addAttribute("errorMessage", errorMessage);
//			return "error/general";
//		}
//	}
	
	
	
}
