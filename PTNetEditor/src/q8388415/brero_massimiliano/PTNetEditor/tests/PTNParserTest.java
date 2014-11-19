package q8388415.brero_massimiliano.PTNetEditor.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNPlace;
import q8388415.brero_massimiliano.PTNetEditor.utils.PTNParser;

public class PTNParserTest {

    private PTNNet net;
    private String testFilePath = "src\\snippet\\Beispiel1.pnml";
    private PTNParser parser;

    @Before
    public void setUp() throws Exception {
        net = new PTNNet();
        File pnm = new File(testFilePath);
        parser = new PTNParser(pnm, net);
        parser.initParser();
        parser.parse();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testReadingNodes1() {
        assertEquals(3, net.getNodes().size());
    }

    @Test
    public void testNewPlace() {
        assertNotNull(net.getNodeById("place1"));
    }

    @Test
    public void testNewTransition() {
        assertNotNull(net.getNodeById("transition1"));
    }

    @Test
    public void testSetPosition() {
        Point pos = new Point(400, 250);
        assertTrue(net.getNodeById("place1").getLocation().equals(pos));
    }

    @Test
    public void testSetMarking() {
        PTNPlace place = (PTNPlace) net.getNodeById("place1");
        assertEquals(0, place.getToken());
    }

    @Test
    public void testSetName() {
        assertEquals("a", net.getNodeById("transition1").getName());
    }
    
    @Test
    public void testHandleParsingFinish_1() {
        assertEquals(2, net.getArcs().size());
    }
    
    @Test
    public void testHandleParsingFinish_2() {
        assertNotNull(net.getArcById("arc1"));
    }
    
    @Test
    public void testHandleParsingFinish_3() {
        assertNotNull(net.getArcsBySource(net.getNodeById("place1")));
    }
    
    

}
