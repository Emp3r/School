package Class06;

/**
 * Trida Circle - kruznice v rovine.
 * @author Josef Podstata
 * @since 1.0
 */
public class Circle {
	private Point x;
	private double r;
	
	/** 
	 * <b>Konstruktor</b> tridy Circle. Vytvari objekt na zaklade 
	 * souradnic <b>x</b> (<i>xx</i>) a polomeru <b>r</b> (<i>radius</i>).
	 * @param xx (souradnice)
	 * @param radius (polomer) 
	 */
	public Circle(Point xx, double radius) {
		this.x = xx;
		this.r = radius;
	}

	/**
	 * Metoda na vypocet obsahu kruznice.
	 * @return Math.PI * <b>r</b> * <b>r</b> (obsah kruznice) 
	 */
	public double getArea() {
		return Math.PI * r * r;
	}
	
	/**
	 * Metoda na vypocet vzdalenosti bodu (<i>p</i>) od kruznice.
	 * @param p
	 * @return Vzdalenost bodu <i>p</i> od kruznice. 
	 */
	public double distance(Point p) {
		return Math.abs(x.distance(p) - r);
	}
	
	/* TESTS
	public static void main(String[] args) {
		Point p = new Point(4, 4);
		Point p1 = new Point(10, 10);
		Circle c = new Circle(p, 2);
		System.out.print("Vzdalenost: " + c.distance(p1));
	}	
	*/	
}
