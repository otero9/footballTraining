package footballtraining.app.model.exceptions;

/**
 * The Class UserNotExistsException.
 */
@SuppressWarnings("serial")
public class UserNotExistsException extends InternalError {
	/**
	 * Instantiates a new save not available exception.
	 *
	 * @param message the message
	 */
	public UserNotExistsException(String message) {  
		super(message);
	}
}

