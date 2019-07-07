package recognition;

/**
 * Нейрон рецептор отправляет на выход то же самое что получил на входе
 *
 */

public class ReceptorNeuron implements Neuron {
    private Neuron[] neuronsOutput;
    private int countOutput;

    public ReceptorNeuron(int countInput, int countOutput) {
        neuronsOutput = new Neuron[countOutput];
    }

    @Override
    public void changeWeight(int num, double w) {

    }

    public void sendSignal(double x)
    {
        sendOutput(x);
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
