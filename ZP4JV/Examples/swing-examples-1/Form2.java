package cz.upol.swing1;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Form2 extends JFrame {

	private static final long serialVersionUID = -3489120285099256952L;
	
	private JPanel mainPanel;
	private JLabel lbTop;
	private JLabel lbLeft;
	private JButton btnRight;
	private JTextArea mainTextArea;
	private JTextField txtBottom;

	public Form2()  {
		super();
		this.setTitle("Form #2");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		mainPanel = new JPanel();
		
		// rozmisteni komponent po stranach okna
		mainPanel.setLayout(new BorderLayout());
		
		lbLeft = new JLabel("Levy text");
		lbLeft.setOpaque(true); 			// nastavi, aby stitek mel pozadi
		lbLeft.setBackground(Color.CYAN);
		
		lbTop = new JLabel("Text v horni casti");
		lbTop.setOpaque(true);
		lbTop.setBackground(Color.RED);
		
		btnRight = new JButton("Tlacitko Foo");
		
		// viceradkove textove pole
		mainTextArea = new JTextArea("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis mollis, lectus vitae aliquet sodales, purus augue tempor urna, a sollicitudin sapien neque ac ipsum. In et fringilla magna. Pellentesque commodo arcu ut eros hendrerit pulvinar. Sed imperdiet lacinia pulvinar.");
		
		// jednoradkove textove pole
		txtBottom = new JTextField("Textove pole");
		
		// vlozi komponenty do panelu
		mainPanel.add(lbTop, BorderLayout.NORTH);
		mainPanel.add(lbLeft, BorderLayout.WEST);
		mainPanel.add(btnRight, BorderLayout.EAST);
		mainPanel.add(mainTextArea, BorderLayout.CENTER);
		mainPanel.add(txtBottom, BorderLayout.SOUTH);
		
		// navaze na tlacitko udalost
		btnRight.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button!");
				
				// uzitecna trida obsahujici radu preddefinovanych dialogu pro bezne situace
				// v tomto pripade se zobrazi textova zprava, ktera cte hodnotu z pole txtBottom
				JOptionPane.showMessageDialog(null, "Text: " + txtBottom.getText());
			}
		});
		
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(400, 400));
		
		this.pack();
	}

}
