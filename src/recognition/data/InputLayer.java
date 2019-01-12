package recognition.data;

import java.util.ArrayList;
import java.util.List;

public class InputLayer implements Layer{
    private List<Neuron> neurons = new ArrayList<>();

    public InputLayer() {
    }

    public InputLayer(int neuronsNum) {
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

    public List<Neuron> getNeurons() {
        return neurons;
    }
}
