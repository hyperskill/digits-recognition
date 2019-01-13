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

    public final static double[] bias = new double[]{0, -1, 6, 0, 0, 0, 0, -1, 4, -2};

    public final static double mu = 0.5;

    public final static double[] input0 = new double[]{1,1,1,  1,0,1,  1,0,1,  1,0,1,  1,1,1};
    public final static double[] input1 = new double[]{0,1,0,  0,1,0,  0,1,0,  0,1,0,  0,1,0};
    public final static double[] input2 = new double[]{1,1,1,  0,0,1,  1,1,1,  1,0,0,  1,1,1};
    public final static double[] input3 = new double[]{1,1,1,  0,0,1,  1,1,1,  0,0,1,  1,1,1};
    public final static double[] input4 = new double[]{1,0,1,  1,0,1,  1,1,1,  0,0,1,  0,0,1};
    public final static double[] input5 = new double[]{1,1,1,  1,0,0,  1,1,1,  0,0,1,  1,1,1};
    public final static double[] input6 = new double[]{1,1,1,  1,0,0,  1,1,1,  1,0,1,  1,1,1};
    public final static double[] input7 = new double[]{1,1,1,  0,0,1,  0,0,1,  0,0,1,  0,0,1};
    public final static double[] input8 = new double[]{1,1,1,  1,0,1,  1,1,1,  1,0,1,  1,1,1};
    public final static double[] input9 = new double[]{1,1,1,  1,0,1,  1,1,1,  0,0,1,  1,1,1};

    public final static double[][] inputs
            = new double[][] {input0, input1, input2, input3, input4, input5, input6, input7, input8, input9};

    public final static double[] out0 = new double[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public final static double[] out1 = new double[]{0, 1, 0, 0, 0, 0, 0, 0, 0, 0};
    public final static double[] out2 = new double[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 0};
    public final static double[] out3 = new double[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 0};
    public final static double[] out4 = new double[]{0, 0, 0, 0, 1, 0, 0, 0, 0, 0};
    public final static double[] out5 = new double[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
    public final static double[] out6 = new double[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 0};
    public final static double[] out7 = new double[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0};
    public final static double[] out8 = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 0};
    public final static double[] out9 = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 1};

    public final static double[][] outputs
            = new double[][] {out0, out1, out2, out3, out4, out5, out6, out7, out8, out9};

}
