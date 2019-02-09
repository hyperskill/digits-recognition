package digrec.stages.stage3;

import java.util.Arrays;
import java.util.Scanner;

public class Test {

	
	public static void main(String[] args) {
		
		int [][] idealInputNeurones = {
				{1,1,1,1,0,1,1,0,1,1,0,1,1,1,1,1}, //0
				{0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,1}, //1
				{1,1,1,0,0,1,1,1,1,1,0,0,1,1,1,1}, //2
				{1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1}, //3
				{1,0,1,1,0,1,1,1,1,0,0,1,0,0,1,1}, //4
				{1,1,1,1,0,0,1,1,1,0,0,1,1,1,1,1}, //5
				{1,1,1,1,0,0,1,1,1,1,0,1,1,1,1,1}, //6
				{1,1,1,0,0,1,0,0,1,0,0,1,0,0,1,1}, //7
				{1,1,1,1,0,1,1,1,1,1,0,1,1,1,1,1}, //8
				{1,1,1,1,0,1,1,1,1,0,0,1,1,1,1,1}  //9
				};
		
		Weights wts = new Weights();
		
		
		Scanner sc = new Scanner(System.in);
		System.out.println("1. Learn the network\r\n" + 
				"2. Guess a number\r\n"+
				"Enter your choice:");
		switch (sc.nextInt()) {
		case 1:
			sc.close();
			wts.selfLearning(); 
			break;
		case 2:
			/*System.out.println("Enter matrix 3x5, where 1 is blue and 0 is white:");
			int[] inNeurons = new int [16];
			for (int i=0;i<15;i++) {
			inNeurons[i]= sc.nextInt();
			}
			sc.close();
			inNeurons[15]= 1;*/
			wts.loadFromF();
			
			//System.out.println("It's a \"" + wts.takeDigit(inNeurons) + "\".");
			for(int u = 0; u<10;u++) {
			System.out.println("It's a \"" + wts.takeDigit(idealInputNeurones[u]) + "\".");
			}
			System.out.println("It's a \"" + wts.takeDigit(new int[] {1,1,1,1,0,0,1,0,1,1,0,1,1,1,1,1}) + "\".");
			
		}
		
		
		
		
	
		
	}
	
	
}
