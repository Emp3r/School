package Class06;

/**
 * Neporovnatelne hodnoty. Pravdepodobne je jedna z hodnot typu boolean.
 * @author Josef Podstata
 */
public class IncomparableValuesException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public IncomparableValuesException() {
		super("Incomparable values.");
	}
	
	public IncomparableValuesException(Throwable cause) {
		super("Incomparable values.", cause);
	}
}
