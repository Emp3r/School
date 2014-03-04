package Class06;

/**
 * Trida Rectangle - obdelnik. Ma dva rozdilne konstruktory.
 * @author Josef Podstata
 * @since 1.0
 */
public class Rectangle {
	private Point a, b, c, d;
	private double w, h;

	/**
	 * Prvni konstruktor tridy Rectangle. Vytvari objekt na 
	 * zaklade zaklade bodu <b>a</b> (parametr <i>Point aa</i>) 
	 * a bodu <b>c</b> (<i>Point cc</i>).
	 * @param aa
	 * @param cc
	 */
	public Rectangle(Point aa, Point cc) {
		this.a = aa;
		this.c = cc;
		this.w = Math.abs(c.getX() - a.getX());
		this.h = Math.abs(c.getY() - a.getY());
		this.b = new Point(a.getX() + w, a.getY());
		this.d = new Point(a.getX(), a.getY() + h);
	}

	/**
	 * Druhy konstruktor tridy Rectangle. Vytvari objekt na 
	 * zaklade zaklade bodu <b>a</b> (parametr <i>Point aa</i>), 
	 * vysce <b>h</b> (<i>height</i>) a sirce <b>v</b> (<i>width</i>).
	 * @param aa
	 * @param width
	 * @param height
	 */
	public Rectangle(Point aa, double width, double height) {
		this.a = aa;
		this.w = width;
		this.h = height;
		this.b = new Point(a.getX() + w, a.getY());
		this.c = new Point(a.getX() + w, a.getY() + h);
		this.d = new Point(a.getX(), a.getY() + h);
	}

	/**
	 * Metoda pro vypocet obsahu obdelniku.
	 * @return <b>h</b> * <b>w</b>
	 */
	public double getArea() {
		return h * w;
	}

	/**
	 * Metoda na vypocet vzdalenosti bodu <i>Point p</i> od usecky.
	 * @param p
	 */
	public double distance(Point p) {
		Line la = new Line(a, b);
		Line lb = new Line(b, c);
		Line lc = new Line(d, c);
		Line ld = new Line(a, d);

		double result = la.distance(p);

		if (lb.distance(p) < result) {
			result = lb.distance(p);
		}
		if (lc.distance(p) < result) {
			result = lc.distance(p);
		}
		if (ld.distance(p) < result) {
			result = ld.distance(p);
		}
		return result;
	}

	/* TESTS
	public static void main(String[] args) {
		Point x = new Point(0, 0);
		Point y = new Point(2, 2);
		Point z = new Point(3, 3);
		Rectangle r1 = new Rectangle(x, y);
		System.out.print("Obsah: " + r1.getArea());
		Rectangle r2 = new Rectangle(x, 2, 2);
		System.out.print("\nObsah: " + r2.getArea());
		System.out.print("\nVzdalenost: " + r1.distance(z));
	}
	*/
}
