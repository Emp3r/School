package cz.upol.swing1;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;


public class Form4 extends JFrame {

	private static final long serialVersionUID = -3489120285099256952L;
	
	private JMenuBar mainMenu;
	private JMenu menuFile;
	private JList mainList;
	private DefaultListModel listModel;

	private List<String> items = new ArrayList<String>();

	public Form4()  {
		super();
		
		items.add("Foo");
		items.add("Bar");
		
		this.setTitle("Form #4");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		mainMenu = new JMenuBar();
		
		menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem menuItemFoo = new JMenuItem("Add...");
		menuItemFoo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		menuItemFoo.addActionListener(new AddAction(this));
		menuFile.add(menuItemFoo);
		
		mainMenu.add(menuFile);
		this.setJMenuBar(mainMenu);
		
		// vytvori seznam s pevne danym listem polozek
		// mainList = new JList(new String [] {"Foo", "Bar" });
		
		// vytvori seznam, ktery je zpracovava data z nejakeho konkretniho datoveho modelu
		// nejdriv vytvori model, pak do nej vlozi prvek a priradi jej seznamu
		listModel = new DefaultListModel();
		listModel.addElement("Foo");
		mainList = new JList(listModel);
		
		this.setLayout(new BorderLayout());
		this.add(mainList, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(400, 400));
		
		this.pack();
	}
	/**
	 * Udalost, ktera zobrazi formular
	 */
	private class AddAction implements ActionListener {

		private JFrame parentFrame;
		public AddAction(JFrame frame) {
			this.parentFrame = frame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Dialog1 dlg = new Dialog1(parentFrame); // vytvori dialog a udava, ktery formular je nadrazeny
			dlg.setModal(true);						// modalita !!!
			dlg.setVisible(true);					// ceka dokud nebude uzavren formular 
			if (dlg.getResult() != null)			// precte hodnotu
				listModel.addElement(dlg.getResult());
			
			dlg.dispose();
		}
		
	}
}
