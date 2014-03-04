package Class04;

import java.util.List;

public class Point {
	private int x;
	private int y;

	public Point(int xx, int yy) {
		this.x = xx;
		this.y = yy;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double distance(Point p) {
		return Math.sqrt((p.x - this.x) * (p.x - this.x) + (p.y - this.y)
				* (p.y - this.y));
	}
	
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

	}
	*/
}
