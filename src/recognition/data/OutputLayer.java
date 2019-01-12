package recognition.data;

import java.util.ArrayList;
import java.util.List;

public class OutputLayer implements Layer {
    private List<Neuron> neurons = new ArrayList<>();
    private List<int[]> weights = new ArrayList<>();

    public OutputLayer() {
    }

    public OutputLayer(int neuronsNum) {
        for (int i = 0; i < neuronsNum; i++) {
            addNeuron(new Neuron(0));
        }
    }

    public void addNeuron(Neuron neuron) {
        neurons.add(neuron);
    }

    public int size() {
        return neurons.size();
    }

    public void addWeights(int[] w) {
        weights.add(w);
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    public List<int[]> getWeights() {
        return weights;
    }
}
