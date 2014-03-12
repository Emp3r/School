

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;


public class Example3 extends JFrame {

	public static void main(String[] args) throws Exception  {
		Example3 f = new Example3();
		f.setVisible(true);
	} 
	
	private JPanel panel = new JPanel();
	private JTree tree;
	private JButton btnAdd;
	
	public Example3() {
			
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		final DefaultMutableTreeNode subItem1 = new DefaultMutableTreeNode("sub-item 1");
		final DefaultMutableTreeNode subItem2 = new DefaultMutableTreeNode("sub-item 2");
		root.add(subItem1);
		root.add(subItem2);
		final DefaultTreeModel model = new DefaultTreeModel(root);
		tree = new JTree(model);
	
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("fooo");
				subItem2.add(newNode);
				model.nodeStructureChanged(subItem2);
				tree.expandPath(new TreePath(new Object[] { root, subItem2 }));
			}
		});
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(500, 300));
		
		panel.setLayout(new BorderLayout());
		panel.add(new JScrollPane(tree), BorderLayout.CENTER);
		panel.add(btnAdd, BorderLayout.SOUTH);
		getContentPane().add(panel);
				
		pack();
	}
}
