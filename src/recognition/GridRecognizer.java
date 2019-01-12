package recognition;

public class GridRecognizer {
    private int[][] weights = new int[][]{{2, 1, 2}, {4, -4, 4}, {2, -1, 2}};
    private int bias = -5;

    public int recognize(Grid grid) {
        int sum = 0;
        for (byte row = 0; row < weights.length; row++) {
            for (byte col = 0; col < weights[row].length; col++) {
                sum += weights[row][col] * grid.getCell(row, col);
            }
        }

        return (sum + bias) < 0 ? 1 : 0;
    }
}
