package digrec.stages.stage4;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

/**
 * @version 4.4
 *
 */
public class NeuronNet implements Serializable {

	private static final long serialVersionUID = 328778L;
	private final int LAYERS;
	private final int [] NEURONS_IN_LAYERS;
	private double [][][] weights;
	public double [][] idealNeurons; 	// non-rectangle matrix!
		
	double [][] idealInputNeurones = {
			{1,1,1,1,0,1,1,0,1,1,0,1,1,1,1}, //0
			{0,1,0,0,1,0,0,1,0,0,1,0,0,1,0}, //1
			{1,1,1,0,0,1,1,1,1,1,0,0,1,1,1}, //2
			{1,1,1,0,0,1,1,1,1,0,0,1,1,1,1}, //3
			{1,0,1,1,0,1,1,1,1,0,0,1,0,0,1}, //4
			{1,1,1,1,0,0,1,1,1,0,0,1,1,1,1}, //5
			{1,1,1,1,0,0,1,1,1,1,0,1,1,1,1}, //6
			{1,1,1,0,0,1,0,0,1,0,0,1,0,0,1}, //7
			{1,1,1,1,0,1,1,1,1,1,0,1,1,1,1}, //8
			{1,1,1,1,0,1,1,1,1,0,0,1,1,1,1}  //9
			};
	int iInLength = idealInputNeurones.length;
	
	
	public NeuronNet() {
		NEURONS_IN_LAYERS = new int[]{15,10};
		LAYERS = NEURONS_IN_LAYERS.length;
		weights = new double [LAYERS-1][][];
		Random rd = new Random(serialVersionUID);
		for (int l=0;l<(LAYERS-1);l++) {
			weights[l] = new double[NEURONS_IN_LAYERS[l+1]][];
			for (int i=0;i<NEURONS_IN_LAYERS[l+1];i++) {
				weights[l][i] = new double[NEURONS_IN_LAYERS[l]+1];
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
		Random rd = new Random(328778L);
		for (int l=0;l<(LAYERS-1);l++) {
			weights[l] = new double[NEURONS_IN_LAYERS[l+1]][];
			for (int i=0;i<NEURONS_IN_LAYERS[l+1];i++) {
				weights[l][i] = new double[NEURONS_IN_LAYERS[l]+1];
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
		 
	    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("nnw.bin"))) {
			out.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	   // System.out.println("Saved successfully.");
	}
	
	public void loadFromF() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("nnw.bin"))) {
			this.setWeights(((NeuronNet)in.readObject()).getWeights());
						
		} catch (ClassNotFoundException|IOException e) {
			e.printStackTrace();
		}
		//System.out.println("Loaded successfully.");
	}
	
	
	public void learnNeuronNet() {
		final double eta = 0.5;
		double [][] neurons = new double[2][]; 		
		double [][][] deltaW =	new double[weights.length][][];	
		CountIdealNeurons();
		for(int n =0;n<iInLength;n++) {
			neurons[1] = idealInputNeurones[n].clone();
			
			for (int l=1;l<LAYERS;l++) { 		//layer
				neurons[0] = new double[neurons[1].length+1];
				for(int b = 0; b<neurons[0].length-1; b++) {
					neurons[0][b] = neurons[1][b];
				}
				neurons[0][neurons[0].length-1] = 1.0;
				//neurons[1] = new double [NEURONS_IN_LAYERS[l]+1];
				neurons[1] = MatrixMath.ActivateNeuron(neurons[0], weights[l-1]);
				deltaW[l-1] = new double[weights[l-1].length][];
				for(int i = 0;i<weights[l-1].length;i++) {
					deltaW[l-1][i] = new double[weights[l-1][i].length];
					for(int j = 0; j<weights[l-1][i].length; j++) {
						deltaW[l-1][i][j] += eta*neurons[0][j]*(idealNeurons[l][i]-neurons[1][i]);
					}
				}	
			}
			
		}	
		for (int l = 0;l<LAYERS-1;l++)	{
			for (int v = 0;v<NEURONS_IN_LAYERS[l+1];v++)	{
				for (int k = 0; k<NEURONS_IN_LAYERS[l];k++){
					weights[l][v][k]+= deltaW[l][v][k] /NEURONS_IN_LAYERS[l+1];
				}
				
			}	
			
		}
		
	}
	
	
	public void CountIdealNeurons() {
		idealNeurons = new double [LAYERS][]; 	// non-rectangle matrix!
		double iIL = 1/(double)iInLength;
		for (int l=0;l<LAYERS;l++) { 		//layer
			idealNeurons[l] = new double [NEURONS_IN_LAYERS[l]];
		}
		for(int n =0;n<iInLength;n++) {
			for(int ln = 0;ln<NEURONS_IN_LAYERS[LAYERS-1];ln++) { 
				idealNeurons[LAYERS-1][ln] = (ln==n)?1:0;			// Ideal Output neurons
			}
			
			for(int l = LAYERS-2;l>=0;l--) {
				
				for (int i=0;i<NEURONS_IN_LAYERS[l];i++) {
					
						for (int j = 0; j<NEURONS_IN_LAYERS[l+1];j++){
							idealNeurons[l][i] += idealNeurons[l+1][j]/weights[l][j][i];
						}
				}
			}
		}
		for(int k = 0; k<LAYERS-2; k++) {
			for(int m = 0; m<idealNeurons[k].length; m++) {
				idealNeurons[k][m] = idealNeurons[k][m]*iIL;
				idealNeurons[k][m] = MatrixMath.Sigmoid(idealNeurons[k][m]);
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
	double [][] neurons = new double[2][]; 	
	neurons[1] = inNeurons.clone();
	
	for (int l=1;l<LAYERS;l++) { 		//layer
		neurons[0] = neurons[1].clone();
		neurons[1] = new double [NEURONS_IN_LAYERS[l]];
		neurons[1] = MatrixMath.ActivateNeuron(neurons[0], weights[l-1]);
	}
	
	for (int i=1;i<NEURONS_IN_LAYERS[LAYERS-1];i++) {
		if(neurons[1][i]>bestRes) {
			bestRes = neurons[1][i];
			digit = i;
		}	
	}
		return digit;
	}
	
	
	public void selfLearning () {
		System.out.println("Learning...");
		double max = -1;
		for(int i = 0;i<507;i++) {
			double[][][] oldWts = getWeights();
			learnNeuronNet();
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
			if(max<=0.2553566) {
				System.out.println("Done "+ i +" iteration.");
				break;		
			}
		}
		saveToF();
		System.out.println("Saved to a file.");
		System.out.println(max);
	}

}


