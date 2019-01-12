package recognition;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int[] mass = {-1, 6, 0, 0, 0, 0, -1, 4, -2, -1};
        weights weight = new weights();
        Scanner sc = new Scanner(System.in);
        String[] str = new String[5];
        for (int i = 0; i < 5; i++) {
            str[i] = sc.nextLine();
        }
        for (int nb = 0; nb < mass.length; nb++) {
            for (int i = 0; i < 5; i++) {
                for (int k = 0; k < 3; k++){
                    if (str[i].charAt(k) == 'X'){
                        mass[nb] += weight.nbrs[nb][i][k];
                    }
                }
            }
        }
        int out = 0;
        int max = 0;
        for (int i = 0; i < mass.length; i++){
            if (mass[i] > max){
                max = mass[i];
                out = i;
            }
        }
        System.out.printf("This is %d !!!", out);

    }
}