package form;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import logic.Board;

public class JGame extends JComponent {

	private static final long serialVersionUID = 9194462959615679687L;

	private static final int SIZE = 28;
	private static final int GAP = 2;

	private int width;
	private int height;
	private int difficulty;
	private Board board;
	private int clearFields;
	private int cleared;
	private boolean win;
	private boolean loose;
	private Color lightGray = new Color (235, 235, 235);
	private Color darkGray = new Color (100, 100, 100);
	private Color darkGreen = new Color (60, 220, 60);
	private Color darkCyan = new Color (0, 204, 255);
	private Form form;
	private Timer timer;
	private int minutes = 0;
	private int seconds = 0;

	public JGame(int w, int h, int dif, Form form) {
		this.width = w;
		this.height = h;
		this.difficulty = dif;
		this.board = new Board(width, height, difficulty);
		this.clearFields = board.getAllClearFields();
		this.cleared = 0;
		this.win = false;
		this.loose = false;
		this.form = form;
		
		this.addMouseListener(new MouseListener() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				mouseClick(e);
				repaint();
			} 
			@Override
			public void mousePressed(MouseEvent e) { }
			@Override
			public void mouseExited(MouseEvent e) { }
			@Override
			public void mouseEntered(MouseEvent e) { }
			@Override
			public void mouseClicked(MouseEvent e) { }
		});
		
		timer = new Timer(1000, timerAction);
		timer.start();
	}
			
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setBackground(Color.black);
		g2.setFont(new Font("Jokerman", Font.PLAIN, 18));

		// platno na pozadi
		g.setColor(Color.black);
		g.fillRect(0, 0, this.width * (SIZE + GAP) + 2, (this.height + 1) * (SIZE + GAP) + 2);

		// dolni button (smajlik)
		g.setColor(Color.gray);
		g.fillRect((this.width / 2 - 1) * (SIZE + GAP) + 2, (this.height) * (SIZE + GAP) + 4 , (SIZE + GAP + SIZE), SIZE - 4);
		g.setColor(Color.black);
		if (win) {
			g.fillRect((this.width / 2 - 1) * (SIZE + GAP) + 14, (this.height) * (SIZE + GAP) + 8 , 4, 4);
			g.fillRect((this.width / 2 - 1) * (SIZE + GAP) + 44, (this.height) * (SIZE + GAP) + 8 , 4, 4);
			g.fillRect((this.width / 2 - 1) * (SIZE + GAP) + 6, (this.height) * (SIZE + GAP) + 20, (SIZE + SIZE - 6), 4);
			g.fillRect((this.width / 2 - 1) * (SIZE + GAP) + 6, (this.height) * (SIZE + GAP) + 15, 4, 5);
			g.fillRect((this.width / 2 - 1) * (SIZE + GAP) + 52, (this.height) * (SIZE + GAP) + 15, 4, 5);	
		} else if (loose) {
			g.fillRect((this.width / 2 - 1) * (SIZE + GAP) + 14, (this.height) * (SIZE + GAP) + 8 , 4, 4);
			g.fillRect((this.width / 2 - 1) * (SIZE + GAP) + 44, (this.height) * (SIZE + GAP) + 8 , 4, 4);
			g.fillRect((this.width / 2 - 1) * (SIZE + GAP) + 6, (this.height) * (SIZE + GAP) + 16, (SIZE + SIZE - 6), 4);
			g.fillRect((this.width / 2 - 1) * (SIZE + GAP) + 6, (this.height) * (SIZE + GAP) + 20, 4, 5);
			g.fillRect((this.width / 2 - 1) * (SIZE + GAP) + 52, (this.height) * (SIZE + GAP) + 20, 4, 5);	
		} else {
			g.fillRect((this.width / 2 - 1) * (SIZE + GAP) + 14, (this.height) * (SIZE + GAP) + 8 , 4, 4);
			g.fillRect((this.width / 2 - 1) * (SIZE + GAP) + 44, (this.height) * (SIZE + GAP) + 8 , 4, 4);
			g.fillRect((this.width / 2 - 1) * (SIZE + GAP) + 6, (this.height) * (SIZE + GAP) + 18 , (SIZE + SIZE - 6), 4);
		}
		
		// timer
		g.setColor(Color.gray);
		g.fillRect((this.width - 3) * (SIZE + GAP) + 2, (this.height) * (SIZE + GAP) + 4, (SIZE + GAP + SIZE), SIZE - 4);
		g.setColor(Color.black);
		int xx = (minutes >= 10) ? (this.width - 3) * (SIZE + GAP) + 6 : (this.width - 3) * (SIZE + GAP) + 17;
		g.drawString(minutes + ":" + seconds, xx, (this.height) * (SIZE + GAP) + 23);
		

		// hraci deska (policka)
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) { 
				
				if (board.getFields()[x][y].getClicked()) {
					String letter = board.getFields()[x][y].toString();
					
					if (letter.equals("X")) { 
						// mina
						g.setColor(Color.red);
						g.fillRect(x * (SIZE + GAP) + 2, y * (SIZE + GAP) + 2, SIZE, SIZE);
						
						g.setColor(Color.black);
						g.fillOval(x * (SIZE + GAP) + 9, y * (SIZE + GAP) + 9, 15, 15);
						g.fillOval(x * (SIZE + GAP) + 9, y * (SIZE + GAP) + 8, 15, 15);
						g.fillOval(x * (SIZE + GAP) + 8, y * (SIZE + GAP) + 9, 15, 15);
						g.fillOval(x * (SIZE + GAP) + 8, y * (SIZE + GAP) + 8, 15, 15);

						g.drawLine(x * (SIZE + GAP) + 15, y * (SIZE + GAP) + 5, x * (SIZE + GAP) + 15, y * (SIZE + GAP) + 26);
						g.drawLine(x * (SIZE + GAP) + 16, y * (SIZE + GAP) + 5, x * (SIZE + GAP) + 16, y * (SIZE + GAP) + 26);
						g.drawLine(x * (SIZE + GAP) + 5, y * (SIZE + GAP) + 15, x * (SIZE + GAP) + 26, y * (SIZE + GAP) + 15);
						g.drawLine(x * (SIZE + GAP) + 5, y * (SIZE + GAP) + 16, x * (SIZE + GAP) + 26, y * (SIZE + GAP) + 16); 
					}
					else if (!letter.equals(" ")) {
						// cislo
						g.setColor(lightGray);
						g.fillRect(x * (SIZE + GAP) + 2, y * (SIZE + GAP) + 2, SIZE, SIZE);
						
						switch (letter) {
						case "1": g.setColor(Color.blue); break;
						case "2": g.setColor(darkGreen); break;
						case "3": g.setColor(Color.red); break;
						case "4": g.setColor(darkCyan); break;
						case "5": g.setColor(Color.magenta); break;
						case "6": g.setColor(Color.orange); break;
						case "7": g.setColor(Color.pink); break;
						case "8": g.setColor(Color.black); break;
						}
						g.drawString(letter, x * (SIZE + GAP) + 11, y * (SIZE + GAP) + 23);
					}
					else {
						// prazdno (0 min kolem policka)
						g.setColor(Color.white);
						g.fillRect(x * (SIZE + GAP) + 2, y * (SIZE + GAP) + 2, SIZE, SIZE);
					}
				} 
				else if (board.getFields()[x][y].getFlag()) {
					// vlajka
					g.setColor(darkGray);
					g.fillRect(x * (SIZE + GAP) + 2, y * (SIZE + GAP) + 2, SIZE, SIZE); 
					
					g.setColor(Color.black);
					int[] x1 = {(x * (SIZE + GAP) + 16), (x * (SIZE + GAP) + 18), (x * (SIZE + GAP) + 18), (x * (SIZE + GAP) + 16)};
					int[] y1 = {(y * (SIZE + GAP) + 8), (y * (SIZE + GAP) + 8), (y * (SIZE + GAP) + 22), (y * (SIZE + GAP) + 22)};
					g.fillPolygon(x1, y1, 4); 
					g.setColor(Color.red);
					int[] x2 = {(x * (SIZE + GAP) + 10), (x * (SIZE + GAP) + 16), (x * (SIZE + GAP) + 16) };
					int[] y2 = {(y * (SIZE + GAP) + 12), (y * (SIZE + GAP) + 8), (y * (SIZE + GAP) + 16) };
					g.fillPolygon(x2, y2, 3);
				}
				else {
					// neodkliknute
					g.setColor(Color.gray);
					g.fillRect(x * (SIZE + GAP) + 2, y * (SIZE + GAP) + 2, SIZE, SIZE);
				}
			}
		}
	}

	public void mouseClick(MouseEvent e) {
		int px = (e.getX() - 3) / (SIZE + GAP);
		int py = (e.getY() - 3) / (SIZE + GAP);

		if (px < this.width && py < this.height && !win && !loose) {
			if (SwingUtilities.isLeftMouseButton(e)) 
				click(px, py);
			else
				flag(px, py);
		}

		if (px >= (this.width / 2 - 1) && px < (this.width / 2 + 1) && py == (this.height)) {
			newGame();
		}
	}
	
	private void resetTimer() {
		timer.stop();
		minutes = 0;
		seconds = 0;
		timer = new Timer(1000, timerAction);
		timer.start();
	}
	
	public void newGame() {
		this.board = new Board(width, height, difficulty);
		this.clearFields = board.getAllClearFields();
		this.cleared = 0;
		this.win = false;
		this.loose = false;
		
		resetTimer();
		this.repaint();
	}
	
	ActionListener timerAction = new ActionListener() { 
        public void actionPerformed(ActionEvent e) {
        	if (seconds == 59) {
        		if (minutes != 99) minutes++;
        		seconds = 0;
        	} else
        		seconds++;
        	repaint();
        }
     };
     
	public String getScore() {
		return minutes + ":" + seconds;
	}
	
	public int getMinutes() {
		return minutes;
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	public boolean getWin() {
		return win;
	}

	public boolean getLoose() {
		return loose;
	}

	public Board getBoard() {
		return board;
	}

	public int getClearFields() {
		return clearFields;
	}

	public int getCleared() {
		return cleared;
	}
	
	public void setDifficulty(int diff) {
		this.difficulty = diff;
	}

	public void flag(int x, int y) {
		board.flag(x, y);
		this.repaint();
	}

	public void click(int x, int y) {
		try {
			if (!board.getFields()[x][y].getClicked()) {
				int c = board.click(x, y);

				if (c == -2) {
					// pole je ovlajkované
					return;
				} else if (c == -1) {
					// pole je mina
					loose = true;
					timer.stop();
					return;
				}
				cleared++;

				// pokud je stejný počet odkliknutých polí s celkovým
				// počtem polí bez min, hráč vyhrál
				if (cleared == clearFields) {
					win = true;
					this.repaint();
					timer.stop();
					form.win(difficulty);
					return;
				}
				
				// pokud má pole 0 min kolem sebe, automaticky se
				// odklikají všechny pole kolem něho
				if (c == 0)
					autoClick(x, y);
			}
		} catch (Exception e) { }
		this.repaint();
	}

	private void autoClick(int x, int y) {
		if (x == 0) {
			if (y == 0) {
				click(x, y + 1); click(x + 1, y); click(x + 1, y + 1);
			} else if (y == width - 1) {
				click(x, y - 1); click(x + 1, y); click(x + 1, y - 1);
			} else {
				click(x, y + 1); click(x, y - 1); click(x + 1, y); click(x + 1, y + 1); click(x + 1, y - 1);
			}
		} else if (x == width - 1) {
			if (y == 0) {
				click(x, y + 1); click(x - 1, y); click(x - 1, y + 1);
			} else if (y == width - 1) {
				click(x, y - 1); click(x - 1, y); click(x - 1, y - 1);
			} else {
				click(x, y + 1); click(x, y - 1); click(x - 1, y); click(x - 1, y + 1); click(x - 1, y - 1);
			}
		} else if (y == 0) {
			click(x, y + 1); click(x + 1, y); click(x - 1, y); click(x + 1, y + 1); click(x - 1, y + 1);
		} else if (y == height - 1) {
			click(x, y - 1); click(x + 1, y); click(x - 1, y); click(x + 1, y - 1); click(x - 1, y - 1);
		} else {
			click(x, y + 1); click(x, y - 1); click(x + 1, y); click(x - 1, y);
			click(x + 1, y + 1); click(x + 1, y - 1); click(x - 1, y + 1); click(x - 1, y - 1);
		}
	}
}
