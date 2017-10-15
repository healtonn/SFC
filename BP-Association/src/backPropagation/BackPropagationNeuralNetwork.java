package backPropagation;

import java.util.ArrayList;

public class BackPropagationNeuralNetwork {
	public static final int INPUT_LAYER = 0;
	public static final int HIDDEN_LAYER = 1;
	public static final int OUTPUT_LAYER = 2;
	
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
	
}
