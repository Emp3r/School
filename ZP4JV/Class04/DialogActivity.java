package Class04;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogActivity extends JDialog {
	
	private static final long serialVersionUID = 641226831L;
	
	private JPanel buttonPanel = new JPanel();
	private JPanel inputPanel = new JPanel();
	private JButton btnOk;
	private JButton btnCancel;
	private JTextField txtFirst;
	private JTextField txtSecond;
	private JTextField txtThird;
	private JLabel lbFirst;
	private JLabel lbSecond;
	private JLabel lbThird;
	
	private Activity result;
	
	public DialogActivity(JFrame parentFrame) {
		super(parentFrame);
		setTitle("Activity");
		
		lbFirst = new JLabel("Date");
		lbSecond = new JLabel("Hours");
		lbThird = new JLabel("Activity");
		txtFirst = new JTextField();
		txtFirst.setColumns(20);
		txtSecond = new JTextField();
		txtSecond.setColumns(20);
		txtThird = new JTextField();
		txtThird.setColumns(20);
		
		GroupLayout layout = new GroupLayout(inputPanel);
		inputPanel.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);

		GroupLayout.ParallelGroup parallelGroup1 = layout.createParallelGroup();
		GroupLayout.ParallelGroup parallelGroup2 = layout.createParallelGroup();
		parallelGroup1.addComponent(lbFirst).addComponent(lbSecond).addComponent(lbThird);
		parallelGroup2.addComponent(txtFirst).addComponent(txtSecond).addComponent(txtThird);

		GroupLayout.SequentialGroup sequentialGroup1 = layout.createSequentialGroup();
		sequentialGroup1.addGroup(parallelGroup1);
		sequentialGroup1.addGroup(parallelGroup2);
		
		layout.setHorizontalGroup(sequentialGroup1);

		GroupLayout.ParallelGroup parallelGroup3 = layout.createParallelGroup();
		GroupLayout.ParallelGroup parallelGroup4 = layout.createParallelGroup();
		GroupLayout.ParallelGroup parallelGroup5 = layout.createParallelGroup();
		parallelGroup3.addComponent(lbFirst).addComponent(txtFirst);
		parallelGroup4.addComponent(lbSecond).addComponent(txtSecond);
		parallelGroup5.addComponent(lbThird).addComponent(txtThird);

		GroupLayout.SequentialGroup sequentialGroup2 = layout.createSequentialGroup();
		sequentialGroup2.addGroup(parallelGroup3);
		sequentialGroup2.addGroup(parallelGroup4);
		sequentialGroup2.addGroup(parallelGroup5);
		
		layout.setVerticalGroup(sequentialGroup2);
		
		inputPanel.add(txtFirst);
		inputPanel.add(txtSecond);
		inputPanel.add(txtThird);
		
		GridLayout btnLayout = new GridLayout(1, 2);
		
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtFirst.getText().equals("") && txtSecond.getText().equals(""))
					result = null;
				else
					result = new Activity(txtFirst.getText(), txtSecond.getText(), txtThird.getText());
				setVisible(false);
			}
		});
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				result = null;
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

	public Activity getResult() {
		return result;
	}
}
