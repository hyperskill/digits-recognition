package digrec.stages.stage4;

/**
 * There are necessary matrix mathematics calculations for the project.  
 *
 */
public final class MatrixMath {

//TODO My be make another name about weighted sum...	
	public static double [] Add (double [] vec, double [][] matrix) {
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
	
	/**
	 * @param matrix double must be rectangle
	 * @return new rectangle matrix, don't touch source matrix
	 */
	public static double [][] Transpose (double [][] matrix) {
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
	
	public static double Sigmoid (double x) {
	
		return 1/(1+ Math.pow(Math.E, -x));
	}
	
	public static double [][] Sigmoid (double [][] matrix) {
		double[][] sMatrix = new double[matrix.length][];
		for(int i = 0;i<matrix.length;i++) {
			sMatrix[i] = matrix[i].clone();
			for(int j = 0;j<matrix[i].length;j++) {
				sMatrix[i][j]=Sigmoid(sMatrix[i][j]);
			}
		}
		return sMatrix;
	}
	
	public static double [][][] Sigmoid (double [][][] matrix) {
		double[][][] sMatrix = new double[matrix.length][][];
		for(int i = 0;i<matrix.length;i++) {
			sMatrix[i] = matrix[i].clone();
			for(int j = 0;j<matrix[i].length;j++) {
				sMatrix[i][j] = matrix[i][j].clone();
				for(int k = 0;k<matrix[i][j].length;k++) {
					sMatrix[i][j][k]=Sigmoid(sMatrix[i][j][k]);
				}
			}
		}
		return sMatrix;
	}
	
	public static double DerivativeOfSigmoid (double x) {
		
		return Sigmoid(x)*(1-Sigmoid(x));
	}
	
	public static double [][] DerivativeOfSigmoid (double [][] matrix) {
		
		double[][] sMatrix = new double[matrix.length][];
		for(int i = 0;i<matrix.length;i++) {
			sMatrix[i] = matrix[i].clone();
			for(int j = 0;j<matrix[i].length;j++) {
				sMatrix[i][j]=DerivativeOfSigmoid(sMatrix[i][j]);
			}
		}
		return sMatrix;
	}
	
	public static double [][][] DerivativeOfSigmoid (double [][][] matrix) {
		
		double[][][] sMatrix = new double[matrix.length][][];
		for(int i = 0;i<matrix.length;i++) {
			sMatrix[i] = matrix[i].clone();
			for(int j = 0;j<matrix[i].length;j++) {
				sMatrix[i][j] = matrix[i][j].clone();
				for(int k = 0;k<matrix[i][j].length;k++) {
					sMatrix[i][j][k]=DerivativeOfSigmoid(sMatrix[i][j][k]);
				}
			}
		}
		return sMatrix;
	}
	
	
}
