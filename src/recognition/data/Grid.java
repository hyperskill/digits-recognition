package recognition.data;

import static recognition.Helper.*;

public class Grid {
    private double[][] cells = new double[rowNum][colNum];

    public void setCell(byte row, byte col, byte value) {
        cells[row][col] = value;
    }

    public double getCell(byte row, byte col) {
        return cells[row][col];
    }

    public double[][] getCells() {
        return cells;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (double[] row : cells) {
            for (double cell : row) {
                builder.append(cell);
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
