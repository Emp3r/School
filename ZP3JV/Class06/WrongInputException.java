package Class06;

/**
 * Predany argument neni ve spravnem tvaru.
 * @author Josef Podstata
 */
public class WrongInputException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public WrongInputException() {
		super("Input form is incorrect.");
	}

	public WrongInputException(Throwable cause) {
		super("Input form is incorrect.", cause);
	}

	public WrongInputException(String message) {
		super(message);
	}

	public WrongInputException(String message, Throwable cause) {
		super(message, cause);
	}
}
