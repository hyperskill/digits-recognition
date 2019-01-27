
import java.util.*;

public class Main {

    public static void greetings() {
        System.out.println("Hello, dear guest!");
        System.out.println("If you want to exit the program, type \"exit\".");
    }

    public static int[] readInput(Scanner scanner) {
        int[] answer = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
        System.out.println("Input 3x3 grid ('_' for white, any other for blue):");
        String line;
        line = scanner.nextLine();
        if (!line.startsWith("/")) {
            answer = inputToNumbers(scanner, line);
        }
        return answer;
    }

    public static int[] inputToNumbers(Scanner scanner, String firstLine){
        int[] numbers = new int[9];
        for (int j=0; j<3; j++) {
            char c = firstLine.charAt(j);
            if (c == '_') {
                numbers[j] = 0;
            } else {
                numbers[j] = 1;
            }
        }

        String line;
        for (int i=1; i<3; i++){
            line = scanner.nextLine();
            for (int j=0; j<3; j++) {
                char c = line.charAt(j);
                if (c == '_') {
                    numbers[i*3 + j] = 0;
                } else {
                    numbers[i*3 + j] = 1;
                }
            }
        }

        return numbers;
    }

    public static int distinguishZeroAndOne(int[] numbers) {
        int[] weights = {2, 1, 2, 4, -4, 4, 2, -1, 2};
        int bias = -5;
        int answer = 0;
        for (int i=0; i<9; i++) {
            answer += weights[i] * numbers[i];
        }
        answer += bias;
        if (answer > 0) {
            return 0;
        } else return 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        greetings();
        while (true) {
            int[] userInput = readInput(scanner);
            if (userInput[0] != -1) {
                int number = distinguishZeroAndOne(userInput);
                System.out.printf("This answer is %d%n", number);
            } else {
                System.out.println("You tried to invoke custom commad. Sorry, but I haven't implemented it yet. Bye!");
                break;
            }
        }
    }
}