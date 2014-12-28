package q8388415.brero_massimiliano.PTNetEditor.models;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNAppController;
import q8388415.brero_massimiliano.PTNetEditor.exceptions.PTNSimulationException;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNINodeDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNNodeTypes;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNTokenOperations;
import q8388415.brero_massimiliano.PTNetEditor.utils.PTNNodeHelper;
import q8388415.brero_massimiliano.PTNetEditor.views.PlaceView;
import q8388415.brero_massimiliano.PTNetEditor.views.TransitionView;
import q8388415.brero_massimiliano.PTNetEditor.views.desktop.PTNDesktop;

public class PTNSimulationInterpreter {

	protected PTNNet net;
	protected PTNDesktop desktop;
	protected Boolean simError = false;
	protected PTNNodeHelper nodeHelper; 

	public PTNSimulationInterpreter(PTNDesktop desktop, PTNNet net) {
		this.net = net;
		this.desktop = desktop;
	}

	/**
	 * Starts simulation process after a transition was clicked in simulation
	 * mode. If this method encounters an {@link PTNSimulationException} it will
	 * reset the node tokens their their initial state and the exception; this
	 * way the net is kept in an consistent state.
	 * After a simulation state the affected transitions are updated.
	 * 
	 * @param transitionView
	 * @throws PTNSimulationException
	 */
	public void handleClick(TransitionView transitionView) throws PTNSimulationException {
		nodeHelper = new PTNNodeHelper(desktop, net);
		HashMap<String, PTNNode> precedingPlaces = net.getPredecessors(transitionView.getId());
		HashMap<String, PTNNode> succeedingPlaces = net.getSuccessors(transitionView.getId());

		/**
		 * The variable affectedPlaces contains all places whose token number is affected by this 
		 * simulation step. It is then used to store that state before changing the value due to an activated transition.
		 * This way we can restore the bet state if an inconsistent situation is 
		 */
		HashMap<String, PTNNode> affectedPlaces = new HashMap<String, PTNNode>(precedingPlaces);
		affectedPlaces.putAll(succeedingPlaces);
		HashMap<String, PTNINodeDTO> backUpPlacesInformation = this.backupNodeStates((HashMap<String, PTNNode>) affectedPlaces);

		try {
			if (!simError) {
				this.updateTokenOfPlaces(precedingPlaces, PTNTokenOperations.DECREMENT);
				this.updateTokenOfPlaces(succeedingPlaces, PTNTokenOperations.INCREMENT);
				this.updateTransitions(succeedingPlaces, transitionView);			
				this.updateTransitions(precedingPlaces, transitionView);
				
			}
		} catch (PTNSimulationException e) {
			simError = false;
			this.resetPlaceTokens(backUpPlacesInformation, affectedPlaces);
			// reset error state and throw error
			throw new PTNSimulationException(e.getMessage());
		}
	}

	/**
	 * Changes the number of tokens in a place depending on the given operation.
	 * If we have more operations on node tokens like we would with weighted
	 * arcs, one will have to update PTNTokenOperations and this method.
	 * 
	 * @see PTNTokenOperations
	 * @param places
	 * 		Either preceding places which leads to an decrement of their tokens or
	 * 		succeeding places whose tokens will be incremented.
	 * @param operation {@link PTNTokenOperations}
	 * 		INCREMENT or DECREMENT
	 * @throws PTNSimulationException
	 */
	private void updateTokenOfPlaces(HashMap<String, PTNNode> places, PTNTokenOperations operation) 
			throws PTNSimulationException {
		Iterator<Map.Entry<String, PTNNode>> it = places.entrySet().iterator();

		while (it.hasNext()) {

			PTNNode node = it.next().getValue();

			// We check if we really have a place now... just in case...
			if (PTNNodeTypes.STELLE == node.getType()) {
				int token = ((PTNPlace) node).getToken();

				switch (operation) {
				case INCREMENT:
					if (token < PTNAppController.MAX_TOKEN) {
						simError = false;
						((PTNPlace) node).setToken(++token);
						((PlaceView) desktop.getNodeViewById(node.getId())).updateToken(token);
					} else {
						simError = true;
						throw new PTNSimulationException(
								"Dieser Simulationsschritt ist wg. Überschreitung der maximalen Token-Zahl nicht möglich");
					}
					break;
				case DECREMENT:
					if (token > 0) {
						simError = false;
						((PTNPlace) node).setToken(--token);
						((PlaceView) desktop.getNodeViewById(node.getId())).updateToken(token);
					} else {
						/**
						 * This case should be already be inhibited by the controller.
						 * If it's not a PTNSimulationException is thrown.
						 */
						simError = true;
						throw new PTNSimulationException("Dieser Simulationsschritt ist nicht möglich");
					}
					break;
				default:
					break;
				}

				desktop.repaint();

			}

		}

	}

	/**
	 * Updates transition that was clicked on an all transitions adjacent to its
	 * succeeding places.
	 * 
	 * @param places
	 * @param transition
	 */
	private void updateTransitions(HashMap<String, PTNNode> places, TransitionView transitionView) {
		/**
		 * If everything went well the transitions' activation states are updated.
		 * We start with the transition that was clicked on.
		 */
		nodeHelper.updateTransitionState(transitionView);
		Iterator<Map.Entry<String, PTNNode>> it = places.entrySet().iterator();
		
		while (it.hasNext()) {
			PTNNode node = (PTNNode)it.next().getValue();
			
			if(null != node && PTNNodeTypes.STELLE == node.getType()) {
				nodeHelper.updateAdjacentTransitionsState(node);
			}
			
		}

	}

	/**
	 * Resets the place tokens after a simulation error occurred
	 * 
	 * @param backUpPrecedingPlaces
	 * @param backUpSucceedingPlaces
	 */
	private void resetPlaceTokens(HashMap<String, PTNINodeDTO> backUpPlacesInformation, HashMap<String, PTNNode> affectedPlaces) {
		PTNINodeDTO nodeInformation = null;
		PTNNode affectedPlaceModel = null;
		Iterator<Map.Entry<String, PTNNode>> it = affectedPlaces.entrySet().iterator();

		while (it.hasNext()) {
			affectedPlaceModel = (PTNNode)it.next().getValue();
			nodeInformation = backUpPlacesInformation.get(affectedPlaceModel.getId());

			if (null != nodeInformation && PTNNodeTypes.STELLE == nodeInformation.getType()) {
				int oldToken = nodeInformation.getToken();
				// reset place model and view
				((PTNPlace)affectedPlaceModel).setToken(oldToken);
				((PlaceView) desktop.getNodeViewById(affectedPlaceModel.getId())).updateToken(oldToken);
			}
		}

		desktop.repaint();

	}


	/**
	 * This method provides a deep clone copy as a PTNINode hash map for a
	 * places has map. This way we can store old node states.
	 * 
	 * @param map
	 *            HashMap<String, PTNNode>
	 * @return HashMap<String, PTNINodeDTO>
	 */
	private HashMap<String, PTNINodeDTO> backupNodeStates(HashMap<String, PTNNode> map) {

		PTNINodeDTO backupEntry = null;
		HashMap<String, PTNINodeDTO> cloneMap = new HashMap<String, PTNINodeDTO>();
		Iterator<Map.Entry<String, PTNNode>> it = map.entrySet().iterator();

		while (it.hasNext()) {
			final Map.Entry<String, PTNNode> nodeEntry = (Map.Entry<String, PTNNode>) it.next();

			if (PTNNodeTypes.STELLE == nodeEntry.getValue().getType()) {

				backupEntry = new PTNINodeDTO() {
					
					final private PTNNodeTypes type = nodeEntry.getValue().getType();
					final private int token = nodeEntry.getValue().getToken();
					final private String name = nodeEntry.getValue().getName();
					final private Point location = nodeEntry.getValue().getLocation();
					final private String id = nodeEntry.getValue().getId();

					@Override
					public PTNNodeTypes getType() {
						return type;
					}

					@Override
					public Integer getToken() {
						return token;
					}

					@Override
					public String getNodeName() {
						return name;
					}

					@Override
					public Point getLocation() {
						return location;
					}

					@Override
					public String getId() {
						return id;
					}
				};
			}
			
			cloneMap.put(backupEntry.getId(), backupEntry);

		}

		return cloneMap;
	}

}
