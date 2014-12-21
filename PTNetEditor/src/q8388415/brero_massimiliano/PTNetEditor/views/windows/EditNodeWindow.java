package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;
import q8388415.brero_massimiliano.PTNetEditor.utils.PTNNetValidator;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;

public class EditNodeWindow extends JDialog implements ActionListener {
	
	JMenuItem item;
	private JPanel panel;
	private JTextField nameLabel;
	private JTextField token;
    private Boolean isInformationToBeSent = false;
	private NodeView sourceNode;
	private Dimension BUTTON_SIZE = new Dimension(50, 20);
	private Dimension WINDOW_SIZE = new Dimension(200, 100);
	
	public EditNodeWindow(NodeView node) {

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(node);
		panel = new JPanel();
		panel.setPreferredSize(WINDOW_SIZE);
		panel.setLayout(new GridLayout(0,2));
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
		okButton.setPreferredSize(BUTTON_SIZE);
		this.addToPanel(okButton);
		
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(this);
		cancelButton.setPreferredSize(BUTTON_SIZE);
		this.addToPanel(cancelButton);
			
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
		
	     /**
         * First check if we have a valid token. We'll check that in this window
         * because we need no further information for this check.
         * 
         */
        if (e.getActionCommand().equals("OK")) {

                if (PTNNodeTypes.STELLE == sourceNode.getType() && !PTNNetValidator.isValidToken(token.getText())) {
                        JOptionPane.showConfirmDialog(this, "Bitte geben Sie eine Zahl zwischen 0-999 ein.", "Token Fehler", JOptionPane.PLAIN_MESSAGE);
                } else {
                    isInformationToBeSent = true;
                    this.dispose();                 
                }

        } else {
        	this.dispose();
        }
	}

	/**
	 * Updates node information from text fields and returns a DTO containing
	 * new label text and token number;
	 */
	public PTNINodeDTO sendUpdatedNode() {

		if (isInformationToBeSent) {
			return new PTNINodeDTO() {
				@Override
				public Integer getToken() {
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
		
		return null;
	}
}
