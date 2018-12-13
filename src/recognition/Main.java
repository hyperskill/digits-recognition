package recognition;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Input 3x3 grid with space ('0' for white, '1' for black):");
        Scanner sc = new Scanner(System.in).useDelimiter("\\s+");
        int[] neurons = new int[9];

        for (int i = 0; i < 9; i++) {
            neurons[i] = sc.nextInt();
        }

        System.out.println("This number is " + isOneOrZero(neurons));
    }

    private static int isOneOrZero(int[] neurons) {
        int outputNeuron = 0;
        int[] weights = {2, 1, 2, 4, -4, 4, 2, -1, 2, -5};

        for (int i = 0; i < neurons.length; i++) {
            outputNeuron += neurons[i] * weights[i];
        }

        outputNeuron += weights[9];

        return outputNeuron > 0 ? 0 : 1;
    }
}
