package Class03;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DialogConfirm extends JDialog{
	
	private static final long serialVersionUID = 134644521L;
	
	private JPanel buttonPanel = new JPanel();
	private JButton btnOk;
	private JButton btnCancel;
	private JLabel labelText;
	
	private boolean result;

	public DialogConfirm(JFrame parentFrame) {
		super(parentFrame);
		setTitle("Delete");
		
		labelText = new JLabel("Are you sure?", SwingConstants.CENTER);
		
		GridLayout btnLayout = new GridLayout(1, 2);
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				result = true;
				setVisible(false);
			}
		});
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				result = false;
				setVisible(false);
			}
		});
		buttonPanel.setLayout(btnLayout);
		buttonPanel.add(btnOk);
		buttonPanel.add(btnCancel);
		
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(labelText, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		pack();
	}
	

	public boolean getResult() {
		return result;
	}
}
