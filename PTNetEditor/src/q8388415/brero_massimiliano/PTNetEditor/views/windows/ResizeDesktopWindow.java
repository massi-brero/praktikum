package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import q8388415.brero_massimiliano.PTNetEditor.utils.PTNNetValidator;

public class ResizeDesktopWindow extends JDialog implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextField widthField;
	private JTextField heightField;
	private Boolean isInformationToBeSent = false;
	private Dimension currentSize;
	
	public ResizeDesktopWindow(Dimension size) {

		currentSize = size;
		panel = new JPanel();
		panel.setSize(100, 200);
		panel.setLayout(new GridLayout(0,2));
		this.setFocusable(false);
		
        /**
         * Do nothing because we could still have incorrect data in some of the fields.
         */
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		initializeDialog();
		add(panel);
		pack();

	}
	
	/**
	 * @ToDo sanitize token input!
	 * @param node
	 */
	private void initializeDialog() {
		
		widthField = new JTextField(String.valueOf((int)currentSize.getWidth()), 20);
		this.addToPanel(new JLabel("Breite"));
		this.addToPanel(widthField);
		
		heightField = new JTextField(String.valueOf((int)currentSize.getHeight()), 20);
		this.addToPanel(new JLabel("Höhe"));
		this.addToPanel(heightField);
		
		
		JButton okButton = new JButton("Übernehmen");
		okButton.addActionListener(this);
		this.addToPanel(okButton);
		
		JButton cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(this);
        this.addToPanel(cancelButton);
			
	}
	
	public JPanel getPanel() {
		return this.panel;
	}
	
	public void addToPanel(JComponent c) {
		this.getPanel().add(c);
	}


	/**
	 * Sets that information can be sent if check returns we have
	 * valid inputs.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	    if (e.getActionCommand().equals("Übernehmen")) {

            if (!PTNNetValidator.isValidDesktopDimensions(widthField.getText(), heightField.getText())) {
                    JOptionPane.showConfirmDialog(this, "Bitte geben Sie für Breite und Höhe ganze Zahlen ein.", 
                    								"Eingabe-Fehler", JOptionPane.PLAIN_MESSAGE);
            } else {
                isInformationToBeSent = true;
                this.dispose();                 
            }
	    }
	}
	        

	/**
	 * Send new desktop size from user input after validation.
	 */
	public Dimension sendSize() {
	    
	    if (isInformationToBeSent) {
	        return new Dimension(Integer.parseInt(widthField.getText()), Integer.parseInt(heightField.getText()));
	    }
	    
	    return null;

	}
}
