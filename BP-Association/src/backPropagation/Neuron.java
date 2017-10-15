package backPropagation;

public class Neuron {
	static int numberOfNeurons = 0;
	final int neuronID;
	
	public Neuron() {
		this.neuronID = numberOfNeurons;
		numberOfNeurons++;
	}
}
