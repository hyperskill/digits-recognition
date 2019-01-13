package recognition.model;

import recognition.Helper;
import recognition.InputReader;
import recognition.data.Grid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static recognition.Helper.*;

public class Network {
    private List<double[]> layers = new ArrayList<>();
    private List<double[][]> weightsMatrixes= new ArrayList<>();
    private List<double[]> biaseList = new ArrayList<>();
    private Random random = new Random();

    public Network(int ... layerSizes) {
        for (int size : layerSizes) {
            layers.add(new double[size]);
        }
        for (int i = 1; i < layers.size(); i++) {
            double[][] w = new double[layers.get(i).length][layers.get(i-1).length];
            double[] bias = new double[layers.get(i).length];
            for (int row = 0; row < w.length; row++) {
                for (int col = 0; col < w[row].length; col++) {
                    w[row][col] = random.nextGaussian();
                }
            }
            weightsMatrixes.add(w);

            for (int b = 0; b < bias.length; b++) {
                bias[b] = random.nextGaussian();
            }
            biaseList.add(bias);
        }
    }

    public static void main(String[] args) throws IOException {
        Network network = new Network(15, 10);
        //double[] d = network.getLayerOutputs(network.layers.get(0), network.weightsMatrixes.get(0), network.biaseList.get(0));

        network.startLearning(100000, inputs, outputs);

        double[][] weights = network.weightsMatrixes.get(0);
        for (double[] row : weights) {
            //System.out.println(Arrays.toString(row));
        }

        InputReader reader = new InputReader();
        Grid grid = reader.read();

        DoubleStream input = Stream.of(grid.getCells()).flatMapToDouble(Arrays::stream);
        double[] res = network.getLayerOutputs(input.toArray(), network.weightsMatrixes.get(0), network.biaseList.get(0));

        System.out.println(Arrays.toString(res));
    }

    public void startLearning(int iterations, double[][] inputs, double [][] ideals) {
        for (int i = 0; i < iterations; i++) {
            updateWeightsMatrix(inputs, ideals);
        }
    }

    private void updateWeightsMatrix(double[][] inputs, double [][] ideals) {
        double[][] deltas = getWmeanMatrix(inputs, ideals);
        for (int k = 0; k < deltas.length; k++) {
            for (int n = 0; n < deltas[k].length; n++) {
                weightsMatrixes.get(0)[k][n] += deltas[k][n];
            }
        }
    }

    private double[][] getWmeanMatrix(double[][] inputs, double [][] ideals) {
        double [][] matrix = new double[layers.get(1).length][layers.get(0).length];
        for (int i = 0; i< inputs.length; i++) {
            double[][] deltas = getDeltasMatrixForInput(inputs[i], ideals[i]);
            for (int k = 0; k < matrix.length; k++) {
                for (int n = 0; n < matrix[k].length; n++) {
                    matrix[k][n] += deltas[k][n];
                }
            }
        }

        for (int k = 0; k < matrix.length; k++) {
            for (int n = 0; n < matrix[k].length; n++) {
                matrix[k][n] = matrix[k][n] / inputs.length;
            }
        }
        return matrix;
    }

    private double[][] getDeltasMatrixForInput(double[] inputs, double[] idealOutputs) {
        double[][] matrix = new double[idealOutputs.length][inputs.length];

        double[] outputs = getLayerOutputs(inputs, weightsMatrixes.get(0), biaseList.get(0));
        for (int i = 0; i< outputs.length; i++) {
            double[] w = getDeltasWforNeuron(inputs, outputs[i], idealOutputs[i]);
            matrix[i] = w;
        }
        return matrix;
    }

    private double[] getDeltasWforNeuron(double[] inputs, double output, double idealOutput) {
        double[] deltas = new double[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            deltas[i] = getDeltaWforNeuron(inputs[i], output, idealOutput);
        }
        return deltas;
    }

    private double getDeltaWforNeuron(double input, double output, double idealOutput) {
        return mu * input * (idealOutput - output);
    }

    private double[] getLayerOutputs(double[] inputs, double[][] weightsMatrix, double[] biases) {
        double[] outputs = new double[weightsMatrix.length];
        for (int i = 0; i< weightsMatrix.length; i++) {
            outputs[i] = getNeuronOutput(inputs, weightsMatrix[i], biases[i]);
        }
        return outputs;
    }

    private double getNeuronOutput(double[] inputs, double[] weights, double bias) {
        double result = 0;
        for (int i = 0; i < inputs.length; i++) {
            result += inputs[i] * weights[i];
        }
        return sigmoid(result + bias);
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
}
