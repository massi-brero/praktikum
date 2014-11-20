package q8388415.brero_massimiliano.PTNetEditor;

import java.io.File;

import javax.swing.SwingUtilities;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNFileController;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.utils.PTNParser;
import q8388415.brero_massimiliano.PTNetEditor.views.PTNMenu;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNControlPanel;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;
import q8388415.brero_massimiliano.PTNetEditor.views.windows.MainFrame;

public class PTNBootstrap {

    private static PTNBootstrap bootstrap;
    private PTNParser parser;
    private File sourceFile;
    private double desktopWidth;
    private double desktopHeight;

    public PTNBootstrap() {
    }

    public static void main(String[] args) {

        bootstrap = new PTNBootstrap();
        bootstrap.init();

    }

    /**
     * Sets up the application, instantiates all needed controllers and the desktop.
     */
    public void init() {

        sourceFile = new File("src\\snippet\\Kaffee.pnml");
        
        /**
         * Here the net is instantiates which basically all of the components will use.
         */
        PTNNet net = new PTNNet();
        /**
         * Initialize controllers for drawing and basic control operations.
         */
        PTNAppController appControl = new PTNAppController();
        final PTNDesktop desktop = new PTNDesktop(appControl, net);
        final PTNControlPanel controlPanel = PTNControlPanel.getInstance();
        controlPanel.initialize(desktop, appControl);
        final PTNMenu menu = new PTNMenu(desktop, appControl, net);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame(desktop, controlPanel, menu);
            }
        });

    }

    /**
     * 
     * @return net
     *      Net model parsed from file.
     */
    private PTNNet setUpNet() {

        PTNNet net = new PTNNet();
        
        try {
            parser = new PTNParser(sourceFile, net);
            parser.initParser();
            parser.parse();
            
            desktopWidth = parser.getMaxWidth();
            desktopHeight = parser.getMaxWidth();
            
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        try {
//            // PTNPlace node1 = new PTNPlace("node1", "n1", new Point(100,
//            // 100));
//            // node1.setName("node1 node1 node1 node1");
//            // PTNTransition node2 = new PTNTransition("node2", "n2", new
//            // Point(10, 10));
//            // PTNTransition node3 = new PTNTransition("node3", "n3", new
//            // Point(200, 250));
//            // PTNTransition node4 = new PTNTransition("node4", "n4", new
//            // Point(200, 70));
//            // node2.setName("node2");
//            // node3.setName("node3");
//            // node4.setName("node4");
//            // PTNArc arc1 = new PTNArc("a1", node1, node2);
//            // PTNArc arc2 = new PTNArc("a2", node1, node3);
//            // net.addNode(node1);
//            // net.addNode(node2);
//            // net.addNode(node3);
//            // net.addNode(node4);
//            // net.addArc(arc1);
//            // net.addArc(arc2);
//
//        } catch (Exception e) {
//            // TODO Fehler-Dialog Fenster aufrufen
//        }

        return net;

    }

}
