package digrec.stages.stage4;

/**
 * There are necessary matrix mathematics calculations for the project.  
 *
 */
public final class MatrixMath {

	static double [] Add (double [] vec1, double [] vec2) {
		
		
		return null;
		
	}
	static double [] Add (double [] vec1, double [][] vec2) {
		
		
		return null;
		
	}
	
	static double Sigmoid (double x) {
	
		return 1/(1+ Math.pow(Math.E, -x));
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
	
	static double DerivativeOfSigmoid (double x) {
		
		return x;
	}
	
	static double [][] DerivativeOfSigmoid (double [][] matrix) {
		
		return matrix;
	}
	
	static double [][][] DerivativeOfSigmoid (double [][][] matrix) {
		
		return matrix;
	}
	
	
}
