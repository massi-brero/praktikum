package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

public class PTNMenu extends JMenuBar {
	
	private JMenu standard;
	private PTNDesktop desktop;
	private PTNAppController appControl;
	public static final int HEIGHT = 20;
	public static final int WIDTH = 500;
	
	public PTNMenu(PTNDesktop desktop, PTNAppController appC) {
		
		this.desktop = desktop;
		this.appControl = appC;
		this.init();
		
	}
	
	private void init() {
		
		JMenu standard = this.initStandardMenu();
		JMenu file = this.initFileMenu();
		JMenu modus = this.initModusMenu();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		this.add(standard);
		this.add(file);
		this.add(modus);
		
	}

	private JMenu initStandardMenu() {
		
		JMenu menu = new JMenu("Bearbeiten");
		menu.setMnemonic('B');
		JMenuItem item1 = new JMenuItem("Markierung aufheben");
		JMenuItem item2 = new JMenuItem("Markierte Knoten l�schen");
		JMenuItem item3 = new JMenuItem("Neuer Knoten");
		
		//add listeners
		item1.addActionListener(appControl);

		item2.addActionListener(appControl);
		
		item3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				desktop.callNewNodeDialog();
			}
		});
		
		menu.add(item1);
		menu.add(item2);
		menu.add(item3);
		
		return menu;
		
	}
	
	private JMenu initFileMenu() {
		
		JMenu menu = new JMenu("Datei");
		menu.setMnemonic('D');
		JMenuItem item1 = new JMenuItem("Datei �ffnen");
		JMenuItem item2 = new JMenuItem("Datei speichern");
		
		menu.add(item1);
		menu.add(item2);
		
		return menu;
		
	}
	
	private JMenu initModusMenu() {
		
		JMenu menu = new JMenu("Modus");
		menu.setMnemonic('M');
		JMenuItem item1 = new JRadioButtonMenuItem("Editor Modus");
		item1.setSelected(true);
		JMenuItem item2 = new JRadioButtonMenuItem("Simulationsmodus");
		
		menu.add(item1);
		menu.add(item2);
		
		return menu;
		
	}

}
