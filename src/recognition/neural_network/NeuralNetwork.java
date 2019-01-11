package recognition.neural_network;

import java.io.*;

public class NeuralNetwork implements Serializable {

    private Layer[] layers;
    private int[] layersSizes;
    private Training training;


    /**
     * Constructor for neural network, load it from file if it exists
     * @param layersSizes array with quantity of neurons in every layer
     *                    for example NeuralNetwork{15,10,10,10});
     */
    public NeuralNetwork(int[] layersSizes) {
        if (layersSizes.length < 2) {
            System.out.println("Incorrect network size!");
            return;
        }

        this.layers = new Layer[layersSizes.length];
        this.layersSizes = layersSizes;

        this.layers[0] = new Layer(0, layersSizes[0]);

        training = new Training();

        File savedNeurons = new File("neurons.tmp");
        if (!savedNeurons.isFile()) {
            for (int i = 1; i < layers.length; i++) {
                this.layers[i] = new Layer(layersSizes[i-1], layersSizes[i]);
            }
            return;
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(savedNeurons);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            layers = (Layer[])objectInputStream.readObject();
            System.out.println("\nNeurons are load from file");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Method calling correct weights method of training class.
     */
    public void learn() {
        layers[1] = training.correctWeights(layers[1]);
        saveResults();
    }

    /**
     * Method recognize what digit is written on given image
     * @param img array with all pixels in image
     * @return digit which was written on image
     */
    public int recognizeDigit(int[] img) {
        double result;
        double bestResult = -1000;
        int recognizedDigit = 0;

        if (img.length != layersSizes[0]) {
            System.out.println("Size of image not matched with neural network input size!!!");
            return -100;
        }

        updateNeurons(img);

        for (int i = 0; i < layersSizes[layers.length-1]; i++) {
            result = layers[layers.length-1].getNeurons()[i].getValue();
            if (result > bestResult) {
                bestResult = result;
                recognizedDigit = i;
            }
        }

        return recognizedDigit;
    }

    /**
     * Method updates neurons values in network (recalculate all network)
     * @param img input image for first layer of network
     */
    private void updateNeurons(int[] img) {
        for (int i = 0; i < img.length; i++) {
            this.layers[0].getNeurons()[i].setValue(img[i]);
        }

        for (int layerNumber = 1; layerNumber < layers.length; layerNumber++) {
            int neuronsQt = this.layers[layerNumber].getNeurons().length;
            for (int neuronNumber = 0; neuronNumber < neuronsQt; neuronNumber++) {
                layers[layerNumber].getNeurons()[neuronNumber].countOutput(layers[layerNumber-1].getNeurons());
            }
        }
    }

    /**
     * method saves neural network to file
     */
    public void saveResults() {
        File savedNeurons = new File("neurons.tmp");
        if (!savedNeurons.isFile()) {
            try {
                savedNeurons.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("\nFile creation is impossible!");
                return;
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(savedNeurons);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(layers);
            objectOutputStream.close();
            System.out.println("Neurons saved to file");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
