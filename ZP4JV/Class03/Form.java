package Class03;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Form extends JFrame {
	
	private static final long serialVersionUID = 19876L;
	
	private JPanel mainPanel;
	private JMenuBar mainMenu;
	private JMenu menuFile;
	private JMenu menuPerson;
	private JMenu menuActivity;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;
	private JPanel buttonPanel;
	
	private JList<Person> mainList;
	private DefaultListModel<Person> listModel;

	
	// FORM
	public Form() {
		super();
		this.setTitle("Zaměstnanci");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		final JFrame frame = this;
		mainPanel = new JPanel();
		
		// MENU
		mainMenu = new JMenuBar();

		// File
		menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		JMenuItem menuItemOpen = new JMenuItem("Open");
		menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		// open - load
		menuFile.add(menuItemOpen);
		JMenuItem menuItemSave = new JMenuItem("Save");
		menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		// save
		menuFile.add(menuItemSave);
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		menuItemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { setVisible(false); dispose(); }
		});
		menuFile.add(menuItemExit);
		mainMenu.add(menuFile);
		
		// Person
		menuPerson = new JMenu("Person");
		JMenuItem menuItemAddP = new JMenuItem("Add");
		menuItemAddP.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.META_MASK));
		menuItemAddP.addActionListener(new AddPersonAction());
		JMenuItem menuItemEditP = new JMenuItem("Edit");
		menuItemEditP.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.META_MASK));
		menuItemEditP.addActionListener(new EditPersonAction());
		JMenuItem menuItemDeleteP = new JMenuItem("Delete");
		menuItemDeleteP.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.META_MASK));
		menuItemDeleteP.addActionListener(new DeletePersonAction());
		
		menuPerson.add(menuItemAddP);
		menuPerson.add(menuItemEditP);
		menuPerson.add(menuItemDeleteP);
		mainMenu.add(menuPerson);
		
		// Activity
		menuActivity = new JMenu("Activity");
		JMenuItem menuItemAddA = new JMenuItem("Add");
		menuItemAddA.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.META_MASK));
		menuActivity.add(menuItemAddA);
		menuItemAddA.addActionListener(new ActionListener() {
			// ADD Activity
			@Override
			public void actionPerformed(ActionEvent e) { 
				if (mainList.getSelectedIndex() != -1) {
					DialogActivity dlg = new DialogActivity(frame);
					dlg.setModal(true);
					dlg.setVisible(true);
					dlg.dispose();
					listModel.get(mainList.getSelectedIndex()).addActivity(dlg.getResult());
				}
			}
		});
		JMenuItem menuItemShowA = new JMenuItem("Show");
		menuItemShowA.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.META_MASK));
		menuActivity.add(menuItemShowA);
		menuItemShowA.addActionListener(new ActionListener() {
			// SHOW Activities
			@Override
			public void actionPerformed(ActionEvent e) { 
				if (mainList.getSelectedIndex() != -1) {
					DialogShow dlg = new DialogShow(frame, listModel.get(mainList.getSelectedIndex()));
					dlg.setModal(true);
					dlg.setVisible(true);
					dlg.dispose();
				}
			}
		});
		
		mainMenu.add(menuActivity);
		
		
		// LIST
		listModel = new DefaultListModel<Person>();
		mainList = new JList<Person>(listModel);
		
		// BUTTONS
		GridLayout btnLayout = new GridLayout(1, 3);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(btnLayout);
		
		btnAdd = new JButton("Add person");
		btnAdd.addActionListener(new AddPersonAction());
		btnEdit = new JButton("Edit person");
		btnEdit.addActionListener(new EditPersonAction());
		btnDelete = new JButton("Delete person");
		btnDelete.addActionListener(new DeletePersonAction());

		buttonPanel.add(btnAdd);
		buttonPanel.add(btnEdit);
		buttonPanel.add(btnDelete);
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(mainList, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		// test person
		Person p = new Person("Walter White", "meth cook");
		p.addActivity(new Activity("8. 8. 2008", "1", "saving world"));
		listModel.addElement(p);
		p.addActivity(new Activity("5. 6. 2010", "2", "doing something"));
		//

		this.setJMenuBar(mainMenu);
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(666, 666));
		this.pack();
	}
	
	private class AddPersonAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DialogPerson dlg = new DialogPerson(Form.this, null );
			dlg.setModal(true);
			dlg.setVisible(true);
			dlg.dispose();
			if (dlg.getResult() != null)
				listModel.addElement(dlg.getResult());
		}
	}
	
	private class EditPersonAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (mainList.getSelectedIndex() != -1) {
				DialogPerson dlg = new DialogPerson(Form.this, listModel.get(mainList.getSelectedIndex()));
				dlg.setModal(true);
				dlg.setVisible(true);
				dlg.dispose();
				listModel.set(mainList.getSelectedIndex(), dlg.getResult());
			}
		}
	}
	
	private class DeletePersonAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (mainList.getSelectedIndex() != -1) {
				DialogConfirm dlg = new DialogConfirm(Form.this);
				dlg.setModal(true);
				dlg.setVisible(true);
				dlg.dispose();
				if (dlg.getResult())
					listModel.remove(mainList.getSelectedIndex());
			}
		}
	}
}
