package footballtraining.app.model.video;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import footballtraining.app.model.user.User;

@Entity
@Table(name = "video")
public class Video implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private long videoId;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;
	
	@NotNull
	private String videoTitle;
	
	@NotNull
	private String videoURL;
	
	private String videoDescription;
	
	@NotNull
    @Column(name="date")
    private Date date;
	
	@NotNull
	private Float valorationVideo;
	
	@NotNull
	private int valorationCountVideo;
	
	public Video() {
	}

	public Video(User user, String videoTitle, String videoURL, String videoDescription, Float valorationVideo, int valorationCountVideo) {
		this.user = user;
		this.videoTitle = videoTitle;
		this.videoURL = videoURL;
		this.videoDescription = videoDescription;
		this.valorationVideo = valorationVideo;
		this.valorationCountVideo = valorationCountVideo;
		this.date = new Date();
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getVideoId() {
		return videoId;
	}

	public void setVideoId(long videoId) {
		this.videoId = videoId;
	}

	public String getVideoDescription() {
		return videoDescription;
	}

	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	public Float getValorationVideo() {
		return valorationVideo;
	}

	public void setValorationVideo(Float valorationVideo) {
		this.valorationVideo = valorationVideo;
	}

	public int getValorationCountVideo() {
		return valorationCountVideo;
	}

	public void setValorationCountVideo(int valorationCountVideo) {
		this.valorationCountVideo = valorationCountVideo;
	}

}
