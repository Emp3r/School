

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class Example1 extends JFrame {
	
	public static void main(String[] args) throws Exception  {
		Example1 f = new Example1();
		f.setVisible(true);
	}

	private static String HTML_TEXT = "<html>Lorem ipsum dolor sit amet, consectetur adipiscing elit. <i>Nunc elit sem.</i><br><span style=\"color: yellow; font-weight: normal;\">Suspendisse potenti. Suspendisse eget venenatis nisl.</span></html>"; 
	
	private JPanel panel = new JPanel();
	private JEditorPane editor = new JEditorPane();
	
	public Example1() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(500, 300));
		
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel(HTML_TEXT), BorderLayout.NORTH);
		panel.add(new JButton(HTML_TEXT), BorderLayout.SOUTH);
		panel.add(new JScrollPane(editor), BorderLayout.CENTER);
		getContentPane().add(panel);
		
		editor.setContentType("text/html");
		editor.setText(HTML_TEXT);
		
		pack();
	}

}
