package RPN;

/**
 * Promenna nema svou hodnotu v predanem argumentu promennych.
 * @author Josef Podstata
 */
public class NotVariableException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NotVariableException() {
		super("Variable was not found in the argument.");
	}
	
	public NotVariableException(Throwable cause) {
		super("Variable was not found in the argument.", cause);
	}
}
