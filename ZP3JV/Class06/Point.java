package Class06;

import java.util.List;

/**
 * Trida Point - bod v rovine urceny dvema souradnicemi.
 * @author Josef Podstata
 * @since 1.1
 */
public class Point {
	private double x;
	private double y;

	/**
	 * <b>Konstruktor</b> tridy Point. Vytvari objekt na 
	 * zaklade souradnic (<i>xx</i> a <i>yy</i>).
	 * @param xx
	 * @param yy
	 */
	public Point(double xx, double yy) {
		this.x = xx;
		this.y = yy;
	}

	/**
	 * Metoda pro zjisteni souradnic na ose <b>x</b>.
	 * @return double <b>x</b>
	 */
	public double getX() {
		return x;
	}

	/**
	 * Metoda pro zjisteni souradnic na ose <b>y</b>.
	 * @return double <b>y</b>
	 */
	public double getY() {
		return y;
	}

	/**
	 * Metoda na vypocet vzdalenosti dvou bodu v rovine.
	 * @param p
	 * @return Vzdalenost od bodu <i>p</i>.
	 */
	public double distance(Point p) {
		return Math.sqrt((p.x - this.x) * (p.x - this.x) + (p.y - this.y)
				* (p.y - this.y));
	}

	// 5. ukol 3. cviceni
	/**
	 * Metoda pro zjisteni nejblizsiho bodu ze seznamu bodu (<i>points</i>) od bodu (<i>p</i>).
	 * @param p
	 * @param points
	 * @return Bod ze seznamu <i>points</i>, ktery je nejblize k bodu <i>p</i>.
	 */
	public static Point nearest(Point p, List<Point> points) {
		Point result = null;

		for (Point i : points) {
			if (result.distance(p) > i.distance(p)) {
				result = i;
			}
		}
		return result;
	}

	/*
	 * TESTS public static void main(String[] args) { Point a = new Point(3, 5);
	 * Point b = new Point(4, 6); System.out.print("Vzdalenost: " +
	 * a.distance(b)); }
	 */
}
