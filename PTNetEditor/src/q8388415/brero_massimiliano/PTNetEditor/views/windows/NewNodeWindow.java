package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public class NewNodeWindow extends JDialog implements ActionListener {

	JMenuItem item;
	private JPanel panel;
	private JComboBox types;
	JLabel tokenLabel;
	private JTextField nameLabel;
	private JTextField idLabel;
	private JTextField token;

	public NewNodeWindow() {

		panel = new JPanel();
		panel.setSize(100, 200);
		panel.setLayout(new GridLayout(0, 2));
		this.setFocusable(false);

		initializeDialog();
		add(panel);
		pack();

	}

	/**
	 * @ToDo sanitize token input!
	 * @param node
	 */
	private void initializeDialog() {
		
		this.addToPanel(new JLabel("Knoten-Typ"));
		types = this.initializeDropDown();
		this.addToPanel(types);
		tokenLabel = new JLabel("Tokens");
		this.addToPanel(tokenLabel);
		token = new JTextField("", 20);
		this.addToPanel(token);
	
		types.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (types.getSelectedItem() == PTNNodeTypes.transition) {
					tokenLabel.setVisible(false);
					token.setVisible(false);
				} else {
					tokenLabel.setVisible(true);
					token.setVisible(true);
				}
			}
		});
		
		

		idLabel = new JTextField("", 20);
		this.addToPanel(new JLabel("Knoten-ID"));
		this.addToPanel(idLabel);
		
		nameLabel = new JTextField("", 20);
		this.addToPanel(new JLabel("Knoten-Label"));
		this.addToPanel(nameLabel);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(this);
		this.addToPanel(okButton);

	}

	private JComboBox<PTNNodeTypes> initializeDropDown() {
		return new JComboBox(PTNNodeTypes.values());
	}

	public JPanel getPanel() {
		return this.panel;
	}

	public void addToPanel(JComponent c) {
		this.getPanel().add(c);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		this.sendParams();
		this.dispose();
	}

	/**
	 * Updates node information from text fields and returns a DTO containing
	 * new label text and token number;
	 */
	public PTNINodeDTO sendParams() {

		return new PTNINodeDTO() {
			@Override
			public int getToken() {
				return token.getText().equals("") || null == token.getText()  ? 0 : Integer.parseInt(token.getText());
			}

			@Override
			public String getNodeName() {
				return nameLabel.getText();
			}

			@Override
			public String getId() {
				return idLabel.getText();
			}
			
			@Override
			public PTNNodeTypes getType() {
				return (PTNNodeTypes)types.getSelectedItem();
			}
		};
	}
}
