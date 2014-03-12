package Class03;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class DialogShow  extends JDialog {

	private static final long serialVersionUID = 75421423L;

	private JPanel buttonPanel = new JPanel();
	private JButton buttonOk;
	private JButton buttonDel;
	private JPanel mainPanel;
	
	Person result;
	
	private JList<Activity> activityList;
	private DefaultListModel<Activity> listModel;

	public DialogShow(JFrame parentFrame, Person p) {
		super(parentFrame);
		setTitle("Person info");
		mainPanel = new JPanel();
		this.result = p;
		
		
		listModel = new DefaultListModel<Activity>();
		for (Activity a : p.getActivities()) {
			listModel.addElement(a);
		}
		activityList = new JList<Activity>(listModel);
		
		
		buttonOk = new JButton("OK");
		buttonOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		

		buttonDel = new JButton("Delete");
		buttonDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (activityList.getSelectedIndex() != -1) {
					Activity act = listModel.get(activityList.getSelectedIndex());
					java.util.List<Activity> activs = result.getActivities();
					
					activs.remove(act);
					result.setActivities(activs);
					listModel.remove(activityList.getSelectedIndex());
				}
			}
		});

		GridLayout btnLayout = new GridLayout(1, 2);

		buttonPanel.setLayout(btnLayout);
		buttonPanel.add(buttonOk);
		buttonPanel.add(buttonDel);
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(activityList, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(400, 400));
		this.pack();
	}

}
