package digrec.tests;



import java.util.Arrays;
import java.util.Random;
import digrec.stages.stage4.MatrixMath;
import digrec.stages.stage4.NeuronNet;

public class Test  {
	final int LAYERS;
	final int [] NEURONS_IN_LAYERS;
	double [][][] weights;
		  
	public static void main(String[] args) {
		/*double [][] arr1 = {{0.0,0.1,0.2},{1.0,1.1,1.2}};
		double [][] arr2;
		arr2 = Sigmoid(arr1);
	    					{{0.0,0.1,0.2},{2.0,2.1,2.2}}};
		*/
		
		
		/*double [][][] arr3 = {{{1.2,6.1,6.2,6.3},{1.0,1.1,1.2,1.0}},{{5.0,5.1},{1.0,1.1},{1.0,1.1}}};
		double [][] arr2 = MatrixMath.Transpose(arr3[0]);
		double [][][] arr4 = MatrixMath.DerivativeOfSigmoid(arr3);
		System.out.println(Arrays.deepToString(arr2));
		double i = Double.NaN;
		*/
		NeuronNet nn = new NeuronNet(4,3,3,10);
		
		nn.CountIdealNeurons();
		System.out.println(Arrays.deepToString(nn.idealNeurons));
		/*
		Test test1 = new Test(2,3);
		System.out.println(Arrays.deepToString(test1.weights));
		System.out.println();
		double [][] arr4 = MatrixMath.Transpose(test1.weights[0]);
		double [] vec = MatrixMath.Add(new double[] {2,2}, new double[][] {{0,2},{2,0},{2,2}});
		
	    
	   
	    //System.out.println(Arrays.deepToString(arr3));
	    
	

	    System.out.println();
		//Test test1 = new Test(1,2,3);
		System.out.println(Arrays.toString(vec));
		*/
	}
	
	public Test(int... neuronsInLayers) {
		LAYERS = neuronsInLayers.length;
		NEURONS_IN_LAYERS = neuronsInLayers.clone();
		weights = new double [LAYERS-1][][];
		Random rd = new Random(328778L);
		for (int l=0;l<(LAYERS-1);l++) {
			weights[l] = new double[NEURONS_IN_LAYERS[l]][];
			for (int i=0;i<NEURONS_IN_LAYERS[l];i++) {
				weights[l][i] = new double[NEURONS_IN_LAYERS[l+1]];
				for (int j = 0; j<NEURONS_IN_LAYERS[l+1];j++){
					weights[l][i][j]= rd.nextGaussian();
				}
			}
		}
		
	}
	
	static double [][] Sigmoid (double [][] matrix) {
		double[][] sMatrix = new double[matrix.length][];
		for(int i = 0;i<matrix.length;i++) {
			sMatrix[i] = matrix[i].clone();
		}
		return sMatrix;
	}
	
	static double [][][] Sigmoid (double [][][] matrix) {
		double[][][] sMatrix = new double[matrix.length][][];
		for(int i = 0;i<matrix.length;i++) {
			sMatrix[i] = matrix[i].clone();
			for(int j = 0;j<matrix[i].length;j++) {
				sMatrix[i][j] = matrix[i][j].clone();
			}
		}
		return sMatrix;
	}
	
	public double[][][] getWeights() {
		double[][][] wts = new double [LAYERS-1][][];
		int l;
		for(int i = 0;i<LAYERS-1;i++) {
			l = weights[i].length;
			wts[i] = weights[i].clone();
			for(int j = 0;j<l;j++) {
				wts[i][j] = weights[i][j].clone();
			}
		}
		return wts;
	}

	public void setWeights(double[][][] weights) {
		int l;
		for(int i = 0;i<LAYERS-1;i++) {
			l = weights[i].length;
			this.weights[i]= weights[i].clone();
			for(int j = 0;j<l;j++) {
				this.weights[i][j] = weights[i][j].clone();
			}
		}
	}
	

}
