package digrec.stages.stage5;

/**
 * There are necessary matrix mathematics calculations for the project.  
 *
 */
public final class MatrixMath {

//TODO My be make another name about weighted sum...	
	public static double [] multiply (double [] vec, double [][] matrix) {
		if(vec.length!=matrix[0].length) {
			throw new IllegalArgumentException("Illegal length of vector");
		}
		double [] resVec = new double [matrix.length];
		for (int i=0;i<matrix.length;i++) {
			for (int j = 0; j<matrix[0].length;j++){
				resVec[i]+= vec[j]*matrix[i][j];
			}
		}
		return resVec;
		
	}
	
	public static double [] activateNeuron (double [] vec, double [][] matrix) {
		if(vec.length!=matrix[0].length) {
			throw new IllegalArgumentException("Illegal length of vector");
		}
		double [] resVec = new double [matrix.length];
		for (int i=0;i<matrix.length;i++) {
			for (int j = 0; j<matrix[0].length;j++){
				resVec[i]+= vec[j]*matrix[i][j];
			}
			resVec[i] = sigmoid(resVec[i]);
		}
		return resVec;
		
	}
	
	public static double [] divide (double [] vec, double [][] matrix) {
		if(vec.length!=matrix[0].length) {
			throw new IllegalArgumentException("Illegal length of vector");
		}
		double [] resVec = new double [matrix.length];
		for (int i=0;i<matrix.length;i++) {
			for (int j = 0; j<matrix[0].length;j++){
				resVec[i]+= vec[j]/matrix[i][j];
			}
		}
		return resVec;
		
	}
	
	
	
	
	/**
	 * @param matrix double must be rectangle
	 * @return new rectangle matrix, don't touch source matrix
	 */
	public static double [][] transpose (double [][] matrix) {
		if(matrix[0].length!=matrix[1].length) {
			throw new IllegalArgumentException("The matrix is not rectangle!");
		}
		double[][] matrixT = new double[matrix[0].length][matrix.length];
		for(int i = 0;i<matrix.length;i++) {
			for(int j = 0;j<matrix[0].length;j++) {
				matrixT[j][i]=matrix[i][j];
			}
		}
		return matrixT;
	}
	
	public static double sigmoid (double x) {
	
		return 1/(1+ Math.pow(Math.E, -x));
	}
	
	
	
	public static double [][] sigmoid (double [][] matrix) {
		double[][] sMatrix = new double[matrix.length][];
		for(int i = 0;i<matrix.length;i++) {
			sMatrix[i] = matrix[i].clone();
			for(int j = 0;j<matrix[i].length;j++) {
				sMatrix[i][j]=sigmoid(sMatrix[i][j]);
			}
		}
		return sMatrix;
	}
	
	public static double [][][] sigmoid (double [][][] matrix) {
		double[][][] sMatrix = new double[matrix.length][][];
		for(int i = 0;i<matrix.length;i++) {
			sMatrix[i] = matrix[i].clone();
			for(int j = 0;j<matrix[i].length;j++) {
				sMatrix[i][j] = matrix[i][j].clone();
				for(int k = 0;k<matrix[i][j].length;k++) {
					sMatrix[i][j][k]=sigmoid(sMatrix[i][j][k]);
				}
			}
		}
		return sMatrix;
	}
	
	public static double derivativeOfSigmoid (double x) {
		
		return sigmoid(x)*(1-sigmoid(x));
	}
	
	public static double [][] derivativeOfSigmoid (double [][] matrix) {
		
		double[][] sMatrix = new double[matrix.length][];
		for(int i = 0;i<matrix.length;i++) {
			sMatrix[i] = matrix[i].clone();
			for(int j = 0;j<matrix[i].length;j++) {
				sMatrix[i][j]=derivativeOfSigmoid(sMatrix[i][j]);
			}
		}
		return sMatrix;
	}
	
	public static double [][][] derivativeOfSigmoid (double [][][] matrix) {
		
		double[][][] sMatrix = new double[matrix.length][][];
		for(int i = 0;i<matrix.length;i++) {
			sMatrix[i] = matrix[i].clone();
			for(int j = 0;j<matrix[i].length;j++) {
				sMatrix[i][j] = matrix[i][j].clone();
				for(int k = 0;k<matrix[i][j].length;k++) {
					sMatrix[i][j][k]=derivativeOfSigmoid(sMatrix[i][j][k]);
				}
			}
		}
		return sMatrix;
	}
	
	
}
