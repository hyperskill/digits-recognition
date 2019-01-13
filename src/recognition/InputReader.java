package recognition;

import recognition.data.Grid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static recognition.Helper.*;

public class InputReader {
    public Grid read() throws IOException {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            Grid grid = new Grid();

            System.out.println("Input " + rowNum + "x" + colNum +" grid");
            for (byte row = 0; row < rowNum; row++) {
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
