package Class09;

import java.awt.Color;
import java.awt.Rectangle;

public class Button {
	private int x;
	private int y;
	private String text;
	private Rectangle r;
	private Color colorString;
	private Color colorButton;
	
	public Button(Rectangle r, String s, Color colS, Color colB) {
		this.r = r;
		this.x = r.width / 2 + 3;
		this.y = r.height;
		this.text = s;
		this.colorString = colS;
		this.colorButton = colB;
	}

	public String getText() {
		return text;
	}
	public char getChar() {
		return text.toCharArray()[0];
	}

	public Rectangle getR() {
		return r;
	}

	public void setR(Rectangle r) {
		x = r.x + (r.width/2);
		y = r.y+(r.height/2);
		this.r = r;
	}

	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void addComponentListener(JDial jd) { 
		System.out.println("secret 1");
	}

	public Color getColS() {
		return colorString;
	}
	public Color getColB() {
		return colorButton;
	}

}
