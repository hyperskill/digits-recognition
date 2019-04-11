package trulden.recognition;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class NetworkTest {

    static Network network;

    @BeforeAll
    public static void init(){
        network = new Network();
    }

    // Test of basic functions
    @Test
    public void doesAtLeastRun(){
        Main main = new Main();

        main.forgetTheNetwork();

        main.teachNetwork();

        main.testNetworkOnIdealNumbers();
    }

    @Test
    void calculateIdealOutputTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        double[] right = new double[] {0.3, 0.2, 0.1};
        double[][] weight = new double[][] {{0.2, 0.1, 0.7}, {1.2, 2.1, 1.1}};

        double[] expect = new double[] {Network.sigmoid((0.3/0.2 + 0.2/0.1 + 0.1/0.7) / 3d), Network.sigmoid((0.3/1.2 + 0.2/2.1 + 0.1/1.1) / 3d)};

        Method calculateIdealOutput = network.getClass().getDeclaredMethod("calculateIdealOutput", double[][].class, double[].class);
        calculateIdealOutput.setAccessible(true);

        double[] got    = (double[]) calculateIdealOutput.invoke(network, weight, right);

        System.out.println("Expect: " + Arrays.toString(expect) + "\nGot:    " + Arrays.toString(got));

        assertArrayEquals(expect, got);
    }

    @Test
    void calculateDifferenceTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method calculateDifference = network.getClass().getDeclaredMethod("calculateDifference", double[].class, double[].class);
        calculateDifference.setAccessible(true);

        assertArrayEquals(new double[]{-0.5, 0.9}, (double[]) calculateDifference.invoke(network, new double[]{0.2, 1.1}, new double[]{0.7, 0.2}), 0.00001);
    }
}