package recognition;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static final int[] w = {2, 1, 2, 4, -4, 4, 2, -1, 2};
    static final int   b = -5;

    public static void main(String[] args) {
        int[] a = new int[9];
        Scanner scanner = new Scanner(System.in);
        int result = 0;

        System.out.println("Input 3x3 grid ('_' for white, any other for blue):");

        for(int i=0; i < 3; ++i){
            String str = scanner.nextLine();
            for(int j = 0; j < 3; ++j){
                a[3*i + j] =  (str.charAt(j) == '_') ? 0 : 1;
            }
        }

        System.out.println("\nInput:\n" + Arrays.toString(a) + "\n");

        for(int i = 0; i < 9; ++i){
            result += (a[i] * w[i]);
        }
        result += b;

        System.out.println("This is number " + (result < 0 ? "1" : "0"));
    }
}