package q8388415.brero_massimiliano.PTNetEditor.views.desktop;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JPanel;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.views.NodeView;
import q8388415.brero_massimiliano.PTNetEditor.views.partials.PTNEnlargementPanel;

public class PTNControlPanel extends JPanel {
	
	private JButton deselect;
	private JButton delSelection;
	private JButton addNewNode;
	private JPanel buttonPanel;
	private JPanel controllerPanel;
	private PTNDesktop desktop;
	private PTNEnlargementPanel placeSizePanel;
	private PTNEnlargementPanel transitionSizePanel;
	private PTNEnlargementPanel arrowHeadSizePanel;
	
	public PTNControlPanel(PTNAppController appControl, final PTNDesktop desktop) {
		
		this.desktop = desktop;
		deselect = new JButton("unselect");
		delSelection = new JButton("erase");
		deselect.addActionListener(appControl);
		delSelection.addActionListener(appControl);
		addNewNode = new JButton("neuer Knoten");
		addNewNode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				desktop.callNewNodeDialog();				
			}
		});
		

		this.init();
		
	}

	private void init() {
		
		buttonPanel = new JPanel();
		controllerPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0,1));
		controllerPanel.setLayout(new GridLayout(0,1));
		
		// add enlargement Panels
		placeSizePanel = new PTNEnlargementPanel("Place Size");
		transitionSizePanel = new PTNEnlargementPanel("Transition Size");
		arrowHeadSizePanel = new PTNEnlargementPanel("Arrowhead Size");
		
		controllerPanel.add(placeSizePanel);
		controllerPanel.add(transitionSizePanel);
		controllerPanel.add(arrowHeadSizePanel);
		
		// add basic buttons
		buttonPanel.add(deselect);
		buttonPanel.add(delSelection);
		buttonPanel.add(addNewNode);
		
		this.setSize(new Dimension(700, 30));
		this.setLayout(new GridLayout(0, 2));
		this.setUpListeners();
		this.add(buttonPanel);
		this.add(controllerPanel);
		setDoubleBuffered(true);
	}
	
	private void setUpListeners() {
		
		Iterator<NodeView> it = desktop.getNodeViews().iterator();
		
		while (it.hasNext()) {

			NodeView node = it.next();
			
			switch (node.getType()) {
			case place:
				placeSizePanel.addScaleListener(node);
				break;
			case transition:
				transitionSizePanel.addScaleListener(node);
				break;
			default:
				break;
			}
			
			
		}
		
		
	}

}
