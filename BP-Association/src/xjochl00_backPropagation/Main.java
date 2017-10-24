package xjochl00_backPropagation;

/**
 * 
 * @author Jakub Jochlik
 * xjochl00
 *
 */
public class Main {

	public static void main(String[] args) {
		int steps = DataHandler.setSteps(args);
		
		BackPropagationNeuralNetwork association = new BackPropagationNeuralNetwork();
		association.start(steps);
	}

}
