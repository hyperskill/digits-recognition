package recognition;

public class Grid {
    private byte[][] cells = new byte[3][3];

    public void setCell(byte row, byte col, byte value) {
        cells[row][col] = value;
    }

    public byte getCell(byte row, byte col) {
        return cells[row][col];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (byte[] cell : cells) {
            for (byte aCell : cell) {
                builder.append(aCell);
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
