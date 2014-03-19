import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;


public class MyForm extends JFrame {

	private static final long serialVersionUID = 676355654668895961L;

	public static void main(String[] args) {
		MyForm form = new MyForm();
		form.setVisible(true);
	}
	
	public MyForm() {
		getContentPane().add(new MyComponent());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");
		getRootPane().getActionMap().put("cancel", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}});
		
		setPreferredSize(new Dimension(400, 200));
		pack();
	}
}
