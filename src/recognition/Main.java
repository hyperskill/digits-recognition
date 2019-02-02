package recognition;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Input 3x3 grid ('_' for white, any other for blue):");
        int[] w = { 2,1,2,4, -4, 4 , 2, -1, 2,-5};
        int sum = 0;
        byte[] mask = new byte[9];


        Scanner scanner = new Scanner(System.in);
        byte counter = 0;
        while((counter!=9)&&(scanner.hasNext()))
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
        for(int i = 0; i < 9; i++)
        {
            sum += mask[i]*w[i];
        }
        sum+=w[9];
        System.out.println(sum>=0?0:1);
    }
}