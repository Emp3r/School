package RPN;

/**
 * Delitel je nula. Deleni nulou nema v oboru realnych cisel smysl.
 * @author Josef Podstata
 */
public class DivideByZeroException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DivideByZeroException() {
		super("Cannot divide by zero.");
	}
	
	public DivideByZeroException(Throwable cause) {
		super("Cannot divide by zero.", cause);
	}
}
