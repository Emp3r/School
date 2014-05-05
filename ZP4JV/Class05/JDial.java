package Class05;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class JDial extends JComponent {
	private static final long serialVersionUID = 1446151431301171608L;
	 
	List<Button> buttons;
	List<ActionListener> actions; 
	Display dspl;
	int firstOp, secondOp;
	String operation;

	public JDial() {
		buttons = new ArrayList<>();
		actions = new ArrayList<>();
		operation = null;
		
		
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) { }
			@Override
			public void mousePressed(MouseEvent e) { }
			@Override
			public void mouseExited(MouseEvent e) { }
			@Override
			public void mouseEntered(MouseEvent e) { }
			
			@Override
			public void mouseClicked(MouseEvent e) {
				for(Button b : buttons){		
					if (b.getR().contains(e.getX(), e.getY())) {
						
						if ((b.getChar() - '0') <= 9 && (b.getChar() - '0') >= 0) {
							dspl.addText(b.getText());		
						}
						else {
							switch (b.getText()) {
							case "Clear":
								clearAction.actionPerformed(null);
								break;
							case "Enter":
								enterAction.actionPerformed(null);
								break;
							case "+":
								addAction.actionPerformed(null);
								break;
							case "-":
								subAction.actionPerformed(null);
								break;
							case "*":
								mulAction.actionPerformed(null);
								break;
							case "/":
								divAction.actionPerformed(null);
								break;
							}
						}
					}
				}
				repaint();
			}
		});
		

		// Enter
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
		getActionMap().put("enter", enterAction);
		// Clear
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "clear");
		getActionMap().put("clear", clearAction);
		// +
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_1, KeyEvent.META_DOWN_MASK), "add");
		getActionMap().put("add", addAction);
		// -
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_2, KeyEvent.META_DOWN_MASK), "sub");
		getActionMap().put("sub", subAction);
		// *
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_3, KeyEvent.META_DOWN_MASK), "mul");
		getActionMap().put("mul", mulAction);
		// /
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_4, KeyEvent.META_DOWN_MASK), "div");
		getActionMap().put("div", divAction);
		

		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_1, KeyEvent.SHIFT_DOWN_MASK), "1");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), "1");
		getActionMap().put("1", new AbstractAction() {
			private static final long serialVersionUID = -1204205011300267323L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dspl.addText("1");
				repaint();
			}
		});

		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_2, KeyEvent.SHIFT_DOWN_MASK), "2");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), "2");
		getActionMap().put("2", new AbstractAction() {
			private static final long serialVersionUID = 6872146717658917302L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dspl.addText("2");
				repaint();
			}
		});
		
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0 | KeyEvent.SHIFT_DOWN_MASK), "3");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), "3");
		getActionMap().put("3", new AbstractAction() {
			private static final long serialVersionUID = 6810910752637964393L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dspl.addText("3");
				repaint();
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0 | KeyEvent.SHIFT_DOWN_MASK), "4");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0), "4");
		getActionMap().put("4", new AbstractAction() {
			private static final long serialVersionUID = -8810773339900270290L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dspl.addText("4");
				repaint();
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_5, 0 | KeyEvent.SHIFT_DOWN_MASK), "5");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_5, 0), "5");
		getActionMap().put("5", new AbstractAction() {
			private static final long serialVersionUID = -8051962857273308273L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dspl.addText("5");
				repaint();
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_6, 0 | KeyEvent.SHIFT_DOWN_MASK), "6");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_6, 0), "6");
		getActionMap().put("6", new AbstractAction() {
			private static final long serialVersionUID = -2596501932745193096L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dspl.addText("6");
				repaint();
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_7, 0 | KeyEvent.SHIFT_DOWN_MASK), "7");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_7, 0), "7");
		getActionMap().put("7", new AbstractAction() {
			private static final long serialVersionUID = 1123987654234567890L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dspl.addText("7");
				repaint();
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_8, 0 | KeyEvent.SHIFT_DOWN_MASK), "8");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_8, 0), "8");
		getActionMap().put("8", new AbstractAction() {
			private static final long serialVersionUID = -1825223021576746292L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dspl.addText("8");
				repaint();
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_9, 0 | KeyEvent.SHIFT_DOWN_MASK), "9");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_9, 0), "9");
		getActionMap().put("9", new AbstractAction() {
			private static final long serialVersionUID = -7751947636325644268L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dspl.addText("9");
				repaint();
			}
		});
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_0, 0 | KeyEvent.SHIFT_DOWN_MASK), "0");
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_0, 0), "0");
		getActionMap().put("0", new AbstractAction() {
			private static final long serialVersionUID = -2945427221353139980L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dspl.addText("0");
				repaint();
			}
		});
		
	}

	// +
	AbstractAction addAction = new AbstractAction() {
		private static final long serialVersionUID = -4938310807695213653L;
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				firstOp = Integer.parseInt(dspl.getText());
			} catch (Exception ex) {
				firstOp = 0;
			}
			operation = "+";
			dspl.setText("");
			repaint();
		}
	};
	// -
	AbstractAction subAction = new AbstractAction() {
		private static final long serialVersionUID = -4938310807695213653L;
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				firstOp = Integer.parseInt(dspl.getText());
			} catch (Exception ex) {
				firstOp = 0;
			}
			operation = "-";
			dspl.setText("");
			repaint();
		}
	};
	// *
	AbstractAction mulAction = new AbstractAction() {
		private static final long serialVersionUID = -4938310807695213653L;
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				firstOp = Integer.parseInt(dspl.getText());
			} catch (Exception ex) {
				firstOp = 0;
			}
			operation = "*";
			dspl.setText("");
			repaint();
		}
	};
	// /
	AbstractAction divAction = new AbstractAction() {
		private static final long serialVersionUID = -4938310807695213653L;
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				firstOp = Integer.parseInt(dspl.getText());
			} catch (Exception ex) {
				firstOp = 0;
			}
			operation = "/";
			dspl.setText("");
			repaint();
		}
	};

	// Clear
	AbstractAction clearAction = new AbstractAction() {
		private static final long serialVersionUID = -4938310807695213653L;
		@Override
		public void actionPerformed(ActionEvent e) {
			dspl.setText("");
			operation = null;
			firstOp = 0;
			secondOp = 0;
			repaint();
		}
	};

	// Enter
	AbstractAction enterAction = new AbstractAction() {
		private static final long serialVersionUID = -5379679594036681849L;
		@Override
		public void actionPerformed(ActionEvent e) {
			for(ActionListener d : actions)
				d.actionPerformed(e);
			try {
				secondOp = Integer.parseInt(dspl.getText());
			} catch (Exception ex) {
				secondOp = 0;
			}
			
			switch (operation) {
			case "+":
				dspl.setText(Integer.toString(firstOp + secondOp));
				break;
			case "-":
				dspl.setText(Integer.toString(firstOp - secondOp));
				break;
			case "*":
				dspl.setText(Integer.toString(firstOp * secondOp));
				break;
			case "/":
				dspl.setText(Integer.toString(firstOp / secondOp));
				break;
			}
			operation = null;
			repaint();
		}
	};
	
	
	public void addActionListner(ActionListener a) {
		actions.add(a);
	}
	
	public void addDisplay(String s) {
		dspl = new Display(s, Color.black, Color.lightGray);
	}
	
	public void addStringDisplay(String s){
		dspl.addText(s);
	}
	
	public void addButton(String s){
		Button b;
		if (s.equals("Clear") || s.equals("Enter")) {
			b = new Button(new Rectangle(0, 0, 0, 0), s, Color.white, Color.black);	
		}
		else if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
			b = new Button(new Rectangle(0, 0, 0, 0), s, Color.black, Color.gray);	
		}
		else {
			b = new Button(new Rectangle(0, 0, 0, 0), s, Color.black, Color.white);	
		}
		buttons.add(b);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setBackground(Color.black);

		int fontWidth = g.getFontMetrics().getWidths()['0'];
		int tmp = 0;
		for (int i = 0; i < dspl.getText().length(); i++)
			tmp += fontWidth;

		dspl.setX((int) (this.getWidth() - tmp));
		dspl.setY(getHeight() / 4 - 4);
		dspl.setRec(new Rectangle(0, 0, getWidth(), getHeight() / 4));

		g2.setColor(dspl.getColorDisplay());
		g2.fillRect(dspl.getR().x, dspl.getR().y, dspl.getR().width, dspl.getR().height);
		g2.setColor(dspl.getColorString());
		g2.drawString(dspl.getText(), dspl.getX() - 4, dspl.getY() - 1);
		
		if (firstOp != 0 && operation != null) {
			g2.drawString(Integer.toString(firstOp), this.getWidth() - (String.valueOf(firstOp).length() * fontWidth) - 4, dspl.getY() - 30);
			g2.drawString(operation, this.getWidth() - fontWidth - 4, dspl.getY() - 15);
		}
		
		for (int i = 0; i < buttons.size(); i++) {

			Button btn = buttons.get(i);
			initButton(btn, i);
			
			g2.setColor(btn.getColB());
			g2.fillRect(btn.getR().x, btn.getR().y, btn.getR().width, btn.getR().height);
			g2.setColor(Color.black);
			g2.drawRect(dspl.getR().x, dspl.getR().y, dspl.getR().width - 1, dspl.getR().height - 2);
			
			
			g2.setColor(btn.getColS());
			char odsazeni = 0;
			if (btn.getText().equals("Clear") || btn.getText().equals("Enter"))
				odsazeni = 15;
			g2.drawString(btn.getText(), btn.getX() - odsazeni, btn.getY() + 4);
			
		}
	}

	public void initButton(Button b, int i){
		int h = getHeight();
		int w = getWidth();
		int tmp2;
		int tmp = (int)buttons.size()/ 4;
		
		if (buttons.size() != 0)
			w = getWidth() / 4;
		if (tmp != 0)
			h = (getHeight() - (getHeight() / 16)) / (tmp+1);
		
		tmp = i/4;
		tmp2 = i % 4;
			
		b.setR(new Rectangle(tmp2*w, getHeight()/4+(tmp*h), w-2, h-2));
	}
}
