package recognition;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class LearningAlgorithm {

    private NeuronsNetwork network;
    private double learningCoefficient = 0.5;
    private static final int SIZE_FIRST_LAYER = 15;
    private static final int SIZE_SECOND_LAYER = 10;
    private int NERVE_ENDINGS_NUM = 2;
    private NerveEndings[] nerveEndings;
    private double[][] deltaWeigth;
    private int numbOfTraining = 0;

    LearningAlgorithm(NeuronsNetwork network)
    {
        this.network = network;
         nerveEndings = new NerveEndings[network.getLayer(NERVE_ENDINGS_NUM).getSize()];
        for (int i = 0; i < nerveEndings.length; i++)
        {
            nerveEndings[i] = (NerveEndings) network.getLayer(NERVE_ENDINGS_NUM).getNeuron(i);
        }
        deltaWeigth = new double[SIZE_SECOND_LAYER][SIZE_FIRST_LAYER];

    }

    public void sendDigit(int digitRight, double[] digitHandWrite)
    {
        NeuronLayer firstLayer = network.getLayer(0);

        for(int i = 0; i < digitHandWrite.length; i++)
        {
            firstLayer.getNeuron(i).sendSignal(digitHandWrite[i]);
        }

        for(int i = 0; i <  SIZE_SECOND_LAYER; i++)
        {
            for(int j =0; j < SIZE_FIRST_LAYER; j++)
            {
               deltaWeigth[i][j] +=learningCoefficient*digitHandWrite[j]*
                       (IdealNumbers.IDEAL_OUTPUT_NUMBERS[digitRight][i] - nerveEndings[i].getState());

            }

        }
        numbOfTraining++;
    }

    public void changeState() throws IOException {
        NeuronLayer layer = network.getLayer(1);
        for(int i = 0; i <  SIZE_SECOND_LAYER; i++) {
            for (int j = 0; j < SIZE_FIRST_LAYER; j++) {
                layer.getNeuron(i).changeWeight(j, layer.getNeuron(i).getWeigth(j)+ deltaWeigth[i][j]/numbOfTraining) ;
            }
        }
        FileOutputStream fileOut = new FileOutputStream("network");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(network);
        objectOut.close();
        System.out.println("The Network was succesfully written to a file");
    }
}
