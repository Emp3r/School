package cz.upol.swing1;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Form1 extends JFrame {

	private static final long serialVersionUID = -3489120285099256952L;
	
	private JPanel mainPanel;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JButton btn4;

	public Form1() {
		super();
		this.setTitle("Form #1");
		
		// po uzavreni okna, budou prostredky s nim spojene odstraneny
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// vytvori panel do ktereho budou vkladany dalsi komponenty
		mainPanel = new JPanel();
		
		// nastavi zpusob rozlozeni komponent v tomto panelu,
		// pripadne dalsi vlastnosti tohoto rozlozeni
		
		//mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		// vytvori ctyri komponenty predstavujici tlacitka
		btn1 = new JButton("Foo");
		btn2 = new JButton("Bar");
		btn3 = new JButton("Baz");
		btn4 = new JButton("Qux");
		
		// vlozi tlacitka do panelu
		mainPanel.add(btn1);
		mainPanel.add(btn2);
		mainPanel.add(btn3);
		mainPanel.add(btn4);
		
		this.setContentPane(mainPanel);
		
		// muzeme nastavit preferovanou/maximalni/minimalni velikost okna
		//this.setPreferredSize(new Dimension(400, 500));
		
		// uzpusobi velikost okna vlozenym komponentam
		this.pack();
	}

}
