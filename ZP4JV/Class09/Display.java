package Class09;

import java.awt.Color;
import java.awt.Rectangle;

public class Display {
	private int x;
	private int y;
	private String text;
	private Rectangle r;
	private Color colorString;
	private Color colorDisplay;

	public Display(Color cs, Color cR){
		this.text = "";
		colorString = cs;
		colorDisplay= cR;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String s) {
		this.text = s;
	}

	public void appendText(String s) {
		text += s;
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
