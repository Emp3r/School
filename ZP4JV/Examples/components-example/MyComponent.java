import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;


public class MyComponent extends JComponent {
	
	private static final long serialVersionUID = -5810239256305954192L;
	
	private LinkedList<Point> points = new LinkedList<Point>();
	private int pointSize = 10;
	private Point currentPoint = null;
	
	public MyComponent() {
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				currentPoint = null;
				repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				currentPoint = new Point(e.getX(), e.getY());
				repaint();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				points.add(new Point(e.getX(), e.getY()));
				repaint();
			}
		});
		
		this.addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				currentPoint = new Point(e.getX(), e.getY());
				repaint();
			}
		});
		
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "deletePoint");
		getActionMap().put("deletePoint", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (points.size() > 0) points.removeLast();
				repaint();
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// kriz
		g2.setColor(Color.GRAY);
		g2.drawLine(0, 0, this.getWidth() - 1, this.getHeight() - 1);
		g2.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, 0);
		
		g2.setColor(Color.BLUE);
		for (Point p : points)
			g2.fillOval(p.x - pointSize / 2, p.y - pointSize / 2, pointSize, pointSize);
				
		if (currentPoint != null) {
			g2.setColor(Color.RED);
			g2.fillOval(currentPoint.x - pointSize / 2, currentPoint.y - pointSize / 2, pointSize, pointSize);
		}
		
		g2.setColor(Color.BLACK);
		g2.drawString("Bodu: " + points.size(), 0, this.getHeight());
	}

	public int getPointSize() {
		return pointSize;
	}

	public void setPointSize(int pointSize) {
		this.pointSize = pointSize;
	}
}
