package backPropagation;

import java.util.ArrayList;
import java.util.Random;

public class BackPropagationNeuralNetwork {
	public static final int INPUT_LAYER = 0;
	public static final int HIDDEN_LAYER = 1;
	public static final int OUTPUT_LAYER = 2;
	
	// Specifies number of neurons in each layer
	public static final int INPUT_NEURONS = 64;		//must be 64 because numbers are stored in 8x8 matrix	
	public static final int HIDDEN_NEURONS = 50;
	public static final int OUTPUT_NEURONS = 4;
	
	//interval to generate weights is <-0,5;0,5>
	public static final double LOWER_WEIGHT = -0.5;
	public static final double UPPER_WEIGHT = 0.5;

	//based on info said at SFC lecture, BP network should have one input, one hidden and one output layer
	final ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
	final ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
	final ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
	
	static ArrayList<double[]> trainingInput;
	static double[][] expectedOutput;
	static double[] currentOutput;
	final int[] networksize;
	final Neuron biasNeuron = new Neuron();
	
	public BackPropagationNeuralNetwork() {
		networksize = new int[] {INPUT_NEURONS, HIDDEN_NEURONS, OUTPUT_NEURONS};
		
		createNeuralNetwork();
		initializeWeights();
		trainingInput = DataHandler.loadTrainingData();
		expectedOutput = DataHandler.loadExpectedResults();
	}
	
	/**
	 * Main program
	 */
	public void start() {
		/*
		 *  for (dokud jsou vyorky tak){
		 *  	naucsit(param1-vzorek1, param2-vzorek2){
		 *  		
		 *  	}
		 *  }
		 */
		for (int i = 0; i < trainingInput.size(); i++) {			//for all training samples
			double[] inputVector = trainingInput.get(i);
			double[] expectedVector = expectedOutput[i];
			
			initNeurons(inputVector);
			calculateOutput();
			currentOutput = getCurrentNetworkOutput();
		}
	}
	
	/**
	 * This function takes array of doubles as input vector and applies each single value into each single neuron
	 */
	public void initNeurons(double [] inputSample) {
		for (int i = 0; i < inputLayer.size(); i++) {
			inputLayer.get(i).setOutput(inputSample[i]);
		}
	}
	
	/**
	 * ...each neuron has linear base functions and continuous activation function...
	 * first calculate hidden layer, then output layer
	 */
	public void calculateOutput() {
		calculateHiddenLayer();
		calculateOutputLayer();
	}
	
	/**
	 * @return output vector of current run
	 */
	public double[] getCurrentNetworkOutput() {
		double[] currentOutput = new double[outputLayer.size()];
		
		for (int i = 0; i < outputLayer.size(); i++)
			currentOutput[i] = outputLayer.get(i).getOutput();
		
		return currentOutput;
	}
	
	public void calculateHiddenLayer() {
		for (Neuron hiddenNeruon: hiddenLayer) {
			hiddenNeruon.calculateOutput();
		}
	}
	
	public void calculateOutputLayer() {
		for (Neuron outputNeuron: outputLayer) {
			outputNeuron.calculateOutput();
		}
	}
	
	/**
	 * Applies random weights onto connections between neurons
	 */
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
	
	/**
	 * create amout of neurons specified by predefined values and link them together - create network
	 */
	public void createNeuralNetwork() {
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
