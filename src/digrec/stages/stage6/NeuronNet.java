package digrec.stages.stage6;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @version 4.7
 *
 */
public class NeuronNet implements Serializable {

	private static final long serialVersionUID = 328778L;
	private final int LAYERS;
	private final int [] NEURONS_IN_LAYERS;
	private double [][][] weights;
	public double [][][] deltaW;	// non-rectangle matrix!
	public double [][] inputNumbers;
	public int iInLength;
	private double [][] neurons;	
	protected static final Logger LOGGER = Logger.getLogger(NeuronNet.class.getName());
	
	
	
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
	
	public NeuronNet(Level logLevel, int... neuronsInLayers) {
		LAYERS = neuronsInLayers.length;
		NEURONS_IN_LAYERS = neuronsInLayers.clone();
		weights = new double [LAYERS-1][][];
		neurons = new double[LAYERS][];
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
		Handler handler = new ConsoleHandler();
		handler.setLevel(logLevel);
		LOGGER.addHandler(handler);
		LOGGER.setLevel(logLevel);
	}
	
	public NeuronNet(int... neuronsInLayers) {
		this(Level.ALL, neuronsInLayers);
	}

	
	

	/**
	 * @return	double [][][] new array, not reference
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
		 
	    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("nnw5a.bin"))) {
			out.writeObject(this);
			LOGGER.fine("Saved successfully.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
	
	public void loadFromF() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("nnw5a.bin"))) {
			NeuronNet net = (NeuronNet)in.readObject();
			this.setWeights(net.getWeights());
			LOGGER.fine("Loaded successfully.");
						
		} catch (ClassNotFoundException|IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadInputNumbers(int countOfSamplesOfANumber, int startIndex) {
		Assets as = new Assets();										// load input numbers
		as = as.loadFromF();
		inputNumbers = as.createTraningSet(countOfSamplesOfANumber, startIndex);
		iInLength = inputNumbers.length;
	}
	
	public void learnNeuronNet(double eta) {
				
	}
	
	public int straightPropagation(double[] inputNumber) {
		neurons[0] = inputNumber.clone();
		neurons[0][inputNumber.length-1] = 1.0;
		for (int l=0;l<LAYERS-1;l++) {
			neurons[l+1] = MatrixMath.activateNeuron(neurons[l], weights[l]);
			if(l !=LAYERS-2) {
				neurons[l+1] = Arrays.copyOf(neurons[l+1], neurons[l+1].length+1);
				neurons[l+1][neurons[l+1].length-1] = 1.0;
			}
		}
		return (int) inputNumber[inputNumber.length-1];
	}
	
	public void backPropagation(int idealNumber, double eta, double lambda) {
		double [][] error = new double [2][]; 
		
		//last layer LAYERS-1
		error[0] = new double[NEURONS_IN_LAYERS[LAYERS-1]];
		for (int out = 0; out<NEURONS_IN_LAYERS[LAYERS-1];out++) {
			error[0][out] = out==idealNumber?(neurons[LAYERS-1][out]-1.0):(neurons[LAYERS-1][out]);
			error[0][out]*=neurons[LAYERS-1][out]*(1-neurons[LAYERS-1][out]);
		
			for (int i = 0;i<weights[LAYERS-2][0].length;i++) {
				deltaW[LAYERS-2][out][i] -= eta*
						(error[0][out]*neurons[LAYERS-2][i]+
								lambda*weights[LAYERS-2][out][i]/inputNumbers.length);
			}
		}
		
		// hidden layers	
		for(int l = LAYERS-2;l>0;l--) {	
			error[1] = error[0].clone(); 
			error[0] = new double [NEURONS_IN_LAYERS[l]];
			for (int j = 0; j<NEURONS_IN_LAYERS[l];j++) {
				for (int k = 0;k<NEURONS_IN_LAYERS[l+1];k++) {
					error[0][j] += weights[l][k][j]*error[1][k];
				}
				error[0][j]*=neurons[l][j]*(1-neurons[l][j]);
				for (int i = 0;i<weights[l-1][0].length;i++) {
					deltaW[l-1][j][i] -= eta*
							(error[0][j]*neurons[l-1][i]+
									lambda*weights[l-1][j][i]/inputNumbers.length);
				}
			}
		}
		
	}
	
	
	
	

	
	private void recountWeights() {
		for (int l=0;l<(weights.length);l++) {
			for (int m=0;m<(weights[l].length);m++) {
				for (int n=0;n<(weights[l][m].length);n++) {
					weights[l][m][n] += deltaW[l][m][n];
				}
			}
		}
	}
	
	private void countDeltaW(double [][] neurons, double eta, double[] idealNeurons,int layer) {
		
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
	straightPropagation(inNeurons);
		
	for (int i=0;i<NEURONS_IN_LAYERS[LAYERS-1];i++) {
		if(neurons[1][i]>bestRes) {
			bestRes = neurons[1][i];
			digit = i;
		}	
	}
		return digit;
}
	
	
public void selfLearning (int numberOfTranigSets,int ofset, int epoches, 
							double eta, int batchSize, double lambda, double minErr, double minDErr ) {
	double currErr = Short.MAX_VALUE;
	double lastErr = 0;
	int epochN = 0;
	LOGGER.fine("Learning...");
	loadInputNumbers(numberOfTranigSets, ofset);
	List<double[]> iNums = Arrays.asList(inputNumbers);

	if(batchSize<1 || batchSize> (numberOfTranigSets*10)) {
		batchSize = numberOfTranigSets*10;
	}
	
	do {																//epoch cycle
		lastErr = currErr;
		Collections.shuffle(iNums);
		for(int b = 0; b<(iInLength+batchSize); b+=batchSize) {		//batches iterations in input numbers set
			
			// deltaWeight initialization
			deltaW = new double [LAYERS-1][][];				
			for (int l=0;l<(LAYERS-1);l++) {
				deltaW[l] = new double[NEURONS_IN_LAYERS[l+1]][];
				for (int i=0;i<NEURONS_IN_LAYERS[l+1];i++) {
					deltaW[l][i] = new double[NEURONS_IN_LAYERS[l]+1];
				}
			}
			//---
			
			for(int bs = 0; (bs<batchSize) && ((b+bs)<iInLength); bs++) {						// each batch processing 
				int idealNumber = straightPropagation(iNums.get(b+bs));
				backPropagation(idealNumber, eta, lambda);
			}
			
			// recount weights
			recountWeights();
		}
		
		//error block
		
		//real error
		currErr = 0;
		int idealNumber;
		for (int n=0;n<(iNums.size());n++) {
			idealNumber = straightPropagation(inputNumbers[n]);
			for (int i=0;i<NEURONS_IN_LAYERS[LAYERS-1];i++) {
				double out = i==idealNumber?(1.0-neurons[LAYERS-1][i]):(-neurons[LAYERS-1][i]);
				currErr += out*out*0.5 ;
			}
		}
		currErr /= iNums.size();
		
		//error regularization
		double reg = 0;
		if(Math.abs(lambda)>Float.MIN_VALUE) {
			for (int l=0;l<(weights.length);l++) {
				for (int m=0;m<(weights[l].length);m++) {
					for (int n=0;n<(weights[l][m].length);n++) {
						reg+= weights[l][m][n]*weights[l][m][n];
					}
				}
			}
			currErr+=lambda*reg/(2*iNums.size());
		}
		
		epochN++;
		LOGGER.log(Level.INFO,"Epoch # {0} finished; current error is {1}.", new Object[]{epochN,currErr});
	}while(epochN<epoches && currErr>minErr && Math.abs(currErr-lastErr)>minDErr);
	
	//saveToF();
	LOGGER.fine("Saved to a file.");
		
}

public void selfLearning () {
	selfLearning (1000, 0, 10, 0.5,-1, 0, 0.005, 0.000005);
}

}


