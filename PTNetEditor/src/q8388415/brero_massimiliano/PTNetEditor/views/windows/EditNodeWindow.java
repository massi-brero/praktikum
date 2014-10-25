package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;

public class EditNodeWindow extends JDialog implements ActionListener {
	
	JMenuItem item;
	private JPanel panel;
	private JTextField nodeLabel;
	private JTextField token;
	private ActionListener listener;
	private NodeView node;
	
	public EditNodeWindow(NodeView node) {

		panel = new JPanel();
		panel.setSize(100, 200);
		panel.setLayout(new GridLayout(0,2));
		
		this.node = node;
		
		initializeDialog(node);
		add(panel);
		pack();

	}
	
	/**
	 * @ToDo sanitize token input!
	 * @param node
	 */
	private void initializeDialog(NodeView node) {
		
		nodeLabel = new JTextField(this.getNode().getNodeLabel().getText(), 20);
		this.addToPanel(new JLabel("Knoten-Label"));
		this.addToPanel(nodeLabel);
		
		if (node instanceof PlaceView) {
			token = new JTextField(this.getNode().getText(), 20);
			this.addToPanel(new JLabel("Tokens"));
			this.addToPanel(token);
		}
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(this);
		this.addToPanel(okButton);
			
	}
	
	public JPanel getPanel() {
		return this.panel;
	}
	
	public void addToPanel(JComponent c) {
		this.getPanel().add(c);
	}

	public NodeView getNode() {
		return this.node;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		this.getNode().getNodeLabel().setText(this.nodeLabel.getText());
		
		if (this.getNode() instanceof PlaceView)
			this.getNode().setText(this.token.getText());
		
		this.sendUpdatedNode();
		
		dispose();
		
		
	}


	public NodeView sendUpdatedNode() {
		return this.getNode();
	}

	
}
