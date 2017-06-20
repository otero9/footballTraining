package footballtraining.app.model.exceptions;

/**
 * The Class SaveNotAvailableException.
 */
@SuppressWarnings("serial")
public class SaveNotAvailableException extends InternalError {
	/**
	 * Instantiates a new save not available exception.
	 *
	 * @param message the message
	 */
	public SaveNotAvailableException(String message) {  
		super(message);
	}
}
