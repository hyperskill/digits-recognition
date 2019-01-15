package recognition;

import java.io.Serializable;
import java.util.Arrays;

class neurWeights implements Serializable {
    private layer[] layers;

    neurWeights(int[] layers){
        this.layers = new layer[layers.length];
        for (int i = 0; i < layers.length; i++){
            this.layers[i] = new layer(layers[i], (i == 0)? 0 : layers[i - 1]);
        }
    }

    public layer[] getLayers() {
        return layers;
    }
}

