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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;
import q8388415.brero_massimiliano.PTNetEditor.utils.PTNNetValidator;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;

/**
 * Dialog window to change the node's attributes.
 * For a place the use may change:
 * <ul>
 * <li>node name</li>
 * <li>token number</li>
 * </ul><br />
 * For a transition:
 * <ul>
 * <li>node name</li>
 * </ul>
 * 
 * @author q8388415 - Massimiliano Brero
 *
 */
public class EditNodeWindow extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Node information. Token will be only displayed if type of node is place
	 */
	private JPanel panel;
	private JTextField nameLabel;
	private JTextField token;
	
	/**
	 * Needed so not valid or empty paramaters are not sended erraneously.
	 */
    private Boolean isInformationToBeSent = false;
    
    /**
     * Node which "generated event".
     */
	private NodeView sourceNode;
	
	/**
	 * basic window parameters
	 */
	private Dimension BUTTON_SIZE = new Dimension(40, 20);
	private Dimension WINDOW_SIZE_PLACE = new Dimension(200, 90);
	private Dimension WINDOW_SIZE_TRANSITION = new Dimension(200, 60);
	
	/**
	 * 
	 * @param node NodeView
	 * 		Node which "generated event".
	 */
	public EditNodeWindow(NodeView node) {

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(node);
		this.setResizable(false);
		panel = new JPanel();
		
		// 
		if (node.getType() == PTNNodeTypes.STELLE)
			panel.setPreferredSize(WINDOW_SIZE_PLACE);
		else 
			panel.setPreferredSize(WINDOW_SIZE_TRANSITION);
			
		panel.setLayout(new GridLayout(0,2));
		this.setFocusable(false);
		this.sourceNode = node;
		
		this.initializeDialog(node);
		this.add(panel);
		this.pack();

	}
	
	/**
	 * Token input will bes sanitized!
	 * 
	 * @param node NodeView
	 */
	private void initializeDialog(NodeView node) {
		
		nameLabel = new JTextField(this.getSourceNode().getNameLabel().getText(), 20);
		this.addToPanel(new JLabel("Knoten-Label"));
		this.addToPanel(nameLabel);
		
		if (node .getType() == PTNNodeTypes.STELLE) {
			String tokenNumber = this.getSourceNode().getText();
			tokenNumber = tokenNumber == ((PlaceView)node).getDOTSign() ? "1" : 
							(tokenNumber == "" ? "0" : this.getSourceNode().getText());
			token = new JTextField(tokenNumber, 20);
			this.addToPanel(new JLabel("Tokens"));
			this.addToPanel(token);
		}
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(this);
		okButton.setMaximumSize(BUTTON_SIZE);
		this.addToPanel(okButton);
		
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(this);
		cancelButton.setPreferredSize(BUTTON_SIZE);
		this.addToPanel(cancelButton);
			
	}
	
	/**
	 * Getter.
	 * 
	 * @return JPanel
	 */
	public JPanel getPanel() {
		return this.panel;
	}
	
	/**
	 * Adds single components to main panel.
	 * 
	 * @param c JComponent
	 */
	public void addToPanel(JComponent c) {
		this.getPanel().add(c);
	}

	/**
	 * 
	 * @return NodeView
	 * 		Node whose attributes shall be changed.
	 */
	public NodeView getSourceNode() {
		return this.sourceNode;
	}


	/**
	 * First check if we have a valid token. We'll check that in this window
     * because we need no further information for this check.
     * 
     * @param e ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
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
	 * 
	 * @return {@link PTNINodeDTO}
	 * 		Updates node information from text fields and returns a DTO containing
	 * 		new label text and token number.
	 * 		Token input will be validated. If window is closed or dialog canceled
	 * 		method returns null.
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
