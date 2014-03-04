package Class06;

/**
 * Trida Line - usecka v rovine.
 * @author Josef Podstata
 * @since 1.0
 */
public class Line {
	private Point a;
	private Point b;

	/**
	 * <b>Konstruktor</b> tridy Line. Vytvari objekt 
	 * na zaklade dvou bodu (<i>aa</i> a <i>bb</i>).
	 * @param aa
	 * @param bb
	 */
	public Line(Point aa, Point bb) {
		this.a = aa;
		this.b = bb;
	}

	/**
	 * Metoda pro zjisteni delky usecky.
	 * @return Vzdalenost mezi body <b>a</b> a <b>b</b>.
	 */
	public double getLength() {
		return a.distance(b);
	}

	/** 
	 * Metoda na vypocet vzdalenosti bodu od usecky.
	 * @param p
	 * @return Nejmensi vzdalenost mezi bodem <i>p</i> a vsemi body lezicimi na usecce.
	 */
	public double distance(Point p) {
		double aa = b.getY() - a.getY();
		double bb = b.getX() - a.getX();
		double cc = (aa * a.getX() - bb * a.getY()) * -1;

		double d = ((aa * p.getX()) + (bb * p.getY()) + cc)
				/ Math.sqrt(aa * aa + bb * bb);
		double pa = p.distance(a);
		double pb = p.distance(b);

		double l = this.getLength();
		double la = Math.sqrt(pa * pa - d * d);
		double lb = Math.sqrt(pb * pb - d * d);

		if ((la <= l) && (lb <= l)) {
			return d;
		} else {
			return Math.abs(pa > pb ? pb : pa);
		}
	}

	/* TESTS
	public static void main(String[] args) {
		Point a = new Point(0, 0);
		Point b = new Point(5, 0);
		Point c = new Point(2, 4);
		Line l = new Line(a, b);
		double vzdalenost = l.distance(c);
		System.out.print("Vzdalenost: " + vzdalenost);
		Point e = new Point(3, 1);
		Point e1 = new Point(8, 5);
		Point f1 = new Point(5, 9);
		Line l1 = new Line(e, e1);
		double vzdalenost1 = l1.distance(f1);
		System.out.print("\nVzdalenost: " + vzdalenost1);
		Point e2 = new Point(0, 0);
		Point e3 = new Point(2, 2);
		Point f3 = new Point(3, 2);
		Line l3 = new Line(e2, e3);
		double vzdalenost3 = l3.distance(f3);
		System.out.print("\nVzdalenost: " + vzdalenost3);
	}
	*/
}
