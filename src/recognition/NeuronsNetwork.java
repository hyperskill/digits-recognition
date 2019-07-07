package recognition;

import java.io.Serializable;

public class NeuronsNetwork implements Serializable {
    private NeuronLayer[] layers;


    public NeuronsNetwork(int countOfLayers)
    {
        layers = new NeuronLayer[countOfLayers];
    }

    NeuronLayer getLayer(int num)
    {
        return layers[num];
    }

    public void setLayer(NeuronLayer layer, int num)
    {
        layers[num] = layer;
    }
}
