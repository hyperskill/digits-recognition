package digrec.stages.stage5;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

/**
 * @version 4.5
 *
 */
public class NeuronNet implements Serializable {

	private static final long serialVersionUID = 328778L;
	private final int LAYERS;
	private final int [] NEURONS_IN_LAYERS;
	private double [][][] weights;
	public double [][][] deltaW;	
	public double [][][] idealNeurons; 	// non-rectangle matrix!
	public double [][] inputNumbers;
	public int iInLength;
	private double [][] neurons;	
	
	
	
	public NeuronNet() {
		NEURONS_IN_LAYERS = new int[]{15,10};
		LAYERS = NEURONS_IN_LAYERS.length;
		weights = new double [LAYERS-1][][];
		deltaW = new double [LAYERS-1][][];
		Random rd = new Random(serialVersionUID);
		for (int l=0;l<(LAYERS-1);l++) {
			weights[l] = new double[NEURONS_IN_LAYERS[l+1]][];
			deltaW[l] = new double[NEURONS_IN_LAYERS[l+1]][];
			for (int i=0;i<NEURONS_IN_LAYERS[l+1];i++) {
				weights[l][i] = new double[NEURONS_IN_LAYERS[l]+1];
				deltaW[l][i] = new double[NEURONS_IN_LAYERS[l]+1];
				for (int j = 0; j<NEURONS_IN_LAYERS[l]+1;j++){
					weights[l][i][j]= rd.nextGaussian();
				}
			}
		}
		
	}
	
	public NeuronNet(int... neuronsInLayers) {
		LAYERS = neuronsInLayers.length;
		NEURONS_IN_LAYERS = neuronsInLayers.clone();
		weights = new double [LAYERS-1][][];
		deltaW = new double [LAYERS-1][][];
		idealNeurons = new double [10][LAYERS-1][]; 					// non-rectangle matrix!
		neurons = new double[2][];
		Random rd = new Random(328778L);
		for (int l=0;l<(LAYERS-1);l++) {
			weights[l] = new double[NEURONS_IN_LAYERS[l+1]][];
			deltaW[l] = new double[NEURONS_IN_LAYERS[l+1]][];
			for (int n=0;n<10;n++) {
				idealNeurons[n][l] = new double [NEURONS_IN_LAYERS[l+1]];
			}
			for (int i=0;i<NEURONS_IN_LAYERS[l+1];i++) {
				weights[l][i] = new double[NEURONS_IN_LAYERS[l]+1];
				deltaW[l][i] = new double[NEURONS_IN_LAYERS[l]+1];
				for (int j = 0; j<NEURONS_IN_LAYERS[l]+1;j++){
					weights[l][i][j]= rd.nextGaussian();
				}
			}
		}
	}

	
	/**
	 * @return	double [][][] new array, don't return reference
	 */
	public double[][][] getWeights() {
		double[][][] wts = new double [LAYERS-1][][];
		int l;
		for(int i = 0;i<LAYERS-1;i++) {
			l = weights[i].length;
			wts[i] = new double [l][];
			for(int j = 0;j<l;j++) {
				wts[i][j] = weights[i][j].clone();
			}
		}
		return wts;
	}

	/**
	 *	Method don't change the array passed as an argument.
	 * @param weights double [][][] array isn't changing in method 
	 */
	public void setWeights(double[][][] weights) {
		int l;
		for(int i = 0;i<LAYERS-1;i++) {
			l = weights[i].length;
			this.weights[i]= new double [l][];
			for(int j = 0;j<l;j++) {
				this.weights[i][j] = weights[i][j].clone();
			}
		}
	}
	
	public void saveToF() {
		 
	    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("nnw5.bin"))) {
			out.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	   // System.out.println("Saved successfully.");
	}
	
	public void loadFromF() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("nnw5.bin"))) {
			this.setWeights(((NeuronNet)in.readObject()).getWeights());
						
		} catch (ClassNotFoundException|IOException e) {
			e.printStackTrace();
		}
		//System.out.println("Loaded successfully.");
	}
	
	public void loadInputNumbers(int countOfSamplesOfANumber, int startIndex) {
		Assets as = new Assets();										// load input numbers
		as = as.loadFromF();
		inputNumbers = as.createTraningSet(countOfSamplesOfANumber, startIndex);
		iInLength = inputNumbers.length;
	}
	
	public void learnNeuronNet(double eta) {
		 
		int idealNumber;
		countIdealNeurons();
		
		
		for(int n =0;n<iInLength;n++) {									// input numbers
			idealNumber = takeInNeurons(inputNumbers[n]);
			for (int l=0;l<LAYERS-1;l++) { 								//layer
				activateNeurons(inputNumbers[n],l);
				countDeltaW(neurons,eta,idealNeurons[idealNumber][l],l);
				
			}
		}	
		recountWeights();
		
	}
	
	private void activateNeurons(double [] inNeurons, int layer) {
		neurons[0] = new double [neurons[1].length+1];
		for (int b = 0;b<neurons[1].length; b++) {
			neurons[0][b] = neurons[1][b];
		}
		neurons[0][neurons[0].length-1] = 1.0;					// last neuron for bias
		neurons[1] = MatrixMath.activateNeuron(neurons[0], weights[layer]);
	}
	
	private int takeInNeurons(double [] inNeurons) {
		int idealNumber = (int)inNeurons[784];
		neurons[1] = new double [784];
		for (int b = 0;b<neurons[1].length; b++) {
			neurons[1][b] = inNeurons[b];
		}
		return idealNumber;
	}
	
	private void recountWeights() {
		for (int l = 0;l<LAYERS-1;l++)	{
			for (int v = 0;v<NEURONS_IN_LAYERS[l+1];v++)	{
				for (int k = 0; k<NEURONS_IN_LAYERS[l];k++){
					weights[l][v][k]+= deltaW[l][v][k] /iInLength;
				}
			}	
		}
	}
	
	private void countDeltaW(double [][] neurons, double eta, double[] idealNeurons,int layer) {
		for(int i = 0;i<weights[layer].length;i++) {
			for(int j = 0; j<weights[layer][i].length; j++) {
				deltaW[layer][i][j] += eta*neurons[0][j]*(idealNeurons[i]-neurons[1][i]);
			}
		}	
	}
		
	public void countIdealNeurons() {
		int idealNumber;
		for(int n =0;n<10;n++) {					// ideal output neurons (last layer)
			idealNeurons[n][LAYERS-2][n] = 1.0;		
		}
		if(LAYERS>2) {
			for(int l = LAYERS-3;l>=0;l--) {								// layer
				for(int n =0;n<iInLength;n++) {								// input number
					idealNumber = (int)inputNumbers[n][784];
					for (int i=0;i<NEURONS_IN_LAYERS[l+1];i++) {
						for (int j = 0; j<NEURONS_IN_LAYERS[l+2];j++){
							idealNeurons[idealNumber][l][i] += idealNeurons[idealNumber][l+1][j]/weights[l+1][j][i];
						}
					}
				}
				
				for (int n = 0; n<10;n++) {
					
					for(int m = 0; m<NEURONS_IN_LAYERS[l+1]; m++) {
						idealNeurons[n][l][m] = idealNeurons[n][l][m]*10/iInLength;
						idealNeurons[n][l][m] = MatrixMath.sigmoid(idealNeurons[n][l][m]);
						
						//idealNeurons[n][l][m] =(idealNeurons[n][l][m] < 0)?0:idealNeurons[n][l][m];
						//idealNeurons[n][l][m] =(idealNeurons[n][l][m]>1?1:idealNeurons[n][l][m]);
					}
				}
			}
		}
	}

public void printArray(double [][] array) {
	String a = Arrays.deepToString(array);
	a = a.replace("],", "]\n");
	System.out.println(a);
	System.out.println();
}

public void printArray(double [][][] array) {
	String a = Arrays.deepToString(array);
	a = a.replace("],", "]\n");
	a = a.replace("]]", "]]\n");
	System.out.println(a);
	System.out.println();
}
	
public int takeDigit(double [] inNeurons) {
	int digit = -1;
	double bestRes = -1000.0;
	takeInNeurons(inNeurons);
	for (int l=0;l<LAYERS-1;l++) { 								//layer
		activateNeurons(inNeurons,l);
	}
		
	for (int i=0;i<NEURONS_IN_LAYERS[LAYERS-1];i++) {
		if(neurons[1][i]>bestRes) {
			bestRes = neurons[1][i];
			digit = i;
		}	
	}
		return digit;
}
	
	
	public void selfLearning (int iteratons, int numberOfTranigSets, int ofset) {
		System.out.println("Learning...");
		double max = -1;
		loadInputNumbers(numberOfTranigSets, ofset);
		for(int i = 0;i<iteratons;i++) {
			double[][][] oldWts = getWeights();
			learnNeuronNet(0.5);
			double dif;
			max = 0;
			for (int l = 0;l<LAYERS-1;l++)	{
				for (int v = 0;v<NEURONS_IN_LAYERS[l+1];v++)	{
					for (int k = 0; k<NEURONS_IN_LAYERS[l];k++){
						 dif= Math.abs(weights[l][v][k] - oldWts[l][v][k]);
						 if(dif>max) max = dif;
					}
				}
			}

			//if(max<=0.02) {
				//System.out.println("Done "+ i +" iteration.  ");
				//System.out.println(max);
				//break;		
			//}

		}
		saveToF();
		System.out.println("Saved to a file.");
		System.out.println(max);
	}

}


