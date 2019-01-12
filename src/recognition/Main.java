package recognition;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        InputReader reader = new InputReader();
        GridRecognizer recognizer = new GridRecognizer();
        try {
            Grid grid = reader.read();
            int num = recognizer.recognize(grid);

            System.out.println("This number is " + num);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}