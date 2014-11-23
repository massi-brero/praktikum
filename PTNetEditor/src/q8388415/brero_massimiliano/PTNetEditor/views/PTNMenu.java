package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNFileController;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

public class PTNMenu extends JMenuBar {

    private JMenu standard;
    private PTNDesktop desktop;
    private PTNNet net;
    private PTNAppController appControl;
    private File readFile = new File("src\\snippet\\Kaffee.pnml");
    public static final int HEIGHT = 20;
    public static final int WIDTH = 500;

    public PTNMenu(PTNDesktop desktop, PTNAppController appC, PTNNet net) {

        this.desktop = desktop;
        this.net = net;
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
        JMenuItem item2 = new JMenuItem("Markierte Knoten löschen");
        JMenuItem item3 = new JMenuItem("Neuer Knoten");
        JMenuItem item4 = new JMenuItem("Desktop löschen");

        // add listeners
        item1.addActionListener(appControl);

        item2.addActionListener(appControl);

        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desktop.callNewNodeDialog();
            }
        });
        
        item4.addActionListener(new ActionListener() {
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
        menu.add(item3);
        menu.addSeparator();
        menu.add(item4);

        return menu;

    }

    private JMenu initFileMenu() {
        final PTNFileController fileListener = new PTNFileController(desktop, net);
        JMenu menu = new JMenu("Datei");
        menu.setMnemonic('D');
        JMenuItem item1 = new JMenuItem("Datei öffnen");
        JMenuItem item2 = new JMenuItem("Datei speichern");

        item1.setIcon(new ImageIcon("icons/open-document.png"));
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println((fileListener));
                fileListener.readFromFile(net);
            }
        });

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
