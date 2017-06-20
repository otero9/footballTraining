package footballtraining.app.model.exceptions;

/**
 * The Class InstanceNotFoundException.
 */
@SuppressWarnings("serial")
public class InstanceNotFoundException extends InternalErrorException {

	/**
	 * Instantiates a new instance not found exception.
	 *
	 * @param message the message
	 */
	public InstanceNotFoundException(String message) {  
		super(message);
	}

}