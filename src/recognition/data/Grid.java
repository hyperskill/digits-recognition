package recognition.data;

import static recognition.Helper.*;

public class Grid {
    private byte[][] cells = new byte[rowNum][colNum];

    public void setCell(byte row, byte col, byte value) {
        cells[row][col] = value;
    }

    public byte getCell(byte row, byte col) {
        return cells[row][col];
    }

    public byte[][] getCells() {
        return cells;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (byte[] row : cells) {
            for (byte cell : row) {
                builder.append(cell);
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
