package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNArcDirections;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;

public class DeleteArcWindow extends JDialog implements ItemListener, ActionListener {

	private PTNNet net;
	private PTNNode node;
	private HashMap<String, PTNArc> incomingArcs;
	private HashMap<String, PTNArc> outgoingArcs;
	private HashMap<String, PTNArc> arcsToDelete;
	private ActionListener listener;
	private NodeView nodeView;
	private JPanel panel;

	public DeleteArcWindow(PTNNet net, NodeView nodeView) {
		this.net = net;
		this.nodeView = nodeView;
		this.listener = listener;
		node = net.getNodeById(nodeView.getId());
		this.init();
	}

	private void init() {
		incomingArcs = net.getIncomingArcs(node);
		outgoingArcs = net.getOutgoingArcs(node);

		this.setLocationRelativeTo(nodeView);
		this.setFocusable(false);
		this.setLayout(new GridLayout(3,1));
		this.getContentPane().add(this.setupIncomingArcsPanel());
		this.getContentPane().add(this.setupOutgoingArcsPanel());
		this.add(this.getOkButton());
		this.pack();
	}

	private Component getOkButton() {
		JButton ok = new JButton("Weg damit...");
		ok.addActionListener(this);
		return ok;
	}

	/**
	 * We will set the checkboxes' name to the arcs id. This way we may easily identify later on
	 * which arcs shall be deleted.
	 * @param direction
	 * @return
	 */
	private JPanel setupArcsPanel(PTNArcDirections direction) {
		panel = new JPanel();
		JCheckBox arcCheck;
		HashMap<String, PTNArc> arclist = direction == PTNArcDirections.incoming ? incomingArcs : outgoingArcs;
		String labelText = direction == PTNArcDirections.incoming ? "Eingehende Kanten" : "Eingehende Kanten";
		panel.setLayout(new GridLayout(arclist.size() + 1, 1));
		panel.setSize(new Dimension(300, arclist.size() * 14));
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		panel.add(new JLabel(labelText));
		
		if (null != arclist && 0 < arclist.size()) {

			Iterator<Map.Entry<String, PTNArc>> it = arclist.entrySet()
					.iterator();
			PTNArc arc;

			while (it.hasNext()) {
				arc = it.next().getValue();
				String information = this.getArcInformationAsString(arc);
				arcCheck = new JCheckBox(information);
				arcCheck.setName(arc.getId());
				arcCheck.addItemListener(this);
				panel.add(arcCheck);
				panel.setVisible(true);
			}

		}
		
		return panel;

	}

	private JPanel setupIncomingArcsPanel() {
		return this.setupArcsPanel(PTNArcDirections.incoming);
	}

	private JPanel setupOutgoingArcsPanel() {
		return this.setupArcsPanel(PTNArcDirections.outgoing);
	}

	private String getArcInformationAsString(PTNArc arc) {

		String s = "Id: " + arc.getId() + " - " + "von: "
				+ arc.getSource().getName() + " -> " + "nach: "
				+ arc.getTarget().getName();

		return s;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		JCheckBox box = (JCheckBox)e.getSource();
		int change = e.getStateChange();
		
		if (change == ItemEvent.SELECTED) {
			//add to list
		} else if (change == ItemEvent.DESELECTED) {
			
			//remove from list
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.sendArcsToDelete();
		this.dispose();
	}

	public HashMap<String, PTNArc> sendArcsToDelete() {
		
			// evtl. schicken wir hier einen datentyp zurück mit dem der desktop
			// bzw. der Controller mehr anfaggen kann!
			return arcsToDelete;
		
	
		
	}

}
