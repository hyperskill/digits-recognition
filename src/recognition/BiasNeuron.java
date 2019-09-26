package recognition;

public class BiasNeuron implements Neuron {
    private Neuron[] neuronsOutput;



    public BiasNeuron(int countInput, int countOutput) {
        neuronsOutput = new Neuron[countOutput];
    }

    @Override
    public void changeWeight(int num, double w) {

    }

    public void sendSignal(double x)
    {
        sendOutput(1);
    }

    @Override
    public void addNeuronOutput(Neuron neuron, int num) {
        this.neuronsOutput[num] = neuron;
    }

    @Override
    public double getWeigth(int num) {
        return 0;
    }


    private void sendOutput(double x)
    {
        for(int i =0; i < neuronsOutput.length; i++)
        {
            neuronsOutput[i].sendSignal(x);
        }
    }

}
