package footballtraining.app.model.comment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "comment")
public class Comment implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private long commentId;
	
	@NotNull
    @Column(name="userId")
	private Long userId;
	
	@NotNull
    @Column(name="userName")
	private String userName;
	
	@NotNull
    @Column(name="exerciseId")
	private Long exerciseId;
	
	@NotNull
    @Column(name="commentText")
	private String commentText;
	
    @Column(name="commentParentId")
	private Long commentParentId;
    
	@NotNull
    @Column(name="date")
    private Date date;
	
	public Comment() {
	}

	public Comment(Long userId, String userName, Long exerciseId, String commentText) {
		this.userId = userId;
		this.exerciseId = exerciseId;
		this.commentText = commentText;
		this.userName = userName;
		this.date = new Date();
	}
	
	public Comment(Long userId, String userName, Long exerciseId, String commentText, Long commentParentId) {
		this.userId = userId;
		this.exerciseId = exerciseId;
		this.commentText = commentText;
		this.userName = userName;
		this.commentParentId = commentParentId;
		this.date = new Date();
	}

	public long getCommentId() {
		return commentId;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(Long exerciseId) {
		this.exerciseId = exerciseId;
	}

	public void setCommentParentId(Long commentParentId) {
		this.commentParentId = commentParentId;
	}

	public long getCommentParentId() {
		return commentParentId;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}
	
}
