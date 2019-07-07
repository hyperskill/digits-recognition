package recognition;

import java.io.Serializable;

public interface Neuron extends Serializable {

    void changeWeight(int num, double w);
    public void sendSignal(double x);
    public void addNeuronOutput(Neuron neuron, int num);
    public double getWeigth(int num);


}
