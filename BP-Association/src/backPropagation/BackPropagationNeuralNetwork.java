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
	
	public static final double LEARNING_RATE = 0.5;
	public static final double ACCURACY = 0.01;

	//based on info said at SFC lecture, BP network should have one input, one hidden and one output layer
	final ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
	final ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
	final ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
	
	static ArrayList<double[]> trainingInput;
	static double[][] trainingOutput;
	static double[][] finalOutput;
	static double[] currentOutput;
	final int[] networksize;
	static double learningRate = LEARNING_RATE; //"mí" in greece alphabet
	final Neuron biasNeuron = new Neuron();
	
	public BackPropagationNeuralNetwork() {
		networksize = new int[] {INPUT_NEURONS, HIDDEN_NEURONS, OUTPUT_NEURONS};
		
		createNeuralNetwork();
		initializeWeights();
		trainingInput = DataHandler.loadTrainingData();
		trainingOutput = DataHandler.loadExpectedResults();
		finalOutput = DataHandler.init2DArray(DataHandler.NUMBER_OF_ELEMENTS_TO_ASSOCIATE);
	}
	
	/**
	 * Main program
	 */
	public void start(int stepLimit) {
		double networkError = 0;
		int steps = -1;
		
		do {
			for (int sampleIndex = 0; sampleIndex < trainingInput.size(); sampleIndex++) { //for all training samples
				double[] inputVector = trainingInput.get(sampleIndex);
				double[] expectedVector = trainingOutput[sampleIndex];

				initNeurons(inputVector);
				calculateOutput();
				currentOutput = getCurrentNetworkOutput();
				finalOutput[sampleIndex] = currentOutput;
				networkError = calculateNetworkError(networkError, expectedVector);
				//System.out.println("Network error: " + networkError);
				backPropagation(expectedVector);
			}
			steps++;
			
			if (steps % 10000 == 0) {
				System.out.println("Current step: " + steps);
				System.out.println("Network error: " + networkError);
			}
		} while (networkError > ACCURACY && stepLimit > steps);
		
		System.out.println("steps used: " + steps);
		for (int i = 0; i < trainingInput.size(); i++) {
			System.out.println("sample " + i + " : ");
			for (int j = 0; j < finalOutput[i].length; j++) {
				System.out.println("Final output: " + finalOutput[i][j]);
			} 
		}
		
		//printConnectionsInfo();
	}
	
	public void backPropagation(double[] expectedVector) {
		double lambda = 1;		//not sure yet what that is supposed to be set it 1 for now
		int i = 0;
		
		for (Neuron outputNeuron: outputLayer) {			//output layer
			ArrayList<Connection> inputConnections = outputNeuron.getInputConnections();
			for (Connection inputConnection: inputConnections) {
				double Yj = outputNeuron.getOutput();	//Yj represents output of neuron in last layer
				double Xi = inputConnection.leftNeuron.getOutput(); //Xi is input to this neuron which is also output of neuron from layer L-1
				double Dj = expectedVector[i];				//Dj is value which should be achieved
				
				double deltaWeight = learningRate * (Dj - Yj) * lambda * Yj * (1 - Yj) * Xi;
				double newWeight = inputConnection.getWeight() + deltaWeight;
				inputConnection.setWeight(newWeight);
			}
			i++;
		}
		
		for(Neuron hiddenNeuron: hiddenLayer) {			//hidden layer
			ArrayList<Connection> inputConnections = hiddenNeuron.getInputConnections();
			for (Connection inputConnection: inputConnections) {
				i = 0;
				double sum = 0;
				double Yj = hiddenNeuron.getOutput();
				double Xi = inputConnection.leftNeuron.getOutput();
				
				for (Neuron outputNeuron: outputLayer) {	//summ
					double Wkj = outputNeuron.getConnection(hiddenNeuron.getID()).getWeight();	//Wkj is weight of outcoming connection from hidden neuron
					double Dj = expectedVector[i];			//Dj is value which should be achieved
					double outputLayerYj = outputNeuron.getOutput();			//same as Yj but from +1 layer
					sum += (Dj - outputLayerYj) * outputLayerYj * (1 - outputLayerYj) * Wkj;
					i++;
				}
				
				double deltaWeight = learningRate * sum * Yj * (1 - Yj) * Xi;
				double newWeight = inputConnection.getWeight() + deltaWeight;
				inputConnection.setWeight(newWeight);
			}
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
	
	public double calculateNetworkError(double error, double[] expectedOutputVector) {
		double tmpError;
		for (int i = 0; i < trainingOutput[i].length; i++) {
			tmpError = Math.pow(currentOutput[i] - expectedOutputVector[i], 2);
			error += tmpError;
		}

		return error;
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
			ArrayList<Connection> inputConnections = hiddenNeuron.getInputConnections();
			for (Connection connection: inputConnections) {
				assignRandomWeight(connection);
			}
		}
	}
	
	public void initOutputLayerWeight() {
		for (Neuron outputNeuron: outputLayer) {
			ArrayList<Connection> inputConnections = outputNeuron.getInputConnections();
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
	
	public void printConnectionsInfo() {
		for(Neuron outputNeuron: outputLayer) {
			ArrayList<Connection> inputConnections = outputNeuron.getInputConnections();
			for (Connection inputConnection :inputConnections) {
				System.out.println("Neuron id: " + outputNeuron.getID() + " connected with neuron id: " + inputConnection.getLeftNeuron().getID()
						+ " connected with weight: " + inputConnection.getWeight());
			}
		}
		
		for(Neuron hiddenNeuron: hiddenLayer) {
			ArrayList<Connection> inputConnections = hiddenNeuron.getInputConnections();
			for (Connection inputConnection :inputConnections) {
				System.out.println("Neuron id: " + hiddenNeuron.getID() + " connected with neuron id: " + inputConnection.getLeftNeuron().getID()
						+ " connected with weight: " + inputConnection.getWeight());
			}
		}
	}
}
