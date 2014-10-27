package q8388415.brero_massimiliano.PTNetEditor.tests;

import static org.junit.Assert.*;

import java.awt.Dimension;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNPlace;

public class PTNNetTest {
	
	private PTNNet net;
	private PTNNode node1, node2;
	private PTNArc arc;

	@Before
	public void setUp() throws Exception {
		net = new PTNNet();
		node1 = new PTNPlace("node1", "n1", new Dimension(100, 100));
		node2 = new PTNPlace("node2", "n2", new Dimension(10, 10));
		arc = new PTNArc("a1", node1, node2);
		net.addNode(node1);
		net.addNode(node2);
		net.addArc(arc);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testGetNodeByIdHasNode() {
		assertNotNull(net.getNodeById(node1.getId()));
	}
	
	@Test
	public void testGetNodeByIdNotHasNode() {
		assertNull(net.getNodeById("huhu"));
	}
	
	@Test
	public void testGetArcById() {
		
	}


}
