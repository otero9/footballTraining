package footballtraining.app.controller.exercise;

import org.hibernate.validator.constraints.NotBlank;
import footballtraining.app.model.comment.Comment;
import footballtraining.app.model.user.User;

/**
 * The Class CreateCommentForm.
 */
public class CreateCommentForm {
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	
	
	@NotBlank(message = CreateCommentForm.NOT_BLANK_MESSAGE)
	private String text;
	private User user;
	private Long exerciseId;
	


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(Long exerciseId) {
		this.exerciseId = exerciseId;
	}

	/**
	 * Creates the comment.
	 *
	 * @return the comment
	 */
	public Comment createComment(){
		return new Comment(user.getUserId(), user.getUserName(), exerciseId, text);
	}

}
