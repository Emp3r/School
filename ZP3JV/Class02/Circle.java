package Class02;

public class Circle {
	private Point x;
	private double r;
	
	public Circle(Point xx, double radius) {
		this.x = xx;
		this.r = radius;
	}

	public double getArea() {
		return Math.PI * r * r;
	}
	
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
