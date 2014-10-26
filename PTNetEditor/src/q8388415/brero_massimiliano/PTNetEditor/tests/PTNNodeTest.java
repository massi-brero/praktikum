package q8388415.brero_massimiliano.PTNetEditor.tests;

import java.awt.Dimension;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeContructionException;
import q8388415.brero_massimiliano.PTNetEditor.models.PTNNode;
import q8388415.brero_massimiliano.PTNetEditor.models.Place;

public class PTNNodeTest {
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	private PTNNode nodeModel;

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testConstructionError() throws PTNNodeContructionException {
		
		thrown.expect(PTNNodeContructionException.class);
		PTNNode nodeModel = new Place("test", "", new Dimension(0, 0));
		
	}

}