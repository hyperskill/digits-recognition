package recognition;

public class Helper {
    public final static byte rowNum = 5;
    public final static byte colNum = 3;

    public final static int[] w1 = new int[]{-1, 1, -1, -1, 1, -1, -1, 1, -1, -1, 1, -1, -1, 1, -1};
    public final static int[] w2 = new int[]{1, 1, 1, -1, -1, 1, 1, 1, 1, 1, -1, -1, 1, 1, 1,};
    public final static int[] w3 = new int[]{1,1,1, -1,-1,1, 1,1,1, -1,-1,1, 1,1,1};
    public final static int[] w4 = new int[]{1,-1,1,  1,-1,1,  1,1,1,  -1,-1,1,  -1,-1,1};
    public final static int[] w5 = new int[]{1,1,1,  1,-1,-1,  1,1,1,  -1,-1,1,  1,1,1};
    public final static int[] w6 = new int[]{1,1,1,  1,-1,-1,  1,1,1,  1,-1,1,  1,1,1};
    public final static int[] w7 = new int[]{1,1,1,  -1,-1,1,  -1,-1,1,  -1,-1,1,  -1,-1,1};
    public final static int[] w8 = new int[]{1,1,1,  1,-1,1,  1,1,1,  1,-1,-1,  1,1,1};
    public final static int[] w9 = new int[]{1,1,1,  1,-1,1,  1,1,1,  -1,-1,1,  1,1,1};
    public final static int[] w0 = new int[]{1 ,1 ,1, 1, -1, 1, 1, -1, 1, 1, -1, 1, 1, 1, 1};

    public final static int[] bias = new int[]{-1, 6, 0, 0, 0, 0, -1, 4, -2, 0};
}
