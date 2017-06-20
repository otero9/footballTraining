package footballtraining.app.model.exceptions;

/**
 * The Class ExerciseTypeNotExistsException.
 */
@SuppressWarnings("serial")
public class ExerciseTypeNotExistsException extends InternalError {
	/**
	 * Instantiates a new save not available exception.
	 *
	 * @param message the message
	 */
	public ExerciseTypeNotExistsException(String message) {  
		super(message);
	}
}