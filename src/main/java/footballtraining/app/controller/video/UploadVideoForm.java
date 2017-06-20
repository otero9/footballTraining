package footballtraining.app.controller.video;

import org.hibernate.validator.constraints.NotBlank;

import footballtraining.app.model.video.Video;


/**
 * The Class VideoForm.
 */
public class UploadVideoForm {

	private static final String NOT_BLANK_MESSAGE = "Fill in the field";
	
	@NotBlank(message = UploadVideoForm.NOT_BLANK_MESSAGE)
	private String videoTitle;
	
	@NotBlank(message = UploadVideoForm.NOT_BLANK_MESSAGE)
	private String videoURL;
	
	@NotBlank(message = UploadVideoForm.NOT_BLANK_MESSAGE)
	private String videoDescription;

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	public String getVideoDescription() {
		return videoDescription;
	}

	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}
	
	/**
	 * Función que devuelve un video con los campos correctos a partir de los datos introducidos en un formulario.
	 *
	 * @return El video con los datos introducidos en el formulario
	 */
	public Video uploadVideo() {
		return new Video(null, videoTitle, videoURL, videoDescription, Float.valueOf(0), 0);
	}
	
	
}
