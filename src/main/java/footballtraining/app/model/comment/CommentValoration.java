package footballtraining.app.model.comment;

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
@Table(name = "commentvaloration")
public class CommentValoration implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private long commentValorationId;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;

	@Column(name = "commentId")
	@NotNull
	private long commentId;

	@NotNull
	private int valorationValue;

	public CommentValoration() {
	}

	public CommentValoration(User user, long commentId, int valorationValue) {
		this.user = user;
		this.commentId = commentId;
		this.valorationValue = valorationValue;
	}

	public long getCommentValorationId() {
		return commentValorationId;
	}

	public void setCommentValorationId(long commentValorationId) {
		this.commentValorationId = commentValorationId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public int getValorationValue() {
		return valorationValue;
	}

	public void setValorationValue(int valorationValue) {
		this.valorationValue = valorationValue;
	}

}