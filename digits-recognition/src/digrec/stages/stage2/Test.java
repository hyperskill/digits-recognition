package digrec.stages.stage2;

import java.util.Scanner;

public class Test {

	
	public static void main(String[] args) {
		final int[][]nWeights = new int[][] {
			{1,1,1,1,-1,1,1,-1,1,1,-1,1,1,1,1,-1},
			{-1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,6},
			{1,1,1,-1,-1,1,1,1,1,1,-1,-1,1,1,1,0},
			{1,1,1,-1,-1,1,1,1,1,-1,-1,1,1,1,1,0},
			{1,-1,1,1,-1,1,1,1,1,-1,-1,1,-1,-1,1,2},
			{1,1,1,1,-1,-1,1,1,1,-1,-1,1,1,1,1,0},
			{1,1,1,1,-1,-1,1,1,1,1,-1,1,1,1,1,-1},
			{1,1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,4},
			{1,1,1,1,-1,1,1,1,1,1,-1,1,1,1,1,-2},
			{1,1,1,1,-1,1,1,1,1,-1,-1,1,1,1,1,-1}
		 };
		
		Scanner sc = new Scanner(System.in);
		
		int [] outNeurons = new int[10]; 
		int[] inNeurons = new int [16];
		inNeurons[15] = 1; // bias multiplier
		int bestRes = -100;
		int digit = 0;
		System.out.println("enter matrix 3x5, where 1 is blue and 0 is white:");
		for (int i=0;i<15;i++) {
			inNeurons[i]= sc.nextInt();
		}
		sc.close();
		
		for (int i=0;i<10;i++) {
			for (int j = 0; j<16;j++){
				outNeurons[i]+= inNeurons[j]*nWeights[i][j];
			}
			
			if(outNeurons[i]>bestRes) {
				bestRes = outNeurons[i];
				digit = i;
			}
		}
		
		//System.out.println(Arrays.toString(outNeurons));
		System.out.println("It's a \"" + digit + "\".");
		

	}

}
