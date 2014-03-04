package Class04;

import java.util.List;

public class NewPoint {
	private int x;
	private int y;

	public NewPoint(int xx, int yy) {
		this.x = xx;
		this.y = yy;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double distance(NewPoint p) {
		return Math.sqrt((p.x - this.x) * (p.x - this.x) + (p.y - this.y)
				* (p.y - this.y));
	}
	
	public static NewPoint nearest(NewPoint p, List<NewPoint> points) {
		NewPoint result = null;
		
		for (NewPoint i : points) {
			if (result.distance(p) > i.distance(p)) {
				result = i;
			}
		} 
		return result;
	}
	
	@Override
    public int hashCode() {
	    int hash = 7;
	    hash = 71 * hash + this.x;
	    hash = 71 * hash + this.y;
	    
        return hash; 
    }
	
	@Override
    public boolean equals(Object o) 
    {
        if (this == o)
          return true;
        
        if (o == null) { 
            return false; 
        } 
        
        if (this.getClass() != o.getClass()) { 
            return false; 
        } 
        
        NewPoint p = (NewPoint) o;
        return p.x == x && p.y == y;
    }
	
	/* TESTS
	public static void main(String[] args) {
	
	}
	*/
}
