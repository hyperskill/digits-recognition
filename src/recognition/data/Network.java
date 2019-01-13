package recognition.data;

import java.util.List;
import static recognition.Helper.*;

public class Network {
    private InputLayer inputLayer;
    private OutputLayer outputLayer;

    public Network() {
    }

    public InputLayer getInputLayer() {
        return inputLayer;
    }

    public void setInputLayer(InputLayer inputLayer) {
        this.inputLayer = inputLayer;
    }

    public OutputLayer getOutputLayer() {
        return outputLayer;
    }

    public void setOutputLayer(OutputLayer outputLayer) {
        this.outputLayer = outputLayer;
    }

    public void evaluateOutput() {
        List<Neuron> neurons = outputLayer.getNeurons();
        for (int i = 0; i < neurons.size(); i++) {
            int value = calculateValue(inputLayer.getNeurons(), outputLayer.getWeights().get(i));
            //neurons.get(i).setValue(value - bias[i]);
        }
    }

    private int calculateValue(List<Neuron> neurons, int[] weights) {
        int sum = 0;
        for (int i = 0; i < neurons.size(); i++) {
            sum += neurons.get(i).getValue() * weights[i];
        }
        return sum;
    }
}
