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
	private ActionListener listener;
	private NodeView node;
	private String labelText;
	private String token;
	
	public EditNodeWindow(NodeView node) {

		panel = new JPanel();
		panel.setSize(100, 200);
		panel.setLayout(new GridLayout(0,2));
		
		this.node = node;
		this.setLabelText(node.getLabel().getText());
		this.setToken(node.getText());
		
		initializeDialog(node);
		add(panel);
		pack();

	}
	

	private void initializeDialog(NodeView node) {
		
		JTextField labelField = new JTextField(this.getLabelText(), 20);
		this.addToPanel(new JLabel("Knoten-Label"));
		this.addToPanel(labelField);
		
		if (node instanceof PlaceView) {
			this.addToPanel(new JLabel("Tokens"));
			this.addToPanel(new JTextField(token, 20));
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

		this.getNode().getLabel().setText(labelText);
		
		if ("" != this.getToken())
			this.getNode().setText(this.getToken());
		
		node.getParent().repaint();
		
		
	}
	
	public String getLabelText() {
		return labelText;
	}


	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}

	
}
