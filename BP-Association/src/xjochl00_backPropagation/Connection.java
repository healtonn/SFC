package xjochl00_backPropagation;

public class Connection {
	final Neuron leftNeuron;
	final Neuron rightNeuron;
	
	double weight;
	
	public Connection(Neuron leftNeuron, Neuron rightNeuron) {
		this.leftNeuron = leftNeuron;
		this.rightNeuron = rightNeuron;
		weight = 0;			//init weight to 0 for now
	}
	
	public void setWeight(double newWeight) {
		weight = newWeight;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public Neuron getLeftNeuron() {
		return leftNeuron;
	}

}
