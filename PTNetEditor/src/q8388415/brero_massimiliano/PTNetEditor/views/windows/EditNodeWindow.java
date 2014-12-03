package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;

public class EditNodeWindow extends JDialog implements ActionListener {
	
	JMenuItem item;
	private JPanel panel;
	private JTextField nameLabel;
	private JTextField token;
	private NodeView sourceNode;
	
	public EditNodeWindow(NodeView node) {

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		panel = new JPanel();
		panel.setSize(100, 200);
		panel.setLayout(new GridLayout(0,2));
		this.setLocationRelativeTo(node);
		this.setFocusable(false);
		this.sourceNode = node;
		
		this.initializeDialog(node);
		this.add(panel);
		this.pack();

	}
	
	/**
	 * @ToDo sanitize token input!
	 * @param node
	 */
	private void initializeDialog(NodeView node) {
		
		nameLabel = new JTextField(this.getSourceNode().getNameLabel().getText(), 20);
		this.addToPanel(new JLabel("Knoten-Label"));
		this.addToPanel(nameLabel);
		
		if (node instanceof PlaceView) {
			String tokenNumber = this.getSourceNode().getText();
			tokenNumber = tokenNumber == ((PlaceView)node).getDOTSign() ? "1" : 
							(tokenNumber == "" ? "0" : this.getSourceNode().getText());
			token = new JTextField(tokenNumber, 20);
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

	public NodeView getSourceNode() {
		return this.sourceNode;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		this.sendUpdatedNode();
		this.dispose();
	}

	/**
	 * Updates node information from text fields and returns a DTO containing
	 * new label text and token number;
	 */
	public PTNINodeDTO sendUpdatedNode() {

		return new PTNINodeDTO() {
			@Override
			public int getToken() {
				return token.getText().equals("") || null == token.getText() ? 0 : Integer.parseInt(token.getText());
			}
			
			@Override
			public String getNodeName() {
				return nameLabel.getText();
			}

			@Override
			public String getId() {
				return sourceNode.getId();
			}

			@Override
			public PTNNodeTypes getType() {
				return sourceNode.getType();
			}
			
			@Override
			public Point getLocation() {
				return sourceNode.getLocation();
			}
		};
	}
}
