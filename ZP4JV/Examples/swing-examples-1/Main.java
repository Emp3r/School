package cz.upol.swing1;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame form = null;
		int option = 4;
		switch (option) {
		case 1: form = new Form1(); break;
		case 2: form = new Form2(); break;
		case 3: form = new Form3(); break;
		case 4: form = new Form4(); break;
		}
		
		form.setVisible(true);
	}

}
