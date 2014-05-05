package Class05;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.*;


public class Form extends JFrame {

	private static final long serialVersionUID = 676355654668895961L;
	
	JDial jd;
	
	public Form() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Calculus maximus");

		jd = new JDial();
		getContentPane().add(jd);
		
		for (String i : Arrays.asList("7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0"))
			jd.addButton(i);
		jd.addButton("Clear");
		jd.addButton("Enter");
		jd.addButton("+");
		
		jd.addDisplay("");

		
		
		jd.addActionListner(makeAL("what what"));
		jd.addActionListner(makeAL("fsafasdfa\n"));
		
		
		setPreferredSize(new Dimension(400, 300));
		this.setMinimumSize(new Dimension(200, 150));
		this.setMaximumSize(new Dimension(800, 600));
		pack();
	}
	
	public ActionListener makeAL(final String s) {
		return (new AbstractAction(){
			private static final long serialVersionUID = -8248364348536458609L;
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(s);
			}
		});	
	}
	
}
