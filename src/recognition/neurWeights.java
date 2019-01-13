package recognition;

import java.io.Serializable;
import java.util.Arrays;

public class neurWeights implements Serializable {
    private double[][][] neur = newNeur(0.5);



    public double[][][] getNeur() {
        return neur;
    }

    public double[][] getMas(double d){
        double[][] mas = new double[5][3];
        for (double[] a : mas){
            Arrays.fill(a, d);
        }
        return mas;
    }
    private double[][][] newNeur(double d){
        return new double[][][]{getMas(d),getMas(d),getMas(d),getMas(d),getMas(d),getMas(d),getMas(d),getMas(d),getMas(d),getMas(d)};
    }
}

