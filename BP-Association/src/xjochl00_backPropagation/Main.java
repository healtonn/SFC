package xjochl00_backPropagation;

/**
 * 
 * @author Jakub Jochlik
 * xjochl00
 *
 */
public class Main {

	public static void main(String[] args) {
		int steps = 40000;
		BackPropagationNeuralNetwork association = new BackPropagationNeuralNetwork();
		association.start(steps);
	}

}
