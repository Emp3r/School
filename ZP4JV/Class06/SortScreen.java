package Class06;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;

public class SortScreen extends JComponent {

	private static final long serialVersionUID = 2219955967671808189L;

	private int length;
	private int[] points;
	private int pointSize = 5;
	private static final Color DARK_GREEN = new Color(8, 104, 64);
	
	public SortScreen(int length) {
		this.length = length;
	}

	public void setArray(int[] array) {
		this.points = array;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(DARK_GREEN);
		
		for (int i = 0; i < points.length; i++)
			g2.fillOval(i * this.getWidth() / length, points[i] * this.getHeight() / length, pointSize, pointSize);
	}
}
