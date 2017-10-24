package xjochl00_backPropagation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataHandler {
	public static final int NUMBER_OF_ELEMENTS_TO_ASSOCIATE = 10;
	public static final int NUMBER_OF_STEPS = 40000;
	
	/*
	 * 4 neurons in output layer will be presenting bit-like results representing associated numbers
	 */
	private static double[][] expectedResults = {
			{0.0, 0.0, 0.0, 0.0},
			{0.0, 0.0, 0.0, 1.0},
			{0.0, 0.0, 1.0, 0.0},
			{0.0, 0.0, 1.0, 1.0},
			{0.0, 1.0, 0.0, 0.0},
			{0.0, 1.0, 0.0, 1.0},
			{0.0, 1.0, 1.0, 0.0},
			{0.0, 1.0, 1.0, 1.0},
			{1.0, 0.0, 0.0, 0.0},
			{1.0, 0.0, 0.0, 1.0}
	};
	
	public static final int DATA_MATRIX_SIZE = 64;

	public static double[][] loadExpectedResults() {
		return expectedResults;
	}
	
	public static ArrayList<double[]> loadTrainingData() throws FileNotFoundException{
		ArrayList<double[]> trainingData = new ArrayList<double[]>();
		
		trainingData.add(loadzero());
		trainingData.add(loadone());
		trainingData.add(loadtwo());
		trainingData.add(loadthree());
		trainingData.add(loadfour());
		trainingData.add(loadfive());
		trainingData.add(loadsix());
		trainingData.add(loadseven());
		trainingData.add(loadeight());
		trainingData.add(loadnine());
		
		return trainingData;
	}
	
	public static double[] loadDataFromFile(String file) throws FileNotFoundException {
		double[] data = new double[DATA_MATRIX_SIZE];
		int index = 0;
		int number;
		double pixelValue = 0.0;
		
		FileReader inputFile = null;
		
		try {
			inputFile = new FileReader(file);
			while((number = inputFile.read()) != -1) {
				if (number == '0' || number == '1') {
					if (number == '0')
						pixelValue = 0.0;
					else if (number == '1')
						pixelValue = 1.0;
					if (index < DATA_MATRIX_SIZE) {
						data[index] = pixelValue;
						index++;
					} 
				}
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if(inputFile != null) {
				try {
					inputFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return data;
	}
	
	public static double[][] init2DArray(int arraySize){
		double[][] array = new double[arraySize][];
		for (int i = 0; i < arraySize; i++) {
			array[i] = new double[] {0.0};
		}
		
		return array;
	}
	
	public static double[] loadzero() throws FileNotFoundException {
		double[] zero = loadDataFromFile("data/learning samples/0.txt");
		return zero;
	}
	
	public static double[] loadone() throws FileNotFoundException {
		double[] zero = loadDataFromFile("data/learning samples/1.txt");
		return zero;
	}
	
	public static double[] loadtwo() throws FileNotFoundException {
		double[] zero = loadDataFromFile("data/learning samples/2.txt");
		return zero;
	}
	
	public static double[] loadthree() throws FileNotFoundException {
		double[] zero = loadDataFromFile("data/learning samples/3.txt");
		return zero;
	}
	
	public static double[] loadfour() throws FileNotFoundException {
		double[] zero = loadDataFromFile("data/learning samples/4.txt");
		return zero;
	}
	
	public static double[] loadfive() throws FileNotFoundException {
		double[] zero = loadDataFromFile("data/learning samples/5.txt");
		return zero;
	}
	
	public static double[] loadsix() throws FileNotFoundException {
		double[] zero = loadDataFromFile("data/learning samples/6.txt");
		return zero;
	}
	
	public static double[] loadseven() throws FileNotFoundException {
		double[] zero = loadDataFromFile("data/learning samples/7.txt");
		return zero;
	}
	
	public static double[] loadeight() throws FileNotFoundException {
		double[] zero = loadDataFromFile("data/learning samples/8.txt");
		return zero;
	}
	
	public static double[] loadnine() throws FileNotFoundException {
		double[] zero = loadDataFromFile("data/learning samples/9.txt");
		return zero;
	}
	
	public static int setSteps(String[] argv) {
		int steps = 0;
		
		if(argv.length == 0)
			return NUMBER_OF_STEPS;
		
		try {
			steps = Math.abs(Integer.parseInt(argv[0]));
		}
		catch(NumberFormatException e){
			System.out.println("This is not valid argument. Number must be positive integer. Using default value...");
			steps = NUMBER_OF_STEPS;
		}
		
		System.out.println("pocet kroku: " + argv[0]);
		
		System.out.println("test: " + steps);
		return steps;
	}
}
