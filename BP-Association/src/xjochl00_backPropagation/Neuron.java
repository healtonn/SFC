package xjochl00_backPropagation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Neuron {
	public ArrayList<Connection> inputConnections = new ArrayList<Connection>();	
	//this hashmap is used to get out-coming connections from hidden neuron to output neurons
	public Map<Integer, Connection> connectionMap = new HashMap<Integer, Connection>();
	private Connection biasConnection;			//this doesn't need to be inside the connection map
	private final int neuronID;
	private double output;
	
	protected static final double bias = 1.0;       	//creating its own subclass didn't work well, so just use static variable
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
	
	/**
	 * calculate U = U + Wji * Xj(+ bias --- also bias might be needed?)
	 */
	public void calculateOutput() {
		double newOutput = 0;
		
		for (Connection inputConnection: inputConnections)
			newOutput += inputConnection.getLeftNeuron().getOutput() * inputConnection.getWeight();
		
		newOutput += biasConnection.getWeight() * bias;
		output = calculateSigmoid(newOutput);
	}
	
	public double calculateSigmoid(double input) {
		return 1.0 / (1.0 + (Math.exp(-input)));
	}
	
	public int getID() {
		return neuronID;
	}
	
	public ArrayList<Connection> getInputConnections(){
		return inputConnections;
	}
	
	public Connection getConnection(int neuronID) {
		return connectionMap.get(neuronID);
	}
	
	public void setOutput(double newOutput) {
		output = newOutput;
	}
	
	public double getOutput() {
		return output;
	}
	
	public void connectBiasNeuron(Neuron biasNeuron) {
		Connection biasConnection = new Connection(biasNeuron, this);
		inputConnections.add(biasConnection);
		this.biasConnection = biasConnection;
	}
}
