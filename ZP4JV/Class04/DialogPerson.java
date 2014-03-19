package Class04;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class DialogPerson extends JDialog {
	
	private static final long serialVersionUID = 64341021226831L;
	
	private JPanel buttonPanel = new JPanel();
	private JPanel inputPanel = new JPanel();
	private JButton btnOk;
	private JButton btnCancel;
	
	private JTextField txtFirst;
	private JTextField txtSecond;
	private JLabel lbFirst;
	private JLabel lbSecond;
	
	private Person result;
	
	public DialogPerson(JFrame parentFrame, Person p) {
		super(parentFrame);
		setTitle("Person");
		
		lbFirst = new JLabel("Name");
		lbSecond = new JLabel("Occupation");
		txtFirst = new JTextField();
		txtFirst.setColumns(20);
		txtSecond = new JTextField();
		txtSecond.setColumns(20);
		
		if (p != null) {
			result = p;
			txtFirst.setText(p.getName());
			txtSecond.setText(p.getOccupation());
		} else {
			result = null;
		}
		
		GroupLayout layout = new GroupLayout(inputPanel);
		inputPanel.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
									.addComponent(lbFirst)
									.addComponent(lbSecond))
						.addGroup(layout.createParallelGroup()
									.addComponent(txtFirst)
									.addComponent(txtSecond, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, 200))); // nastavuje navic vlastnosti komponente 
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
											.addComponent(lbFirst)
											.addComponent(txtFirst, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createParallelGroup()
											.addComponent(lbSecond)
											.addComponent(txtSecond, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
		
		inputPanel.add(lbFirst);
		inputPanel.add(txtFirst);
		
		
		GridLayout btnLayout = new GridLayout(1, 2);
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (result != null) {
					result.setName(txtFirst.getText());
					result.setOccupation(txtSecond.getText());
				}
				else {
					if (!txtFirst.getText().equals(""))
						result = new Person(txtFirst.getText(), txtSecond.getText());
				}
				setVisible(false);
			}
		});
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		buttonPanel.setLayout(btnLayout);
		buttonPanel.add(btnOk);
		buttonPanel.add(btnCancel);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(inputPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	  
		pack();
	}

	public Person getResult() {
		return result;
	}
}
