package form;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import data.*;

public class Form extends JFrame {

	private static final long serialVersionUID = 845222177524994234L;
	private static final int EASY = 13;
	private static final int MEDIUM = 8;
	private static final int HARD = 5;
	
	private JGame game;
	private ScoreManager sm;
	private JMenuBar mainMenu;
	private JMenu menuGame;
	private JMenu menuDifficulty;
	private JMenu menuScore;
	
	public Form() {
		this.setDefaultCloseOperation(Form.EXIT_ON_CLOSE);
		this.setTitle("MineSweeper");
		
		sm = new ScoreManager();
		game = new JGame(16, 8, MEDIUM, this);
		this.getContentPane().add(game);

		String opSys = System.getProperty("os.name");
		if (opSys.equals("Mac OS X")) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			this.setPreferredSize(new Dimension(482, 294));
		} else {
			this.setPreferredSize(new Dimension(482, 316));
		}
		initializeMenu();
		
		this.setJMenuBar(mainMenu);
		this.setLocation(300, 160);
		this.setResizable(false);
		pack();
	}
	
	// menu init
	private void initializeMenu() {
		mainMenu = new JMenuBar();
		menuGame = new JMenu("Game");
		menuDifficulty = new JMenu("Difficulty");
		menuScore = new JMenu("Highscores");

		// game
		JMenuItem menuItemNew = new JMenuItem("New game");
		menuItemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItemNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { game.newGame(); }
		});
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		menuItemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { setVisible(false); dispose(); System.exit(0); }
		});
		menuGame.add(menuItemNew);
		menuGame.add(menuItemExit);
		mainMenu.add(menuGame);
		
		// difficulty
		final JCheckBoxMenuItem menuItemEasy = new JCheckBoxMenuItem("Easy");
		final JCheckBoxMenuItem menuItemMedium = new JCheckBoxMenuItem("Medium");
		final JCheckBoxMenuItem menuItemHard = new JCheckBoxMenuItem("Hard");
		menuItemEasy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
		menuItemEasy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { game.setDifficulty(EASY); game.newGame(); 
				menuItemMedium.setSelected(false); menuItemHard.setSelected(false);}
		});
		menuItemMedium.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
		menuItemMedium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { game.setDifficulty(MEDIUM); game.newGame(); 
				menuItemEasy.setSelected(false); menuItemHard.setSelected(false);}
		});
		menuItemHard.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK));
		menuItemHard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { game.setDifficulty(HARD); game.newGame(); 
				menuItemEasy.setSelected(false); menuItemMedium.setSelected(false);}
		});
		menuItemMedium.setSelected(true);
		menuDifficulty.add(menuItemEasy);
		menuDifficulty.add(menuItemMedium);
		menuDifficulty.add(menuItemHard);
		mainMenu.add(menuDifficulty);
		
		// score
		JMenuItem menuItemScoreEasy = new JMenuItem("Easy");
		JMenuItem menuItemScoreMedium = new JMenuItem("Medium");
		JMenuItem menuItemScoreHard = new JMenuItem("Hard");
		JMenuItem menuItemScoreReset = new JMenuItem("Reset tables"); 
		menuItemScoreEasy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { scoreDialog(EASY); }
		});
		menuItemScoreMedium.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { scoreDialog(MEDIUM); }
		});
		menuItemScoreHard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { scoreDialog(HARD); }
		});
		menuItemScoreReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { 
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset all highscores?", "Really?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (reply == JOptionPane.YES_OPTION) sm.fillTable(); 
			}
		});
		menuScore.add(menuItemScoreEasy);
		menuScore.add(menuItemScoreMedium);
		menuScore.add(menuItemScoreHard);
		menuScore.add(menuItemScoreReset);
		mainMenu.add(menuScore); 
	}
	
	// win dialog
	public void win(int dif) {
		NewScoreDialog dialog = new NewScoreDialog(this, game.getScore());
		dialog.setModal(true);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
		
		if (dialog.getResult() != null && !dialog.getResult().equals("")) { 
			int score = (game.getMinutes() * 100) + game.getSeconds();
			sm.writeScore(score, dialog.getResult(), dif);
		}
	}
	
	// highscore tables
	public void scoreDialog(int dif) {
		ScoreTableDialog dialog = new ScoreTableDialog(this, sm, dif);
		dialog.setModal(true);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}
	
	// MAIN
	public static void main(String[] args) {

		Form form = new Form();
		form.setVisible(true);
	}
	// potencionalni dodelavky - "are you sure?" custom dialog okno, "about creator" dialog okno
}
