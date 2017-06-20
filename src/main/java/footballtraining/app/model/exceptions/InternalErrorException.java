package footballtraining.app.model.exceptions;

/**
 * The Class InternalErrorException.
 */
@SuppressWarnings("serial")
public class InternalErrorException extends Exception {

	/**
	 * Instantiates a new internal error exception.
	 *
	 * @param message the message
	 */
	public InternalErrorException(String message) {  
		super(message);
	}

}

