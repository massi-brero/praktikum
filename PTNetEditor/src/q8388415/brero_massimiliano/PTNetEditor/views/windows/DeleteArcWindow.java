package q8388415.brero_massimiliano.PTNetEditor.views.windows;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNArcDirections;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNIArcDTO;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;

public class DeleteArcWindow extends JDialog implements ItemListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private PTNNode node;
	private HashMap<String, PTNArc> incomingArcs;
	private HashMap<String, PTNArc> outgoingArcs;
	private HashMap<String, PTNArc> allNodeArcs;
	private HashMap<String, PTNIArcDTO> arcsToDelete;
	private JPanel basePanel = new JPanel();
	private final int BUTTON_HEIGHT = 30;
	private final int BUTTON_WIDTH = 120;
	private final int WIDTH = 120;
	private NodeView nodeView;
	private JPanel panel;

	public DeleteArcWindow(PTNNet net, NodeView nodeView) {
		this.nodeView = nodeView;
		allNodeArcs = new HashMap<String, PTNArc>();
		arcsToDelete = new HashMap<String, PTNIArcDTO>();
		node = net.getNodeById(nodeView.getId());
		incomingArcs = net.getIncomingArcs(node);
		outgoingArcs = net.getOutgoingArcs(node);
		allNodeArcs.putAll(incomingArcs);
		allNodeArcs.putAll(outgoingArcs);
		this.init();
	}

	private void init() {

		this.setLocationRelativeTo(nodeView);
		this.setFocusable(false);
		this.setResizable(false);
		
		if (null != incomingArcs && 0 < incomingArcs.size())
			this.basePanel.add(this.setupIncomingArcsPanel());
		
		if (null != outgoingArcs && 0 < outgoingArcs.size())
			this.basePanel.add(this.setupOutgoingArcsPanel());
		
		basePanel.add(this.getOkButton());
		basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));
		this.getContentPane().add(basePanel);
		this.pack();
		
	}

	private Component getOkButton() {
		JPanel buttonPanel = new JPanel();
		JButton ok = new JButton("Weg damit...");
		
		ok.addActionListener(this);
		ok.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.add(ok);
		
		return buttonPanel;
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
		String labelText = direction == PTNArcDirections.incoming ? "Eingehende Kanten" : "Ausgehende Kanten";
		this.initPanel(arclist, labelText);
		
		if (null != arclist && 0 < arclist.size()) {

			Iterator<Map.Entry<String, PTNArc>> it = arclist.entrySet().iterator();
			PTNArc arc;

			while (it.hasNext()) {
				arc = it.next().getValue();
				String information = this.getArcInformationAsString(arc, direction);
				arcCheck = new JCheckBox(information);
				arcCheck.setName(arc.getId());
				arcCheck.addItemListener(this);
				panel.add(arcCheck);
				panel.setVisible(true);
			}

		}
		
		return panel;

	}

	private void initPanel(HashMap<String, PTNArc> arclist, String labelText) {
		
		int maxLines = incomingArcs.size() >= outgoingArcs.size() ? incomingArcs.size() : outgoingArcs.size();
		// we add 1 for the headline label
		panel.setLayout(new GridLayout(maxLines + 1, 1));
		panel.setSize(new Dimension(WIDTH, arclist.size() * 14));
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		panel.add(new JLabel(labelText));
		
	}

	private JPanel setupIncomingArcsPanel() {
		return this.setupArcsPanel(PTNArcDirections.incoming);
	}

	private JPanel setupOutgoingArcsPanel() {
		return this.setupArcsPanel(PTNArcDirections.outgoing);
	}

	/**
	 * A line of information about the arc, so the user may identify it
	 * when arcs get entangled in an complex net.
	 * If it's an incoming arc we display where its coming from, else 
	 * we show where its target is.
	 * 
	 * @param arc
	 * @param direction
	 * @return
	 */
	private String getArcInformationAsString(PTNArc arc, PTNArcDirections direction) {

		String s = ((PTNArcDirections.incoming == direction) ? 
							"von Knoten: "+ arc.getSource().getName() :
								"nach Knoten: " + arc.getTarget().getName());

		return s;
	}

	@Override
	/**
	 * Adds or removes selected/deselected arcs from the arcsToDeleteList.
	 * source.getName() returns the id of the arc that was selected
	 */
	public void itemStateChanged(ItemEvent e) {
		int change = e.getStateChange();
		String arcId = ((JCheckBox)e.getSource()).getName();
		PTNArc changedArc = allNodeArcs.get(arcId);
				
		try {
			if (change == ItemEvent.SELECTED)
				arcsToDelete.put(changedArc.getId(), changedArc);
			else if (change == ItemEvent.DESELECTED)
				arcsToDelete.remove(changedArc.getId());
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(this, "Es gab einen internen Fehler beim Kanten löschen.", "Fehler beim Kantenlöschen.", JOptionPane.WARNING_MESSAGE);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.sendArcsToDelete();
		this.dispose();
	}

	public HashMap<String, PTNIArcDTO> sendArcsToDelete() {

			return arcsToDelete;

	}

}
