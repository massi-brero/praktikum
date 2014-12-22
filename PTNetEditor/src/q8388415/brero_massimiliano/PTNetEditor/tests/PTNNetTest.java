package q8388415.brero_massimiliano.PTNetEditor.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import q8388415.brero_massimiliano.PTNetEditor.models.PTNArc;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNet;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNPlace;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNTransition;

public class PTNNetTest {
	
	private PTNNet net;
	private PTNNode node1, node2, node3;
	private PTNArc arc1, arc2;

	@Before
	public void setUp() throws Exception {
		net = new PTNNet();
		node1 = new PTNPlace("node1", "n1", new Point(100, 100));
		node2 = new PTNTransition("node2", "n2", new Point(10, 10));
		node3 = new PTNTransition("node3", "n3", new Point(20, 20));
		arc1 = new PTNArc("a1", node1, node2);
		arc2 = new PTNArc("a2", node1, node3);
		net.addNode(node1);
		net.addNode(node2);
		net.addNode(node3);
		net.addArc(arc1);
		net.addArc(arc2);
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
	
	@Test
	public void testgetSuccessors() {
		
		HashMap<String, PTNNode> list = new HashMap<String, PTNNode>();
		list = net.getSuccessors(node1.getId());
		
		assertTrue(list.containsKey(new String("n2")) && list.containsKey("n3"));
		
	}
	
	@Test
	public void testgetPredecessor() {
		
		HashMap<String, PTNNode> list = new HashMap<String, PTNNode>();
		list = net.getPredecessors(node2.getId());
		
		assertTrue(list.containsKey(new String("n1")) && !list.containsKey("n3"));
		
	}
	
	
	@Test
	public void testGetNumberOfNodes(){
		assertEquals(3, net.getNumberOfNodes());
	}
	
	@Test
	public void testGetNumberOfArcs(){
		assertEquals(2, net.getNumberOfArcs());
	}
	
	@Test
	public void testIsCorrectPairTrue() {
		assertTrue(net.isCorrectPair(node1, node2));
	}
	
	@Test
	public void testIsCorrectPairFalse() {
		assertFalse(net.isCorrectPair(node2, node3));
	}
	
	@Test
	public void testgetArcsBySource() {
		assertFalse(net.isCorrectPair(node2, node3));
	}
	

	@Test
	public void testGetArcsBySource() {
		HashMap<String, PTNArc> result = net.getArcsBySource(node1);
		assertTrue(result.containsValue(arc1) && result.containsValue(arc2));
	}
	
	@Test
	public void testGetArcsByTarget() {
		HashMap<String, PTNArc> result = net.getArcsByTarget(node2);
		assertTrue(result.containsValue(arc1));
	}
	
	@Test
	public void testGetOutgoingArcs() {
		HashMap<String, PTNArc> result = net.getOutgoingArcs(node1);
		assertTrue(result.containsValue(arc1) && result.containsValue(arc2));
	}
	
	@Test
	public void testGetIncomingArcs() {
		HashMap<String, PTNArc> result = net.getIncomingArcs(node2);
		assertTrue(result.containsValue(arc1) && !result.containsValue(arc2));
	}
	
}
