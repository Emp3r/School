package Class05;

import java.awt.Color;
import java.awt.Rectangle;

public class Display {
	private int x;
	private int y;
	private StringBuilder text;
	private Rectangle r;
	private Color colorString;
	private Color colorDisplay;

	public Display(String s, Color cs, Color cR){
		this.text = new StringBuilder(s);
		colorString = cs;
		colorDisplay= cR;
	}
	
	public String getText() {
		return text.toString();
	}

	public void setText(String s) {
		text.replace(0, text.length(), s);
	}

	public void addText(String s){
		this.text.append(s);
	}

	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Rectangle getR(){
		return r;
	}
	
	public void setRec(Rectangle r){
		this.r = r;
	}

	public Color getColorString() {
		return colorString;
	}

	public Color getColorDisplay() {
		return colorDisplay;
	}
}
