package recognition;

import javax.swing.event.ChangeListener;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        weights CheckList = new weights();
        Scanner sc = new Scanner(System.in);
        String sw = sc.nextLine();
        switch (sw) {
            case "1":
                String s = sc.nextLine();
                String[] str = s.split("\\s+");
                int[] param = new int[str.length];
                for (int i = 0; i < str.length; i++) {
                    param[i] = Integer.parseInt(str[i]);
                }
                neurWeights layers = new neurWeights(param);


               for (int nb = 0; nb < 1000; nb++) {
                   double[] d = toMas(CheckList.nbrs[nb % CheckList.nbrs.length]);
                   calcMass(layers.getLayers()[1], d);

                   setNewWeights(layers.getLayers()[1], nb % CheckList.nbrs.length, d);

               }
               FileOutputStream fos = new FileOutputStream("temp.out");
               ObjectOutputStream oos = new ObjectOutputStream(fos);
               oos.writeObject(layers);
               oos.flush();
               oos.close();
                System.out.println("File saved!");
                break;
            case "2":
                System.out.println("Input 3x5 field, \"_\" is empty space:");
                String[] strO = new String[5];
                double[] max = new double[10];
                for (int i = 0; i < strO.length; i++){
                    strO[i] = sc.nextLine();
                }

                FileInputStream fis = new FileInputStream("temp.out");
                ObjectInputStream ois = new ObjectInputStream(fis);
                neurWeights layersO = (neurWeights) ois.readObject();

                for (int nb = 0; nb < layersO.getLayers()[1].getCurrSumm().length; nb++){
                    double sum = 0;
                    for (int i = 0; i < strO.length; i++){
                        for (int k = 0; k < strO[i].length(); k++){
                            sum += ((strO[i].charAt(k) == '_')? 0 : 1) * layersO.getLayers()[1].getWeights(nb)[i * strO[i].length() + k];
                        }
                    }
                    max[nb] =sum;
                    //layersO.getLayers()[1].setCurrSumm(S(sum + 1), nb);
                }
                int nb = 0;
                double mmax = 0;
                for (int i = 0; i < max.length; i++){
                    if (max[i] > mmax){
                        nb = i;
                        mmax = max[i];
                    }
                }
                System.out.println(nb);

                break;
        }
    }

    private static void calcMass(layer Layer, double[] neibors){
        double[] bufWeights = new double[Layer.getCurrSumm().length];
        Arrays.fill(bufWeights, 0);
        double sum;
        for (int n = 0; n < Layer.getCurrSumm().length; n++) {
            sum = 1;
            for (int i = 0; i < neibors.length; i++) {
                sum += Layer.getWeights(n)[i] * neibors[i];
            }
            bufWeights[n] = S(sum);
        }
        Layer.setCurrSumm(bufWeights);
    }

    private static void setNewWeights(layer Layer, int nb, double[] d){
        double diff = 0.1;
        for (int n = 0; n < Layer.getWeights().length; n++){
            for (int prev = 0; prev < Layer.getWeights()[n].length; prev++){
                double newWeight = diff * (((nb == n)? 1 : 0) - Layer.getCurrSumm()[n]) * d[prev] + Layer.getWeights()[n][prev];
                Layer.setWeights(newWeight, n, prev);
            }
        }
    }

    private static double getSumm ( double[][] a, int[][] b, int layerN){
        double sum = -1;
        for (int i = 0; i < a[layerN].length; i++) {
            sum += (b[i / b[0].length][i % b[0].length] == 1) ? a[layerN][i] : 0;
        }
        return 1 / (Math.exp(-sum) + 1);
    }

    private static double[] toMas(double[][] mas){
        double[] mass = new double[mas.length * mas[0].length];
        for (int i = 0; i < mass.length; i++){
            mass[i] = mas[i / mas[0].length][i % mas[0].length];
        }
        return mass;
    }


    private static double S ( double nb){
        //System.out.println(nb);
        return 1 / (Math.exp(-nb) + 1);
    }
}