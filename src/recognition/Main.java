package recognition;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        weights weight = new weights();
        double[] mass = new double[weight.nbrs.length];
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Learn the network\n2. Guess a number");
        String opt = sc.nextLine();
        System.out.printf("Your choice: %s\n", opt);
        switch (opt) {
            case "1":
                System.out.println("Learning...");
                neurWeights nW1 = new neurWeights();
                for (int i = 0; i < 10; i++) {
                    NewWeights(nW1, weight, i % 10);
                }
                FileOutputStream fos = new FileOutputStream("temp.out");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(nW1);
                oos.flush();
                oos.close();
                System.out.println("Done! Saved to the file.");
                break;
            case "2":
                System.out.println("Input 5x3 grid ('_' for white, any other for blue)");
                FileInputStream fis = new FileInputStream("temp.out");
                ObjectInputStream ois = new ObjectInputStream(fis);
                neurWeights nW = (neurWeights) ois.readObject();
                String[] str = new String[5];
                for (int i = 0; i < 5; i++) {
                    str[i] = sc.nextLine();
                }
                //part of calc of exit
                for (int nb = 0; nb < mass.length; nb++) {
                    for (int i = 0; i < 5; i++) {
                        for (int k = 0; k < 3; k++) {
                            if (str[i].charAt(k) != '_') {
                                mass[nb] += nW.getNeur()[nb][i][k];
                            }
                        }
                    }
                }
                int out = 0;
                double max = 0;
                for (int i = 0; i < mass.length; i++) {
                    if (mass[i] > max) {
                        max = mass[i];
                        out = i;
                    }
                }
                System.out.printf("The number is %d",out);
                break;
        }
    }


    private static void NewWeights(neurWeights nW, weights wG, int nb){
        double le = 0.5;// learning coefficient
        double[][] buff = nW.getMas(0);
        for (int lnb = 0; lnb < wG.nbrs.length; lnb++){
            for (int i = 0; i < wG.nbrs[lnb].length; i++){
                for (int k = 0; k < wG.nbrs[lnb][i].length; k++){
                    buff[i][k] += le * ((wG.nbrs[lnb][i][k] == 1)? 1 : 0) * (((nb == lnb)? 1 : 0) - nW.getNeur()[nb][i][k]);
                }
            }
        }
        for (int i = 0; i < buff.length; i++) {
            for (int k = 0; k < buff[i].length; k++) {
                buff[i][k] /= wG.nbrs.length;
                nW.getNeur()[nb][i][k] += buff[i][k];
            }
        }
    }
}