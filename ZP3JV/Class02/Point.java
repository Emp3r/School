package Class02;

import java.util.List;

public class Point {
	private double x;
	private double y;

	public Point(double xx, double yy) {
		this.x = xx;
		this.y = yy;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double distance(Point p) {
		return Math.sqrt((p.x - this.x) * (p.x - this.x) + (p.y - this.y)
				* (p.y - this.y));
	}
	
	// 5. œkol 3. cvi‹en’
	public static Point nearest(Point p, List<Point> points) {
		Point result = null;
		
		for (Point i : points) {
			if (result.distance(p) > i.distance(p)) {
				result = i;
			}
		} 
		return result;
	}

	/* TESTS
	public static void main(String[] args) {
		Point a = new Point(3, 5);
		Point b = new Point(4, 6);
		System.out.print("Vzdalenost: " + a.distance(b));
	}
	*/
}
