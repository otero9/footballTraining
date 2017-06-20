package footballtraining.app.model.exceptions;

/**
 * The Class DeleteNotAvailableException.
 */
@SuppressWarnings("serial")
public class DeleteNotAvailableException extends InternalErrorException {

	/**
	 * Instantiates a new delete not available exception.
	 *
	 * @param message the message
	 */
	public DeleteNotAvailableException(String message) {  
		super(message);
	}

}
