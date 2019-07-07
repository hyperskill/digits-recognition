package recognition;

public class NerveEndings implements Neuron {
    private double state = 0;
    @Override
    public void changeWeight(int num, double w) {}

    @Override
    public void sendSignal(double x) {
        this.state = x;
    }

    public double getState()
    {
        return state;
    }

    @Override
    public void addNeuronOutput(Neuron neuron, int num) {}

    @Override
    public double getWeigth(int num) {
        return 0;
    }

}
