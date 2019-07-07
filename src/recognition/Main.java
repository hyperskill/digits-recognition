package recognition;


import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final int SIZE_FIRST_LAYER = 15;
    private static final int SIZE_SECOND_LAYER = 10;
    private static final int COUNT_OF_LAYERS = 3;

    /*
    Жестко зашиваем структуру сети в маин
     */
    public static NeuronsNetwork config()
    {
        NeuronsNetwork network = new NeuronsNetwork(COUNT_OF_LAYERS);
        NeuronLayer layer1 = new NeuronLayer(SIZE_FIRST_LAYER);
        NeuronLayer layer2 = new NeuronLayer(SIZE_SECOND_LAYER);

        NeuronLayer layerEndings = new NeuronLayer(SIZE_SECOND_LAYER);

        network.setLayer(layer1,0);
        network.setLayer(layer2, 1);
        network.setLayer(layerEndings, 2);

        for(int i = 0; i < SIZE_FIRST_LAYER; i++ )
        {
                layer1.setNeuron(new ReceptorNeuron(1, SIZE_SECOND_LAYER), i);
        }

        for(int i = 0; i < SIZE_SECOND_LAYER; i++)
        {
            layer2.setNeuron(new SigmoidNeuron(SIZE_FIRST_LAYER, 1),i);
        }



        for(int i = 0; i < SIZE_FIRST_LAYER; i++)
        {
            for(int j = 0; j < SIZE_SECOND_LAYER; j++)
            {
                layer1.getNeuron(i).addNeuronOutput(layer2.getNeuron(j), j);
            }
        }

        /*
          Создаем нервные окончания
         */

        for(int i = 0; i < SIZE_SECOND_LAYER; i++)
        {
            layerEndings.setNeuron( new NerveEndings(), i);
        }
        for(int i = 0; i < SIZE_SECOND_LAYER; i++)
        {
            layer2.getNeuron(i).addNeuronOutput(layerEndings.getNeuron(i),0);
        }
        return network;
    }

    public static void main(String[] args) throws IOException {
        NeuronsNetwork network = Main.config();
        while(true) {
            System.out.println("Input 1 for Training And 2 for Recognize");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.next();

            switch (line) {
                case "1":
                    System.out.println("Processing...");
                    LearningAlgorithm learningAlgorithm = new LearningAlgorithm(network);
                    for(int i =0 ; i<1000; i++) {
                        learningAlgorithm.sendDigit(i%10, IdealNumbers.IDEAL_INPUT_NUMBERS[i%10]);
                    }
                    learningAlgorithm.changeState();
                    break;

                case "2":
                    System.out.println("Recognize...");
                    double[] mask = new double[15];
                    RecognitionAlgorithm recognitionAlgorithm = new RecognitionAlgorithm(network);

                    byte counter = 0;
                    while ((counter != 15) && (scanner.hasNext())) {
                        String str = scanner.next();
                        for (int i = 0; i < str.length(); i++) {
                            if (str.charAt(i) != ' ') {
                                if (str.charAt(i) == '_')
                                    mask[counter++] = 0;
                                else
                                    mask[counter++] = 1;

                            }

                        }
                    }
                    System.out.println("Your digit:"+ recognitionAlgorithm.recognition(mask));
                    break;

                default:
                    break;
            }





        }
    }

}