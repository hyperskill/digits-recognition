package recognition;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int[] weight = {2, 1, 2, 4, -4, 4, 2, -1, 2};
        String str = "";
        Scanner sc = new Scanner(System.in);
        str += sc.nextLine() + sc.nextLine() + sc.nextLine();
        int sum = -5;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '_')
                sum+= weight[i];
        }
        if (sum > 0)
            System.out.print("This is 0!");
        else
            System.out.println("This is 1!");
        //System.out.println(sum);
    }
}