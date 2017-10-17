package backPropagation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataHandler {
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
	
	public static ArrayList<double[]> loadTrainingData(){
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
		
		for(double[]pokus : trainingData) {
			System.out.println("cislo:");
			for (int i = 0; i < DATA_MATRIX_SIZE; i++) {
				System.out.print(pokus[i] + ",");
				if (i % 8 == 7)
					System.out.println();
			}
		}
		
		return trainingData;
	}
	
	public static double[] loadDataFromFile(String file) {
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
			e.printStackTrace();
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
	
	public static double[] loadzero() {
		double[] zero = loadDataFromFile("data/0.txt");
		return zero;
	}
	
	public static double[] loadone() {
		double[] zero = loadDataFromFile("data/1.txt");
		return zero;
	}
	
	public static double[] loadtwo() {
		double[] zero = loadDataFromFile("data/2.txt");
		return zero;
	}
	
	public static double[] loadthree() {
		double[] zero = loadDataFromFile("data/3.txt");
		return zero;
	}
	
	public static double[] loadfour() {
		double[] zero = loadDataFromFile("data/4.txt");
		return zero;
	}
	
	public static double[] loadfive() {
		double[] zero = loadDataFromFile("data/5.txt");
		return zero;
	}
	
	public static double[] loadsix() {
		double[] zero = loadDataFromFile("data/6.txt");
		return zero;
	}
	
	public static double[] loadseven() {
		double[] zero = loadDataFromFile("data/7.txt");
		return zero;
	}
	
	public static double[] loadeight() {
		double[] zero = loadDataFromFile("data/8.txt");
		return zero;
	}
	
	public static double[] loadnine() {
		double[] zero = loadDataFromFile("data/9.txt");
		return zero;
	}
}
