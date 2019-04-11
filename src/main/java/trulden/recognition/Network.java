package trulden.recognition;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Network implements Serializable {

    // Number of layers in network
    private final int numberOfLayers;

    // Number of neurons at every layer of network
    private final int[] layerSizes;

    // Values of neurons
    private transient double[][] layers;

    // Ideal values of neurons
    private transient double[][] idealLayers;

    // Weights of neuron connections
    private double[][][] weights;

    // Learning coefficient
    private double n = 0.5;

    // Do we want to print intermediate results?
    private boolean outputMidResults = true;

    // Generate default network
    public Network(){
        this(new int[] {15, 20, 10});
    }

    // Generate network with given layers sizes
    public Network(int[] layerSizes){
        if(layerSizes == null           // Sizes array must contain something
           || layerSizes.length < 2     // Actually, at least two values
           || Arrays.stream(layerSizes)
                .filter(x -> x <= 0)    // And they must be bigger than zero
                .count() > 0){
            throw new IllegalArgumentException("Wrong layer sizes");
        }

        this.layerSizes = layerSizes;
        numberOfLayers = layerSizes.length;

        weights = initWeightMatrix(true);
    }

    // Calculate output layer from given input
    private double[] calculateOutput(double[] input){
        if(input.length != layerSizes[0])
            throw new IllegalArgumentException("Wrong input size");

        layers = generateLayers();

        layers[0] = input;

        for(int midLayer = 0; midLayer < weights.length; ++midLayer){
            for(int rightNeuron = 0; rightNeuron < layers[midLayer+1].length; ++rightNeuron){

                // Right neuron value = sum of products of left neuron value and corresponding weight
                for(int leftNeuron = 0; leftNeuron < layers[midLayer].length; ++leftNeuron){
                    layers[midLayer+1][rightNeuron] += layers[midLayer][leftNeuron] * weights[midLayer][leftNeuron][rightNeuron];
                }

                // Normalize neuron
                layers[midLayer+1][rightNeuron] = sigmoid(layers[midLayer+1][rightNeuron]);
            }
        }

        if(outputMidResults){
            System.out.println("Output layer:");
            IntStream.range(0, 10).forEach(x -> System.out.printf("%2d  ", x));
            System.out.println();
            Arrays.stream(layers[numberOfLayers - 1]).mapToInt(x -> (int)(100*x)).forEach(x -> System.out.printf("%2d  ", x));
        }

        return layers[numberOfLayers-1];
    }

    private double[][] generateLayers() {
        double[][] layers = new double[numberOfLayers][];

        // Initialize every layer
        for(int layer = 0; layer < numberOfLayers; ++layer){
            layers[layer] = new double[layerSizes[layer]];
        }

        return layers;
    }

    // Sigmoid normalization
    public static double sigmoid(double v) {
        return 1d / (1d + Math.exp(-v));
    }

    // Guess the number from given input
    public int guessTheResult(double[] input){
        int lastLayer = numberOfLayers - 1;

        // Most similar number
        int nearestNumber = 0;

        // Biggest neuron value at last layer
        double bestResult = 0d;

        // Calculate last layer
        calculateOutput(input);

        // Find neuron with max value -- it's probably the right number
        for (int neuron = 0; neuron < layerSizes[lastLayer]; ++neuron){
            if(layers[lastLayer][neuron] > bestResult){
                nearestNumber = neuron;
                bestResult = layers[lastLayer][neuron];
            }
        }

        return nearestNumber;
    }

    // Teach network by back-propagation once
    public void teachNetwork(int iterations){

        System.out.println("\nTeaching network...");

        outputMidResults = false;

        for(int iteration = 0; iteration < iterations; ++iteration){

            double[][][] deltaWeights = initWeightMatrix(false);
            idealLayers = generateLayers();

            // Calculate delta from every possible option
            for(int number = 0; number < Numbers.idealInputNumbers.length; ++number){
                calculateOutput(Numbers.idealInputNumbers[number]);

                for (int midLayer = weights.length - 1; midLayer >= 0; --midLayer) {

                    idealLayers[midLayer+1] = (midLayer == weights.length-1) ?
                            Numbers.idealOutputNumbers[number] :
                            calculateIdealOutput(weights[midLayer+1], idealLayers[midLayer+2]);

                    double[] difference = calculateDifference(idealLayers[midLayer+1], layers[midLayer+1]);

                    for (int leftNeuron = 0; leftNeuron < layerSizes[midLayer]; ++leftNeuron) {
                        for (int rightNeuron = 0; rightNeuron < layerSizes[midLayer+1]; ++rightNeuron) {
                            deltaWeights[midLayer][leftNeuron][rightNeuron] += n * layers[midLayer][leftNeuron] * difference[rightNeuron];
                        }
                    }
                }

            }

            // Apply delta
            for(int i = 0; i < weights.length; ++i) {
                for (int leftNeuron = 0; leftNeuron < layerSizes[i]; ++leftNeuron) {
                    for (int rightNeuron = 0; rightNeuron < layerSizes[i+1]; ++rightNeuron) {
                        weights[i][leftNeuron][rightNeuron] += (deltaWeights[i][leftNeuron][rightNeuron] / Numbers.idealInputNumbers.length);
                    }
                }
            }
        }

        outputMidResults = true;

        System.out.println("\nTeaching finished!");
    }

    // Create empty or gaussian-filled weight matrices
    private double[][][] initWeightMatrix(boolean needNumbers) {
        double[][][] w = new double[numberOfLayers - 1][][];
        Random random = new Random();

        for(int midLayer = 0; midLayer < numberOfLayers - 1; ++midLayer){
            w[midLayer] = new double[layerSizes[midLayer]][layerSizes[midLayer+1]];

            for(int i = 0; i < layerSizes[midLayer]; ++i){
                for(int j = 0; j < layerSizes[midLayer+1]; ++j){
                    w[midLayer][i][j] = needNumbers ? random.nextGaussian() : 0d;
                }
            }
        }

        return w;
    }

    // Calculate difference between given double arrays
    private static double[] calculateDifference(double[] ideal, double[] current){
        if(ideal == null || current == null || ideal.length!=current.length)
            throw new IllegalArgumentException();

        double[] difference = new double[ideal.length];

        for(int i = 0; i < difference.length; ++i){
            difference[i] = ideal[i] - current[i];
        }

        return difference;
    }

    // Calculate ideal output for layer with given weights and next ideal layer
    private static double[] calculateIdealOutput(double[][] weights, double[] rightIdeal) {
        double[] leftIdeal = new double[weights.length];

        // lw=r → l=r/w

        for(int leftNeuron = 0; leftNeuron < leftIdeal.length; ++leftNeuron){
            double sum = 0d;
            for(int rightNeuron = 0; rightNeuron < rightIdeal.length; ++rightNeuron){
                sum += rightIdeal[rightNeuron]/weights[leftNeuron][rightNeuron];
            }
            leftIdeal[leftNeuron] = sigmoid( sum / (1d * rightIdeal.length) );
        }

        return leftIdeal;
    }
}
