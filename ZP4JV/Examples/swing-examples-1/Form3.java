package cz.upol.swing1;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;


public class Form3 extends JFrame {

	private static final long serialVersionUID = -3489120285099256952L;
	
	private JMenuBar mainMenu;
	private JMenu menuFile;
	private JMenu menuEdit;

	public Form3()  {
		super();
		this.setTitle("Form #3");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// vytvori objekt predstavujici hlavni radek v menu
		mainMenu = new JMenuBar();
		
		// vytvori objekt menu: ,,File'' (pristupny pres Alt+F)
		menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		
		// vytvori polozku v menu a navaze na ni udalost
		JMenuItem menuItemFoo = new JMenuItem("Foo");
		menuItemFoo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Fooooo!");	
			}
		});
		
		// pripoji polozku menu do menu 
		menuFile.add(menuItemFoo);
		
		// vytvori dalsi polozku v menu
		JMenuItem menuItemExit = new JMenuItem("Exit");
		
		// nastavi klavesovou zkratku
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		menuItemExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("So long!");
				// ukonceni prace s oknem
				setVisible(false);
				dispose();
			}
		});
		menuFile.add(menuItemExit);
		
		
		menuEdit = new JMenu("Edit");
		menuEdit.setMnemonic(KeyEvent.VK_E);
		
		mainMenu.add(menuFile);
		mainMenu.add(menuEdit);
		
		// nastavi formulari menu
		this.setJMenuBar(mainMenu);
		this.setPreferredSize(new Dimension(400, 400));
		
		this.pack();
	}

}
