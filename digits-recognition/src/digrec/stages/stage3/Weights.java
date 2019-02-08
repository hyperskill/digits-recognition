package digrec.stages.stage3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class Weights implements Serializable {

	private static final long serialVersionUID = 328778L;
	double [][] weights = new double [10][16];
	
	public Weights() {
		Random rd = new Random(328778L);
		for (int i=0;i<10;i++) {
			for (int j = 0; j<16;j++){
				weights[i][j]= rd.nextGaussian();
			}
		}
		
	}

	public double[][] getWeights() {
		double[][] wts = new double [10][16];
		System.arraycopy(wts, 0, weights, 0, wts.length); 
		return wts;
	}

	public void setWeights(double[][] weights) {
		System.arraycopy(this.weights, 0, weights, 0, this.weights.length); 
	}
	
	public void saveToF() {
		 
	    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("nnw.txt"))) {
			out.writeObject(this);
			out.flush();
			out.close();
			System.out.println("Saved successfully to nnw.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void loadFromF() {
		
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("nnw.txt"))) {
			Weights twts = (Weights) in.readObject();
			in.close();
			this.setWeights(twts.getWeights());
			System.out.println("Loaded successfully.");
		} catch (ClassNotFoundException|IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void learnNeuNet() {
		
	}
	
}
