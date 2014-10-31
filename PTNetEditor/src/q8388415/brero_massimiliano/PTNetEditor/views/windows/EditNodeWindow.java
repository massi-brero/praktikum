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

import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;

public class EditNodeWindow extends JDialog implements ActionListener {
	
	JMenuItem item;
	private JPanel panel;
	private JTextField nodeLabel;
	private JTextField token;
	private ActionListener listener;
	private NodeView sourceNode;
	
	public EditNodeWindow(NodeView node) {

		panel = new JPanel();
		panel.setSize(100, 200);
		panel.setLayout(new GridLayout(0,2));
		
		this.sourceNode = node;
		
		initializeDialog(node);
		add(panel);
		pack();

	}
	
	/**
	 * @ToDo sanitize token input!
	 * @param node
	 */
	private void initializeDialog(NodeView node) {
		
		nodeLabel = new JTextField(this.getSourceNode().getNodeLabel().getText(), 20);
		this.addToPanel(new JLabel("Knoten-Label"));
		this.addToPanel(nodeLabel);
		
		if (node instanceof PlaceView) {
			String tokenNumber = this.getSourceNode().getText();
			tokenNumber = tokenNumber == PlaceView.DOT_SIGN ? "1" : 
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
		dispose();
	}

	/**
	 * Updates node information from text fields and returns a DTO containing
	 * new label text and token number;
	 */
	public PTNINodeDTO sendUpdatedNode() {

		return new PTNINodeDTO() {
			@Override
			public int getToken() {
				return Integer.parseInt(token.getText());
			}
			
			@Override
			public String getNodeLabelText() {
				return nodeLabel.getText();
			}
		};
	}
}
