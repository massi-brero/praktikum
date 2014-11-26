package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;
import q8388415.brero_massimiliano.PTNetEditor.utils.PTNNetValidator;

public class NewNodeWindow extends JDialog implements ActionListener {

    JMenuItem item;
    private JPanel panel;
    private JComboBox types;
    JLabel tokenLabel;
    private JTextField nameLabel;
    private JTextField idLabel;
    private JTextField token;
    private Boolean isInformationToBeSent = false;

    public NewNodeWindow() {

        panel = new JPanel();
        panel.setSize(100, 200);
        panel.setLayout(new GridLayout(0, 2));
        this.setFocusable(false);
        /**
         * Do nothing because we could still have incorrect data in some of the fields.
         */
        //this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

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

        JButton okButton = new JButton("Abspeichern");
        okButton.addActionListener(this);
        this.addToPanel(okButton);
        
        JButton cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(this);
        this.addToPanel(cancelButton);

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
        /**
         * First check if we have a valid token. We'll check that in this window
         * because we need no further information for this check.
         * 
         */
        if (e.getActionCommand().equals("Abspeichern")) {

                if (types.getSelectedItem() == PTNNodeTypes.place && !PTNNetValidator.isValidToken(token.getText())) {
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
    public PTNINodeDTO sendParams() {

        if (isInformationToBeSent) {
            
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
                    return idLabel.getText();
                }
                
                @Override
                public PTNNodeTypes getType() {
                    return (PTNNodeTypes) types.getSelectedItem();
                }
            };
            
        }
        
        return null;
        
    }
}
