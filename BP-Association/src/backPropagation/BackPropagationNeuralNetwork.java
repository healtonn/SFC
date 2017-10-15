package backPropagation;

import java.util.ArrayList;
import java.util.Random;

public class BackPropagationNeuralNetwork {
	public static final int INPUT_LAYER = 0;
	public static final int HIDDEN_LAYER = 1;
	public static final int OUTPUT_LAYER = 2;
	
	public static final double LOWER_WEIGHT = -0.5;
	public static final double UPPER_WEIGHT = 0.5;
	
	/**
	 * based on info said at SFC lecture, BP network should have one input, one hidden and one output layer
	 */
	final ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
	final ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
	final ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
	
	final int[] networksize;

	final Neuron biasNeuron = new Neuron();
	
	public BackPropagationNeuralNetwork(int inputNeurons, int hiddenNeurons, int outputNeurons) {
		networksize = new int[] {inputNeurons, hiddenNeurons, outputNeurons};
		
		createNeuralNetwork();
		initializeWeights();
		
	}
	
	public void initializeWeights() {
		initHiddenLayerWeight();
		initOutputLayerWeight();
	}
	
	public void initHiddenLayerWeight() {
		for (Neuron hiddenNeuron: hiddenLayer) {
			ArrayList<Connection> inputConnections = hiddenNeuron.getConnections();
			for (Connection connection: inputConnections) {
				assignRandomWeight(connection);
			}
		}
	}
	
	public void initOutputLayerWeight() {
		for (Neuron outputNeuron: outputLayer) {
			ArrayList<Connection> inputConnections = outputNeuron.getConnections();
			for (Connection connection: inputConnections) {
				assignRandomWeight(connection);
			}
		}
	}
	
	public void createNeuralNetwork() {
		System.out.println("rozmery site jsou: " + networksize[0] + "," + networksize[1] + "," + networksize[2]);
		createInputLayer();
		createHiddenLayer();
		createOutputLayer();
	}
	
	public void createInputLayer() {
		for (int i = 0; i < networksize[INPUT_LAYER]; i++) {
			Neuron inputNeuron = new Neuron();
			inputLayer.add(inputNeuron);
		}
	}
	
	public void createHiddenLayer() {
		for (int i = 0; i < networksize[HIDDEN_LAYER]; i++) {
			Neuron hiddenNeuron = new Neuron();
			hiddenNeuron.connectToInputLayer(inputLayer);
			hiddenNeuron.connectBiasNeuron(biasNeuron);
			hiddenLayer.add(hiddenNeuron);
		}
	}
	
	public void createOutputLayer() {
		for (int i = 0; i < networksize[OUTPUT_LAYER]; i++) {
			Neuron outputNeuron = new Neuron();
			outputNeuron.connectToInputLayer(hiddenLayer);
			outputNeuron.connectBiasNeuron(biasNeuron);
			outputLayer.add(outputNeuron);
		}
	}
	
	public void assignRandomWeight(Connection connection) {
		double weight = generateWeight();
		connection.setWeight(weight);
	}
	
	public double generateWeight() {
		Random random = new Random();
		return LOWER_WEIGHT + (UPPER_WEIGHT - LOWER_WEIGHT) * random.nextDouble();
	}
	
}
