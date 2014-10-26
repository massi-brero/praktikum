package q8388415.brero_massimiliano.PTNetEditor.models;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNNodeContructionException;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;

public abstract class PTNNode {
	
	private String name;
	private String id;
	private Dimension position;
	private PTNNodeTypes type;
	
	public PTNNode(String name, String id, Dimension pos) throws PTNNodeContructionException{
		System.out.println(id);
			if ("" == id) {
				
				throw new PTNNodeContructionException("Vital information for this node is missing (id)!");
			} else {
				this.setName(name);
				this.setId(id);
				this.setPosition(pos);
			}

		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Dimension getPosition() {
		return position;
	}

	public void setPosition(Dimension position) {
		this.position = position;
	}

	public PTNNodeTypes getType() {
		return type;
	}

	public void setType(PTNNodeTypes type) {
		this.type = type;
	}
	
	
	
}
