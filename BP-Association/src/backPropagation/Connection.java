package backPropagation;

public class Connection {
	final Neuron leftNeuron;
	final Neuron rightNeuron;
	
	public Connection(Neuron leftNeuron, Neuron rightNeuron) {
		this.leftNeuron = leftNeuron;
		this.rightNeuron = rightNeuron;
	}

}
