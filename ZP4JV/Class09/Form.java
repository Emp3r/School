package Class09;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.JFrame;


public class Form extends JFrame {

	private static final long serialVersionUID = 630018066L;
	private static Logger logger = Logger.getLogger("Form");
	
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
		
		jd.setEnter(enter);
		jd.setClear(clear);
		
		setPreferredSize(new Dimension(400, 300));
		this.setMinimumSize(new Dimension(200, 150));
		this.setMaximumSize(new Dimension(800, 600));
		pack();
	}
	
	AbstractAction enter = new AbstractAction(){
			private static final long serialVersionUID = 110;
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Enter pressed.\n" + Integer.toString(jd.getFirstOp()) + " " + jd.getOperation() + " " + Integer.toString(jd.getSecondOp()) + " = " + jd.getDisplay().getText());
			}
		};	
	AbstractAction clear = new AbstractAction(){
			private static final long serialVersionUID = -110;
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Clear pressed.\nBoth operands setted to 0 and operation to null.");
			}
		};	
}
