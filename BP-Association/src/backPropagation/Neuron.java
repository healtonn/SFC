package backPropagation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Neuron {
	private ArrayList<Connection> inputConnections = new ArrayList<Connection>();
	private Map<Integer, Connection> connectionMap = new HashMap<Integer, Connection>();
	private Connection biasConnection;			//this doesn't need to be inside the connection map
	private final int neuronID;
	
	protected static final double bias = 1.0;       	//creating its own subclass didn't work well, so just use variable
	protected static int numberOfNeurons = 0;
	
	public Neuron() {
		neuronID = numberOfNeurons;
		numberOfNeurons++;
	}
	
	/*
	 * connect to previous layer of neurons witch is used as "input layer" to current neuron
	 */
	public void connectToInputLayer(ArrayList<Neuron> inputLayer) {
		for(Neuron inputNeuron: inputLayer) {
			Connection connection = new Connection(inputNeuron, this);
			inputConnections.add(connection);
			connectionMap.put(inputNeuron.neuronID, connection);
		}
	}
	
	public int getID() {
		return neuronID;
	}
	
	public void connectBiasNeuron(Neuron biasNeuron) {
		Connection biasConnection = new Connection(biasNeuron, this);
		inputConnections.add(biasConnection);
		this.biasConnection = biasConnection;
	}
	
	public ArrayList<Connection> getConnections() {
		return inputConnections;
	}
}
