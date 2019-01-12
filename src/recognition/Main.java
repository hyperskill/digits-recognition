package recognition;

import recognition.data.*;
import java.io.IOException;
import static recognition.Helper.*;

public class Main {
    public static void main(String[] args) {
        InputReader reader = new InputReader();
        GridRecognizer recognizer = new GridRecognizer();
        try {
            Grid grid = reader.read();

            InputLayer layer1 = new InputLayer();
            for (byte[] row : grid.getCells()) {
                for (byte cell : row) {
                    layer1.addNeuron(new Neuron(cell));
                }
            }

            OutputLayer layer2 = new OutputLayer(10);
            layer2.addWeights(w0);
            layer2.addWeights(w1);
            layer2.addWeights(w2);
            layer2.addWeights(w3);
            layer2.addWeights(w4);
            layer2.addWeights(w5);
            layer2.addWeights(w6);
            layer2.addWeights(w7);
            layer2.addWeights(w8);
            layer2.addWeights(w9);

            Network network = new Network();
            network.setInputLayer(layer1);
            network.setOutputLayer(layer2);

            recognizer.setNetwork(network);
            int num = recognizer.recognize();

            System.out.println("This number is " + num);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}