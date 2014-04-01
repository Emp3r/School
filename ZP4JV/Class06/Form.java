package Class06;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Form extends JFrame {

	private static final long serialVersionUID = -1351722896202706088L;

	public static final int ARRAY_SIZE = 100;
	public static final int SLEEP_TIME = 100;

	private JButton btnShuffle;
	private JButton btnPause;
	private JButton btnResume;
	private JPanel buttonPanel;
	private SortScreen screen;
	private ScreenThread screenT;

	//// >> FORM << ////
	public Form() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Selection Sort");

		screen = new SortScreen(ARRAY_SIZE);
		screenT = new ScreenThread();
		screen.setPreferredSize(new Dimension(400, 335));

		initializeButtons();
		btnShuffle.setPreferredSize(new Dimension(133, 40));

		//this.setResizable(false);
		//this.setLocation(200, 120);
		this.setLayout(new BorderLayout());
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.add(screen, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(400, 400));
		pack();
	}

	// BUTTONS
	private void initializeButtons() {
		GridLayout btnLayout = new GridLayout(1, 3);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(btnLayout);

		String start = "<html><center><font color=\"#20996D\" size=\"4\"><b>";
		String end = "</b></font></center></html>";

		btnShuffle = new JButton(start + "SHUFFLE" + end);
		btnShuffle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnPause.setEnabled(false);
				btnResume.setEnabled(true);
				screenT = new ScreenThread();
				screen.repaint();
			}
		});

		btnPause = new JButton(start + "PAUSE" + end);
		btnPause.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				btnPause.setEnabled(false);
				btnResume.setEnabled(true);
				screenT.suspend();
			}
		});
		btnPause.setEnabled(false);

		btnResume = new JButton(start + "RESUME" + end);
		btnResume.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				btnPause.setEnabled(true);
				btnResume.setEnabled(false);
				if (screenT.isAlive())
					screenT.resume();
				else {
					screenT.start();
				}
			}
		});

		buttonPanel.add(btnShuffle);
		buttonPanel.add(btnPause);
		buttonPanel.add(btnResume);
	}

	// THREAD
	private class ScreenThread extends Thread {

		private int[] points;

		public ScreenThread() {
			super();
			points = new int[ARRAY_SIZE];
			for (int i = 0; i < points.length; i++)
				points[i] = i;

			shuffleArray();
			screen.setArray(points);
		}

		@Override
		public void run() {
			selectSort();
		}

		public void shuffleArray() {
			Random rnd = new Random();
			for (int i = points.length - 1; i > 0; i--) {
				int index = rnd.nextInt(i + 1);
				int a = points[index];
				points[index] = points[i];
				points[i] = a;
			}
		}

		public void selectSort() {
			for (int i = 0; i < points.length - 1; i++) {
				int maxIndex = i;
				for (int j = i + 1; j < points.length; j++)
					if (points[j] > points[maxIndex])
						maxIndex = j;
				int temp = points[i];
				points[i] = points[maxIndex];
				points[maxIndex] = temp;
				
				screen.repaint();
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
