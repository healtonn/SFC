package backPropagation;

public class Main {

	public static void main(String[] args) {
		int steps = 50000;
		BackPropagationNeuralNetwork association = new BackPropagationNeuralNetwork();
		association.start(steps);
	}

}
