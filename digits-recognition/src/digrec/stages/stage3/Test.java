package digrec.stages.stage3;

import java.util.Arrays;
import java.util.Scanner;

public class Test {

	
	public static void main(String[] args) {
		
		double[][]nWeights = new double[10][15];
		
		
		//Scanner sc = new Scanner(System.in);
		
		double [] outNeurons = new double[10]; 
		int[] inNeurons = new int [15];
		
		double bestRes = -1.0;
		int digit = 0;
		//System.out.println("enter matrix 3x5, where 1 is blue and 0 is white:");
		/*for (int i=0;i<15;i++) {
			inNeurons[i]= sc.nextInt();
		}
		sc.close();*/
		
		
		
		//System.out.println(Arrays.toString(outNeurons));
		//System.out.println("It's a \"" + digit + "\".");
		Weights wts = new Weights();
		//wts.saveToF();
		//wts.loadFromF();
		
		System.out.println(Arrays.deepToString(wts.weights));
		for(int i = 0;i<100;i++) {
		wts.learnNeuNet();
		System.out.println(Arrays.deepToString(wts.weights));
		}
	}
	
	int takeDigit(int[] inNeurons) {
		int digit = 0;
		double [] outNeurons = new double[10];
		double bestRes = -1.0;
		double[][] nWeights = new double[10][16];
		 
		for (int i=0;i<10;i++) {
			for (int j = 0; j<16;j++){
				outNeurons[i]+= inNeurons[j]*nWeights[i][j];
			}
			
			if(outNeurons[i]>bestRes) {
				bestRes = outNeurons[i];
				digit = i;
			}
		}
		return digit;
	}
}
