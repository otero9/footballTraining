package footballtraining.app.model.video;

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
@Table(name = "videovaloration")
public class VideoValoration implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private long videoValorationId;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;
	
    @Column(name="videoId")
	@NotNull
	private long videoId;
	
	@NotNull
	private int valorationValue;
	
	public VideoValoration() {
	}

	public VideoValoration(User user, long videoId, int valorationValue) {
		this.user = user;
		this.videoId = videoId;
		this.valorationValue = valorationValue;
	}

	public long getVideoValorationId() {
		return videoValorationId;
	}

	public void setVideoValorationId(long videoValorationId) {
		this.videoValorationId = videoValorationId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getVideoId() {
		return videoId;
	}

	public void setVideoId(long videoId) {
		this.videoId = videoId;
	}

	public int getValorationValue() {
		return valorationValue;
	}

	public void setValorationValue(int valorationValue) {
		this.valorationValue = valorationValue;
	}

}
