package recognition;

import recognition.data.Network;
import recognition.data.Neuron;
import java.util.List;

public class GridRecognizer {
    private Network network;

    public void setNetwork(Network network) {
        this.network = network;
    }

    public int recognize() {
        network.evaluateOutput();
        List<Neuron> neurons = network.getOutputLayer().getNeurons();
        int max = 0;
        int index = 0;
        for (int i = 0; i < neurons.size(); i++) {
            if (neurons.get(i).getValue() > max) {
                max = neurons.get(i).getValue();
                index = i;
            }
        }
        return index;
    }
}
