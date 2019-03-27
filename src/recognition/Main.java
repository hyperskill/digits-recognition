package recognition;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static final String NETWORK_OBJECT_FILENAME = "NetworkObj.ser";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Network network = readNetworkFile();

        while(true){
            System.out.println("\nWhat do you want?\n");
            System.out.println("1. Teach the network");
            System.out.println("2. Guess a number");
            System.out.println("3. Test network on ideal numbers");
            System.out.println("4. Save the network");
            System.out.println("5. Forget the network");
            System.out.println("6. Exit");
            System.out.print("\nYour choice: ");

            switch(scanner.nextInt()){
                case 1:{ // Teaching network
                    network.teachNetwork(1000);
                    break;
                }
                case 2:{ // Guess number
                    System.out.println("\nLooks like: " + network.guessTheResult(readInput()) + "\n");
                    break;
                }
                case 3:{ // Test network on ideal numbers
                    for(int number = 0; number < Numbers.idealInputNumbers.length; ++number){
                        System.out.println(Numbers.getIdealNumber(number) + "\n");
                        System.out.println("\nLooks like: " + network.guessTheResult(Numbers.idealInputNumbers[number]));
                        System.out.println("===============================================================");
                    }
                    break;
                }
                case 4:{ // Saving the network
                    writeNetworkFile(network);
                    break;
                }
                case 5:{ // Forget the network
                    System.out.println("\nForgetting the network..");
                    network = new Network();
                    removeNetworkFile();
                    System.out.println("Network successfully forgotten");
                    break;
                }
                case 6:{ // Exit
                    writeNetworkFile(network);
                    System.out.println("\nBye bye!");
                    System.exit(0);
                }
                default: // Wrong input
                    System.out.println("\nWrong input");
            }
        }
    }

    // Remove network serialized file
    private static void removeNetworkFile(){
        File file = new File(NETWORK_OBJECT_FILENAME);
        file.delete();
    }

    // Read network from file
    private static Network readNetworkFile() {
        Network n;

        try{
            FileInputStream fileInputStream = new FileInputStream(NETWORK_OBJECT_FILENAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            n = (Network) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();

            System.out.println("Network loaded and ready!");

        } catch (Exception e){
            System.out.println("Error reading network\nGenerating empty");
            n = new Network();
        }
        return n;
    }

    // Save network to file
    private static void writeNetworkFile(Network network) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(NETWORK_OBJECT_FILENAME);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(network);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();

            System.out.println("\nNetwork saved");
        } catch (Exception e){
            System.out.println("\nError saving network");
            e.printStackTrace();
        }
    }

    // Read number from console as 5x3 grid
    private static double[] readInput() {
        double[] a = new double[15];
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nInput 5x3 grid ('_' for white, any other for blue):");
        for(int i=0; i < 5; ++i){
            String str = scanner.nextLine();
            for(int j = 0; j < 3; ++j){
                a[3*i + j] =  (str.charAt(j) == '_') ? 0 : 1;
            }
        }

        System.out.println("\nInput:\n" + Arrays.toString(a) + "\n");

        return a;
    }
}