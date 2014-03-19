package Class04;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.*;

import java.util.ArrayList;
import java.util.List;

public class Form extends JFrame {
	
	private static final long serialVersionUID = 12345678765438L;
	
	private JMenuBar mainMenu;
	private JMenu menuFile;
	private JMenu menuPerson;
	private JMenu menuActivity;
	private JTree tree;
	private JTable table;
	private MyTableModel tableModel;
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode root;
	private List<Person> listOfPersons = new ArrayList<>();
	private String[] columnsP = new String[] { "Name", "Occupation" };
	private String[] columnsA = new String[] { "Date", "Hours", "Text"};
	private Object[][] valuesP = new Object[][] {};
	private Object[][] valuesA = new Object[][] {};
	private JPanel buttonPanel = new JPanel();
	
	
	//// >> FORM << ////
	public Form() {
		super();
		this.setTitle("Company");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		initializeMenu();
		
		tableModel = new MyTableModel(columnsP, valuesP);
		table = new JTable(tableModel);
		fillTablePersons(listOfPersons);
		
		root = new DefaultMutableTreeNode("Firma");
		treeModel = new DefaultTreeModel(root);
		tree = new JTree(treeModel);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
		    public void valueChanged(TreeSelectionEvent e) {
		        DefaultMutableTreeNode selectedNode;
		        selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		        if (selectedNode == null) 
		        	return;		        
		        Object nodeObject = selectedNode.getUserObject();
		        
		        if (nodeObject.getClass() == Person.class) {
		        	Person p = (Person)nodeObject;
		        	fillTableActivity(p);
		           	table.setModel(new MyTableModel(columnsA, valuesA));
		        }
		        else if (nodeObject.getClass() == Activity.class) {
		        	nodeObject = ((DefaultMutableTreeNode)selectedNode.getParent()).getUserObject();
		        	Person p = (Person)nodeObject;
		        	fillTableActivity(p);
		           	table.setModel(new MyTableModel(columnsA, valuesA));
		        }
		        else {
		    		fillTablePersons(listOfPersons);
		        	table.setModel(new MyTableModel(columnsP, valuesP));
		        }
		    }
		});
		
		JScrollPane treePane = new JScrollPane(tree);
		treePane.setPreferredSize(new Dimension(190, 660));

		initializeButtons();
		
		test();		
		this.setLayout(new BorderLayout());
		this.setJMenuBar(mainMenu);
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		this.add(treePane, BorderLayout.WEST);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension(666, 666));
		this.pack();
	}
	
	
	//// MENU ////
	private void initializeMenu() {
		mainMenu = new JMenuBar();
			// File //
		menuFile = new JMenu("File");
		mainMenu.add(menuFile);
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		menuItemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { setVisible(false); dispose(); }
		});
		menuFile.add(menuItemExit);
			// Person //
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
			// Activity //
		menuActivity = new JMenu("Activity");
		JMenuItem menuItemAddA = new JMenuItem("Add");
		menuItemAddA.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.META_MASK));
		menuActivity.add(menuItemAddA);
		menuItemAddA.addActionListener(new AddActivityAction());
		JMenuItem menuItemDelA = new JMenuItem("Delete");
		menuItemDelA.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.META_MASK));
		menuActivity.add(menuItemDelA);
		menuItemDelA.addActionListener(new DeleteActivityAction());
		mainMenu.add(menuActivity);
	}
	
	//// MENU ACTIONS ////
		// Person
	private class AddPersonAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DialogPerson newOne = new DialogPerson(Form.this, null);
			newOne.setModal(true);
			newOne.setVisible(true);
			if (newOne.getResult() != null) {
				listOfPersons.add(newOne.getResult());
				DefaultMutableTreeNode newPerson = new DefaultMutableTreeNode(newOne.getResult());
				root.add(newPerson);
				treeModel.nodeStructureChanged(root);
				tree.expandPath(new TreePath(new Object[] { root, newPerson }));
				fillTablePersons(listOfPersons);
				table.setModel(new MyTableModel(columnsP, valuesP));
			}
		}
	}
	private class EditPersonAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (tree.getLastSelectedPathComponent() != null) {
				DefaultMutableTreeNode nodeBeingChanged;
				nodeBeingChanged = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (nodeBeingChanged.getUserObject().getClass() == Person.class) {
					DialogPerson newOne = new DialogPerson(Form.this, (Person) nodeBeingChanged.getUserObject());
					newOne.setModal(true);
					newOne.setVisible(true);
					if (newOne.getResult() != null){
						listOfPersons.get(listOfPersons.indexOf((Person)nodeBeingChanged.
								getUserObject())).setName(newOne.getResult().getName());
						listOfPersons.get(listOfPersons.indexOf((Person)nodeBeingChanged.
								getUserObject())).setOccupation(newOne.getResult().getOccupation());
						fillTablePersons(listOfPersons);
						table.setModel(new MyTableModel(columnsP, valuesP));
					}
				} 
				else {
					JOptionPane.showMessageDialog(null,"Select somebody.", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			} else
				JOptionPane.showMessageDialog(null, "Nothing selected.", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	private class DeletePersonAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (tree.getLastSelectedPathComponent() != null) {
				DefaultMutableTreeNode nodeBeingRemoved = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (nodeBeingRemoved.getUserObject().getClass() == Person.class) {
					int dialogResult = JOptionPane.
							showConfirmDialog(null, "Really?", "Deleting person", JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_OPTION) {
						listOfPersons.remove(nodeBeingRemoved.getUserObject());
						treeModel.removeNodeFromParent(nodeBeingRemoved);
						fillTablePersons(listOfPersons);
						table.setModel(new MyTableModel(columnsP, valuesP));
					}
				}
			} else
				JOptionPane.showMessageDialog(null, "Nothing selected.", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
		// Activity
	private class AddActivityAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (tree.getLastSelectedPathComponent() != null) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				if (selectedNode.getUserObject().getClass() == Person.class){
					DialogActivity a = new DialogActivity(null);
					a.setModal(true);
					a.setVisible(true);
					if (a.getResult() != null){ 
						listOfPersons.get(listOfPersons.indexOf((Person)selectedNode.getUserObject())).addActivity(a.getResult());
						DefaultMutableTreeNode newActivity = new DefaultMutableTreeNode(a.getResult());
						selectedNode.add(newActivity);
						treeModel.nodeStructureChanged(selectedNode);
						fillTableActivity((Person)selectedNode.getUserObject());
			           	table.setModel(new MyTableModel(columnsA, valuesA));
					}
				} else 
					JOptionPane.showMessageDialog(null, "Select something.", "Error", JOptionPane.INFORMATION_MESSAGE);
			} else 
				JOptionPane.showMessageDialog(null, "Select something.", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	private class DeleteActivityAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (tree.getLastSelectedPathComponent() != null) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				if (selectedNode.getUserObject().getClass() == Activity.class){
					int dialogResult = JOptionPane.
							showConfirmDialog(null, "Really?", "Deleting activity", JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_OPTION) {
						DefaultMutableTreeNode personNode = (DefaultMutableTreeNode)selectedNode.getParent();
						Person p = (Person)personNode.getUserObject();
						Activity a = (Activity)selectedNode.getUserObject();
						p.getActivities().remove(a);
						treeModel.removeNodeFromParent(selectedNode);
						fillTableActivity(p);
						table.setModel(new MyTableModel(columnsA, valuesA));
					}
				} else 
					JOptionPane.showMessageDialog(null, "Select something.", "Error", JOptionPane.INFORMATION_MESSAGE);
			} else 
				JOptionPane.showMessageDialog(null, "Select something.", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	//// BUTTONS ////
	private void initializeButtons() {
		GridLayout btnLayout = new GridLayout(1, 5);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(btnLayout);
		JButton btnAdd = new JButton("Add person");
		btnAdd.addActionListener(new AddPersonAction());
		JButton btnEdit = new JButton("Edit person");
		btnEdit.addActionListener(new EditPersonAction());
		JButton btnDelete = new JButton("Delete person");
		btnDelete.addActionListener(new DeletePersonAction());
		JButton btnAddA = new JButton("Add activity");
		btnAddA.addActionListener(new AddActivityAction());
		JButton btnDelA = new JButton("Delete activity");
		btnDelA.addActionListener(new DeleteActivityAction());		
		buttonPanel.add(btnAdd);
		buttonPanel.add(btnEdit);
		buttonPanel.add(btnDelete);
		buttonPanel.add(btnAddA);
		buttonPanel.add(btnDelA);
		btnAdd.setPreferredSize(new Dimension(100, 70));
	}
	
	
	
	//// TABLE ////
	private void fillTablePersons(List<Person> listOfPersones) {
		valuesP = new Object[listOfPersones.size()][2];
		for (int i = 0; i < listOfPersons.size(); i++) {
			valuesP[i][0] = listOfPersons.get(i).getName();
			valuesP[i][1] = listOfPersons.get(i).getOccupation();
		}
	}
	private void fillTableActivity(Person p) {
		valuesA = new Object[p.getActivities().size()][3];
		for (int i = 0; i < p.getActivities().size(); i++) {
			valuesA[i][0] = p.getActivities().get(i).getDate();
			valuesA[i][1] = p.getActivities().get(i).getHours();
			valuesA[i][2] = p.getActivities().get(i).getName();
		}
	}

	//// TABLE MODEL ////
	private class MyTableModel extends AbstractTableModel {
		
		private static final long serialVersionUID = 12435675432L;
		private String[] columns;
		private Object[][] values;

		public MyTableModel(String[] c, Object[][] v) {
			super();
			this.columns = c;
			this.values = v;
		}
		@Override
		public int getColumnCount() { return columns.length; }
		@Override
		public int getRowCount() { return values.length; }
		@Override
		public Object getValueAt(int arg0, int arg1) {
			return values[arg0][arg1];
		}
		@Override
		public String getColumnName(int column) {
			return columns[column];
		}
	}
	
	// Test
	private void test() {
		Person testPerson1 = new Person("Noam Chomsky", "linguist");
		DefaultMutableTreeNode testNode1 = new DefaultMutableTreeNode(testPerson1);
		Activity testActivity1 = new Activity("4. 2. 1980", "4", "analytic philosophy stuff");
		testPerson1.addActivity(testActivity1);
		DefaultMutableTreeNode testNodeActivity1 = new DefaultMutableTreeNode(testActivity1);
		testNode1.add(testNodeActivity1);
		tree.expandPath(new TreePath(new Object[] { testNode1 }));
		listOfPersons.add(testPerson1);
		root.add(testNode1);
		
		Person testPerson2 = new Person("Kal-El", "superhero");
		DefaultMutableTreeNode testNode2 = new DefaultMutableTreeNode(testPerson2);
		Activity testActivity2 = new Activity("6. 6. 1945", "4", "saving world");
		DefaultMutableTreeNode testNodeActivity2 = new DefaultMutableTreeNode(testActivity2);
		testNode2.add(testNodeActivity2);
		testPerson2.addActivity(testActivity2);
		tree.expandPath(new TreePath(new Object[] { testNode2 }));
		Activity testActivity3 = new Activity("18. 3. 2014", "2", "still saving world");
		DefaultMutableTreeNode testNodeActivity3 = new DefaultMutableTreeNode(testActivity3);
		testNode2.add(testNodeActivity3);
		testPerson2.addActivity(testActivity3);
		tree.expandPath(new TreePath(new Object[] { testNode2 }));
		listOfPersons.add(testPerson2);
		root.add(testNode2);
       	
		treeModel.nodeStructureChanged(root);
		fillTablePersons(listOfPersons);
		table.setModel(new MyTableModel(columnsP, valuesP));
	}
}
