package recognition;

import java.util.ArrayList;
import java.util.Random;

public class SigmoidNeuron implements Neuron {

    private double [] weightInput;
    private Neuron[] neuronsOutput;
    private ArrayList<Double> signalsInput;
    private double calculatedOutput;

    public SigmoidNeuron(int countInput, int countOutput)
    {
        signalsInput = new ArrayList<>();
        neuronsOutput = new Neuron[countOutput];

        weightInput = new double [countInput];
        Random random = new Random();
        for(int i = 0; i < weightInput.length; i++ )
        {
            weightInput[i] = random.nextGaussian();
        }

    }

    public void changeWeight(int num, double w)
    {
        weightInput[num] = w;
    }

    public void sendSignal(double x)
    {
        signalsInput.add(x);
        //если получили от всех входных нейронов сигналы, то взвешиваем пропускаем через функцию активации и подаем на выход
        if(signalsInput.size()==weightInput.length)
        {
            for(int i = 0; i < signalsInput.size(); i++)
            {
                calculatedOutput += signalsInput.get(i)*weightInput[i];
            }
            signalsInput.clear();
            sendOutput(functionActivation(calculatedOutput));
        }
    }

    @Override
    public void addNeuronOutput(Neuron neuron, int num) {
        this.neuronsOutput[num] = neuron;
    }

    @Override
    public double getWeigth(int num) {
        return weightInput[num];
    }


    private double functionActivation(double x)
    {
        return 1/(1+Math.exp(-x));
    }

    private void sendOutput(double x)
    {
        for(int i =0; i < neuronsOutput.length; i++)
        {
            neuronsOutput[i].sendSignal(x);
        }
    }
}
