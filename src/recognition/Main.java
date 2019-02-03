package recognition;

import java.util.Scanner;



public class Main {

    public static int[][] w = {
            {1,1,1,1,-1,1,1,-1,1,1,-1,1,1,1,1,-1},
            {-1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,0,6},
            {1,1,1,-1,-1,1,1,1,1,1,-1,-1,1,1,1,0},
            {1,1,1,-1,-1,1,1,1,1,-1,-1,1,1,1,1,0},
            {1,-1,1,1,-1,1,1,1,1,-1,-1,1,-1,-1,1,2},
            {1,1,1,1,-1,-1,1,1,1,-1,-1,1,1,1,1,0},
            {1,1,1,1,-1,-1,1,1,1,1,-1,1,1,1,1,-1},
            {1,1,1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,1,4},
            {1,1,1,1,-1,1,1,1,1,1,-1,1,1,1,1,-2},
            {1,1,1,1,-1,1,1,1,1,-1,-1,1,1,1,1,-1}
    };

    public static void main(String[] args) {
        System.out.println("Input 3x5 grid ('_' for white, any other for blue):");

        int[] sum = new int[10];
        byte[] mask = new byte[15];


        Scanner scanner = new Scanner(System.in);
        byte counter = 0;
        while((counter!=15)&&(scanner.hasNext()))
        {
           String str = scanner.next();
           for(int i = 0; i< str.length(); i++)
           {
               if(str.charAt(i)!=' ')
               {
                   if(str.charAt(i)=='_')
                       mask[counter++]=0;
                   else
                       mask[counter++]=1;

               }

           }
        }

        for(int j =0 ; j < 10; j++) {
            for (int i = 0; i < 16; i++) {
                if(i<15)
                    sum[j] += mask[i] * w[j][i];
                else
                    sum[j] +=w[j][i];
            }
        }

        int max = 0;
        for(int i = 0; i < 10; i++)
        {
            if(sum[i]>sum[max])
            {
                max = i;
            }
        }

        System.out.println(max);
    }
}