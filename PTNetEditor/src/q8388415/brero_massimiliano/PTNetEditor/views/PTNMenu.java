package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNFileController;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.utils.PTNInfoMessages;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

/**
 * Menu bar for the application containing these menus:
 * <ul>
 * <li>desktop</li>
 * <li>edit</li>
 * <li>file</li>
 * <li>mode</li>
 * <li>information</li>
 * </ul>
 * 
 * @author Laptop
 *
 */
public class PTNMenu extends JMenuBar {

	private static final long serialVersionUID = 1L;
	private PTNDesktop desktop;
	private PTNNet net;
	private PTNAppController appControl;
	public static final int HEIGHT = 20;
	public static final int WIDTH = 500;
	private JMenu desktopMenu;
	private JMenu nodeMenu;
	private JMenu fileMenu;
	private JMenu modusMenu;
	private JMenu infoMenu;

	public PTNMenu(PTNDesktop desktop, PTNAppController appC, PTNNet net) {

		this.desktop = desktop;
		this.net = net;
		this.appControl = appC;
		this.init();

	}

	private void init() {

		desktopMenu = this.initDesktopMenu();
		nodeMenu = this.initStandardMenu();
		fileMenu = this.initFileMenu();
		modusMenu = this.initModusMenu();
		infoMenu = this.addInfoMenu();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		this.add(desktopMenu);
		this.add(nodeMenu);
		this.add(fileMenu);
		this.add(modusMenu);
		this.add(infoMenu);

	}

	private JMenu addInfoMenu() {

		JMenu menu = new JMenu("Info");
		menu.setMnemonic('I');
		JMenuItem item1 = new JMenuItem("Version");
		JMenuItem item2 = new JMenuItem("Credits");

		item1.setIcon(new ImageIcon("icons/info-icon.png"));
		item1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "<html><body>" +
								 PTNInfoMessages.getVersionInformation()
								 + PTNInfoMessages.getCopyrightInformation()
								 + "</body></html>";
								
				JOptionPane.showMessageDialog(desktop, message, "Copyright", JOptionPane.PLAIN_MESSAGE);
			}
		});

		item2.setIcon(new ImageIcon("icons/credits-icon.png"));
		item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "<html><body>" +
						 PTNInfoMessages.getCredits()
						 + "</body></html>";
						
		JOptionPane.showMessageDialog(desktop, message, "Credits", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		menu.add(item1);
		menu.add(item2);

		return menu;

	}

	private JMenu initDesktopMenu() {
		
		JMenu menu = new JMenu("Schreibtisch");
		menu.setMnemonic('D');
		JMenuItem item1 = new JMenuItem("Schreibtisch vergrößern");
		JMenuItem item2 = new JMenuItem("Schreibtisch löschen");
		
		
		item1.setIcon(new ImageIcon("icons/desktop-size.png"));
		item1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				desktop.callResizeDesktopDialog();
			}
		});

		
		item2.setIcon(new ImageIcon("icons/broom-desktop.png"));
		item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				net.reset();
				PlaceView.resetSize();
				TransitionView.resetSize();
				desktop.init();
			}
		});
		
		
		menu.add(item1);
		menu.add(item2);
		
		return menu;
	}

	private JMenu initStandardMenu() {

		JMenu menu = new JMenu("Bearbeiten");
		menu.setMnemonic('B');
		JMenuItem item1 = new JMenuItem("Markierung aufheben");
		JMenuItem item2 = new JMenuItem("Markierte Elemente löschen");
		JMenuItem item3 = new JMenuItem("Neuer Knoten");


		// add listeners
		item1.setIcon(new ImageIcon("icons/unselect.png"));
		item1.addActionListener(appControl);

		item2.setIcon(new ImageIcon("icons/remove-node.png"));
		item2.addActionListener(appControl);

		item3.setIcon(new ImageIcon("icons/add-node.png"));
		item3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				desktop.callNewNodeDialog(PTNAppController.DEFAULT_NODE_LOCATION);
			}
		});

		menu.add(item1);
		menu.add(item2);
		menu.add(item3);

		return menu;

	}

	private JMenu initFileMenu() {
		final PTNFileController fileListener = new PTNFileController(desktop,
				net);
		JMenu menu = new JMenu("Datei");
		menu.setMnemonic('D');
		JMenuItem item1 = new JMenuItem("Datei öffnen");
		JMenuItem item2 = new JMenuItem("Datei speichern");

		item1.setIcon(new ImageIcon("icons/open-file.png"));
		item1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileListener.readFromFile(net);
			}
		});

		item2.setIcon(new ImageIcon("icons/save-file.png"));
		item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileListener.writeToFile(net);
			}
		});

		menu.add(item1);
		menu.add(item2);

		return menu;

	}

	private JMenu initModusMenu() {

		ButtonGroup group = new ButtonGroup();
		JMenu menu = new JMenu("Modus");
		menu.setMnemonic('M');
		JMenuItem item1 = new JRadioButtonMenuItem("Editor");

		item1.setSelected(true);
		item1.setIcon(new ImageIcon("icons/editor-mode.png"));
		item1.addActionListener(appControl);
		JMenuItem item2 = new JRadioButtonMenuItem("Simulation");
		item2.setIcon(new ImageIcon("icons/sim-mode.png"));
		item2.addActionListener(appControl);

		group.add(item1);
		group.add(item2);
		menu.add(item1);
		menu.add(item2);

		return menu;

	}
	
	/**
	 * Makes the standard menu visible.
	 */
	public void switchOnDesktopMenu() {
		desktopMenu.setVisible(true);
	}

	/**
	 * Removes the standard menu. This way we can ensure that the net is not
	 * changed in an modus we do not want it to be manipulated.
	 */
	public void switchOffDesktopMenu() {
		desktopMenu.setVisible(false);
	}

	/**
	 * Makes the standard menu visible.
	 */
	public void switchOnNodeMenu() {
		nodeMenu.setVisible(true);
	}

	/**
	 * Removes the standard menu. This way we can ensure that the net is not
	 * changed in an modus we do not want it to be manipulated.
	 */
	public void switchOffNodeMenu() {
		nodeMenu.setVisible(false);
	}

	/**
	 * Makes the file menu visible.
	 */
	public void switchOnFileMenu() {
		fileMenu.setVisible(true);
	}

	/**
	 * Removes the file menu. This way we can ensure that the net is not changed
	 * by loading a new one in an modus we do not want it to be manipulated.
	 */
	public void switchOffFileMenu() {
		fileMenu.setVisible(false);
	}

	/**
	 * Makes the modus menu visible.
	 */
	public void switchOnModusMenu() {
		modusMenu.setVisible(true);
	}

	/**
	 * Turns off the modus menu.
	 */
	public void switchOffModusMenu() {
		modusMenu.setVisible(false);
	}

}
