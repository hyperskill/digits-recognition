package recognition;

public class RecognitionAlgorithm {
    private NeuronsNetwork network;
    private NerveEndings[] nerveEndings;
    private int NERVE_ENDINGS_NUM = 2;
    private static final int SIZE_SECOND_LAYER = 10;
    public RecognitionAlgorithm(NeuronsNetwork network)
    {
        this.network = network;
        nerveEndings = new NerveEndings[network.getLayer(NERVE_ENDINGS_NUM).getSize()];
        for (int i = 0; i < nerveEndings.length; i++)
        {
            nerveEndings[i] = (NerveEndings) network.getLayer(NERVE_ENDINGS_NUM).getNeuron(i);
        }
    }

    public int recognition(double[] digitHandWrite)
    {
        NeuronLayer firstLayer = network.getLayer(0);
        for(int i = 0; i < digitHandWrite.length; i++)
        {
            firstLayer.getNeuron(i).sendSignal(digitHandWrite[i]);
        }
        double max = 0;
        int digit=0;
        for(int i = 0; i <  SIZE_SECOND_LAYER; i++)
        {
             if(max < nerveEndings[i].getState())
             {
                 max = nerveEndings[i].getState();
                 digit = i;
             }
        }

        return digit;
    }
}
