package recognition;

import java.io.Serializable;
import java.util.Arrays;

public class layer implements Serializable {
    private double[] currSumm;
    private double[][] weights;


    layer(int nbNeur, int nbPrevNeur){
        this.currSumm = new double[nbNeur];
        this.weights = new double[nbNeur][nbPrevNeur];
        for (int i = 0; i < this.weights.length; i++){
            for (int k = 0; k < this.weights[i].length; k++){
                this.weights[i][k] = Math.random() * 0.3;
            }
        }
    }

    public double[] getWeights(int nb) {
        return weights[nb];
    }

    public double[][] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights, int nb) {
        this.weights[nb] = weights;
    }

    public void setWeights(double weights, int nb1, int nb2) {
        this.weights[nb1][nb2] = weights;
    }

    public void setCurrSumm(double currSumm, int nb) {
        this.currSumm[nb] = currSumm;
    }

    public void setCurrSumm(double[] currSumm) {
        this.currSumm = currSumm;
    }

    public double[] getCurrSumm() {
        return currSumm;
    }
}
