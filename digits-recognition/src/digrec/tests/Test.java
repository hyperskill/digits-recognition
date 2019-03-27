package digrec.tests;



import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import digrec.stages.stage3.Weights;
import digrec.stages.stage5.MatrixMath;
import digrec.stages.stage5.NeuronNet;
import digrec.stages.stage5.Assets;
public class Test  {

		  
	public static void main(String[] args) {
		
		Timer timer =  new Timer();
		long time = 0;
		//for(int i = 0; i<n; i++) {
			time += timer.meTime(new Func());
		//}
		System.out.println(time);
		
		  
	    //System.out.println(Arrays.deepToString(arr3));
	    
	}

}

class Func implements Runnable {

	@Override
	public void run() {
		Assets newInput = new Assets();
		
		newInput.fillTrainingSamples();
		//newInput.getinputSample(7000*8+2);
		for(int i = 0; i<785; i++) {
			System.out.print(newInput.trainingSamples[69999][i] + "\t");
			if((i+1)%28==0 || i==784) {
				System.out.println();
			}
		}
		
		//System.out.println(newInput.inputSample[784]);
		
		
		
		
		
		//NeuronNet nn = new NeuronNet(15,10);//(15,3,4,10);
		//Weights wts = new Weights();
		//nn.CountIdealNeurons();
		//for(int i=0; i<10;i++) {
			
		//}
		//nn.learnNeuronNet();

		//nn.selfLearning(1000);
		//nn.printArray(nn.getWeights());
		//wts.selfLearning();
		//wts.learnNeuNet();
		//nn.printArray(wts.getWeights());
		//System.out.println();
		//nn.selfLearning(100);
		//nn.printArray(nn.getWeights());
		//nn.loadFromF();
		
		//System.out.println("It's a \"" + nn.takeDigit(nn.idealInputNeurones[0]) + "\".");

		//for(int u = 0; u<10;u++) {
		//System.out.println("It's a \"" + nn.takeDigit(nn.idealInputNeurones[u]) + "\".");
		//}
		
	}
	
}

class Timer{
	public long meTime(Runnable run) {
		long start = System.currentTimeMillis();
		run.run();
		return System.currentTimeMillis()-start;
	}
}
