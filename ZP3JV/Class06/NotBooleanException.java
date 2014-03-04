package Class06;

/**
 * Hodnota neni typu boolean, nelze rozpoznat vysledek podminky.
 * @author Josef Podstata
 */
public class NotBooleanException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotBooleanException() {
		super("Not a boolean value.");
	}
	
	public NotBooleanException(Throwable cause) {
		super("Not a boolean value.", cause);
	}
}
