package recognition;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputReader {
    public Grid read() throws IOException {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            Grid grid = new Grid();

            System.out.println("Input 3x3 grid");
            for (byte row = 0; row < 3; row++) {
                String line = reader.readLine();

                if (line.matches("^[\\w]{3}$")) {
                    for (byte col = 0; col < line.length(); col++) {
                        grid.setCell(row, col, line.charAt(col) != '_' ? (byte) 1 : 0);
                    }
                }
                else {
                    throw new IllegalArgumentException("Illegal input");
                }
            }

            return grid;
        }
    }
}
