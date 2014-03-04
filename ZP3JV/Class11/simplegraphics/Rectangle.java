package simplegraphics;

public class Rectangle {
	private Point a, b, c, d;
	private double w, h;
	private String name;

	public Rectangle(String name, Point aa, Point cc) {
		this.name = name;
		this.a = aa;
		this.c = cc;
		this.w = Math.abs(c.getX() - a.getX());
		this.h = Math.abs(c.getY() - a.getY());
		this.b = new Point(a.getX() + w, a.getY());
		this.d = new Point(a.getX(), a.getY() + h);
	}

	public Rectangle(String name, Point aa, double width, double height) {
		this.name = name;
		this.a = aa;
		this.w = width;
		this.h = height;
		this.b = new Point(a.getX() + w, a.getY());
		this.c = new Point(a.getX() + w, a.getY() + h);
		this.d = new Point(a.getX(), a.getY() + h);
	}
	
	public String getName() {
		return name;
	}

	public double getArea() {
		return h * w;
	}

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
