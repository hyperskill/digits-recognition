package digrec.stages.stage5;

public class Learning {
	NeuronNet nNet;
	final double eta;
	double [][] neurons = new double[2][]; 
	
	public Learning() {
		eta = 0.5;
		nNet = new NeuronNet(784,10);
	}
	
	public Learning(double eta,int... neuronsInLayers) {
		this.eta = eta;
		nNet = new NeuronNet(neuronsInLayers);
	}
	//TODO Processing here
	public void learnNeuronNet() {
		final double eta = 0.5;
		double [][] neurons = new double[2][]; 		

		deltaW = new double[weights.length][][];	
		
		if(inputNumbers==null) {
			LoadInputNumbers(1,5);
		}
		
		idealNeurons = new double [LAYERS-1][]; 	// non-rectangle matrix!
		for (int l=0;l<LAYERS-1;l++) { 		//layer
			idealNeurons[l] = new double [NEURONS_IN_LAYERS[l+1]];
			deltaW[l] = new double[weights[l].length][];
		}
		if(LAYERS >2) {
			CountIdealNeurons();
		}
		
		for(int n =0;n<iInLength;n++) {									// input numbers
			neurons[1] = inputNumbers[n].clone();
			for (int l=0;l<LAYERS-1;l++) { 								//layer
				neurons[0] = neurons[1].clone();
				neurons[0][neurons[0].length-1] = 1.0;					// bias neuron for previous layer
				neurons[1] = MatrixMath.ActivateNeuron(neurons[0], weights[l]);
				
				for(int i = 0;i<weights[l].length;i++) {
					if (deltaW[l][i] == null) {
						deltaW[l][i] = new double[weights[l][i].length];
					}
					if (l ==LAYERS-2) {
						idealNeurons[l][i] = (i==inputNumbers[n][784]?1:0);
					}
					for(int j = 0; j<weights[l][i].length; j++) {
						deltaW[l][i][j] += eta*neurons[0][j]*(idealNeurons[l][i]-neurons[1][i]);
					}
				}	
			}
			
		}	
		for (int l = 0;l<LAYERS-1;l++)	{
			for (int v = 0;v<NEURONS_IN_LAYERS[l+1];v++)	{
				for (int k = 0; k<NEURONS_IN_LAYERS[l];k++){
					weights[l][v][k]+= deltaW[l][v][k] /iInLength;
				}
				
			}	
			
		}
		
	}
	
	
}
