package Class06;

/**
 * Trida Square - ctverec. Trida je potomkem tridy <br>
 * Rectangle (ctverec je specialni pripad obdelniku).
 * @author Josef Podstata
 * @since 1.0
 */
public class Square extends Rectangle {

	/**
	 * Konstruktor tridy Square. Vytvari objekt na zaklade
	 * dvou parametru (<i>aa</i> a <i>size</i>).
	 * @param aa
	 * @param size
	 */
	public Square(Point aa, double size) {
		super(aa, size, size);
	}
}
