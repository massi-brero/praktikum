package q8388415.brero_massimiliano.PTNetEditor.models;

import java.util.HashMap;

import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

public class PTNSimulationInterpreter {
	
	private PTNNet net;
	private PTNDesktop desktop;
	
	public PTNSimulationInterpreter(PTNDesktop desktop, PTNNet net) {
		this.net = net;
		this.desktop = desktop;
	}

	public void handleClick(TransitionView transitionView) {
		HashMap<String, PTNNode> precedingPlaces = net.getPredecessors(transitionView.getId());
		HashMap<String, PTNNode> succeedingPlaces = net.getSuccessors(transitionView.getId());
		
		this.takeTokenFromPlaces(precedingPlaces);
		this.addTokenToPlaces(succeedingPlaces);
		
	}
	
	private void takeTokenFromPlaces(HashMap<String, PTNNode> places){
		//update models and views
	}
	
	private void addTokenToPlaces(HashMap<String, PTNNode> places) {
		//update models and views
	}
	
	private void updateTransitions(HashMap<String, PTNNode> places) {
		//update just views
	}
	
	
	
	
}
