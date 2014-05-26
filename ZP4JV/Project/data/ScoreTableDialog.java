package data;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScoreTableDialog extends JDialog {
	
	private static final long serialVersionUID = 143421226811L;

	private JLabel lblTop;
	private JPanel labelPanel;
	private JButton btnOk;

	public ScoreTableDialog(JFrame parentFrame, ScoreManager sm, int difficulty) {
		super(parentFrame);
		setTitle("Score");
		
		
		// labels
		GridLayout lblLayout = new GridLayout(10, 1);
		labelPanel = new JPanel();
		labelPanel.setLayout(lblLayout);
		
		List<Record> list;
		if (difficulty == 13) {
			list = sm.getListEasy();
			lblTop = new JLabel("Easy", JLabel.CENTER);
		} else if (difficulty == 8) {
			list = sm.getListMedium();
			lblTop = new JLabel("Medium", JLabel.CENTER);
		} else {
			list = sm.getListHard();
			lblTop = new JLabel("Hard", JLabel.CENTER);
		}

		for (Record r : list) {
			JLabel lblRecord = new JLabel(r.toString(), JLabel.CENTER);
			labelPanel.add(lblRecord);
		}
		
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { setVisible(false); }
		});
		
		setPreferredSize(new Dimension(300, 300));
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(lblTop, BorderLayout.NORTH);
		getContentPane().add(labelPanel, BorderLayout.CENTER);
		getContentPane().add(btnOk, BorderLayout.SOUTH);
		pack();
	}
	
}
