package data;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class NewScoreDialog extends JDialog {
	
	private static final long serialVersionUID = 643421226831L;

	private JLabel lblGratz;
	private JLabel lblText;
	private JPanel labelPanel;
	private JTextField txtName;
	private JButton btnOk;
	private String name;
	
	public NewScoreDialog(JFrame parentFrame, String score) {
		super(parentFrame);
		setTitle("Score");
		
		GridLayout lblLayout = new GridLayout(2, 1);
		labelPanel = new JPanel();
		labelPanel.setLayout(lblLayout);

		lblGratz = new JLabel("Gratz, you won with time " + score, JLabel.CENTER);
		lblText = new JLabel("Type your name for highscore table", JLabel.CENTER);
		labelPanel.add(lblGratz);
		labelPanel.add(lblText);
		
		txtName = new JTextField();
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				name = txtName.getText();
				setVisible(false);
			}
		});

		this.setPreferredSize(new Dimension(240, 120));
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(labelPanel, BorderLayout.NORTH);
		getContentPane().add(txtName, BorderLayout.CENTER);
		getContentPane().add(btnOk, BorderLayout.SOUTH);
		pack();
	}
	
	public String getResult() {
		return name;
	}
}
