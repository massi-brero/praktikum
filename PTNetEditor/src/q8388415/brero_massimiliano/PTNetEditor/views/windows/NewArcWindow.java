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

public class NewArcWindow extends JDialog implements ActionListener {
	
	JMenuItem item;
	private JPanel panel;
	private JTextField idField;
	
	public NewArcWindow() {

		panel = new JPanel();
		panel.setSize(100, 200);
		panel.setLayout(new GridLayout(2,2));
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
		
		idField = new JTextField("", 20);
		this.addToPanel(idField);
		this.addToPanel(new JLabel("Kanten ID"));
		
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



	@Override
	public void actionPerformed(ActionEvent e) {
		this.sendId();
		this.dispose();
	}

	/**
	 * Updates node information from text fields and returns a DTO containing
	 * new label text and token number;
	 */
	public String sendId() {
		return idField.getText();
	}
}
