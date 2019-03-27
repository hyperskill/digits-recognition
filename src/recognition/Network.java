package recognition;

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

    // Difference between last layer and ideal result
    private transient double[] lastLayerDifference;

    // Weights of neuron connections
    private double[][][] weights;

    // Learning coefficient
    private double n = 0.5;

    // Do we want to print intermediate results?
    private boolean outputMidResults = true;

    // Generate default network
    public Network(){
        this(new int[] {15, 10});
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

        layers = new double[numberOfLayers][];

        layers[0] = input;

        // Initialize every layer
        for(int layer = 1; layer < numberOfLayers; ++layer){
            layers[layer] = new double[layerSizes[layer]];
        }

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

    // Sigmoid normalization
    private double sigmoid(double v) {
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

            // Calculate delta from every possible option
            for(int number = 0; number < Numbers.idealInputNumbers.length; ++number){
                calculateOutput(Numbers.idealInputNumbers[number]);
                calculateDifference(number);

                // Only for stage 3
                // For more than one layer, there will be another way of teaching
                for(int leftNeuron = 0; leftNeuron < layerSizes[0]; ++leftNeuron){
                    for(int rightNeuron = 0; rightNeuron < layerSizes[1]; ++rightNeuron){
                        deltaWeights[0][leftNeuron][rightNeuron] += n * layers[0][leftNeuron] * lastLayerDifference[rightNeuron];
                    }
                }
            }

            // Apply delta
            for(int leftNeuron = 0; leftNeuron < layerSizes[0]; ++leftNeuron){
                for(int rightNeuron = 0; rightNeuron < layerSizes[1]; ++rightNeuron) {
                    weights[0][leftNeuron][rightNeuron] += deltaWeights[0][leftNeuron][rightNeuron] / Numbers.idealInputNumbers.length;
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

    // Calculate difference between given output layer and ideal output layer
    private void calculateDifference(int number) {
        lastLayerDifference = new double[layerSizes[numberOfLayers-1]];

        for(int i = 0; i < lastLayerDifference.length; ++i){
            lastLayerDifference[i] = Numbers.idealOutputNumbers[number][i] - layers[numberOfLayers-1][i];
        }
    }
}
