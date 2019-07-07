package recognition;

import java.io.Serializable;

public class NeuronLayer implements Serializable {
    private Neuron neurons[];

    NeuronLayer(int countNeurons)
    {
        neurons = new Neuron[countNeurons];
    }

    public int getSize()
    {
        return neurons.length;
    }

    public Neuron getNeuron(int num)
    {
        return neurons[num];
    }

    public void setNeuron(Neuron neuron, int num)
    {
        neurons[num] = neuron;
    }
}
