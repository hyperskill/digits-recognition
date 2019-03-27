package recognition;

// Class fo working with input and output numbers
public class Numbers {
    // Ideal input neuron values
    // The input itself is double, so those numbers in double too
    public static final double[][] idealInputNumbers = {
            { 1,  1,  1,  1,  0,  1,  1,  0,  1,  1,  0,  1,  1,  1,  1}, // 0
            { 0,  1,  0,  0,  1,  0,  0,  1,  0,  0,  1,  0,  0,  1,  0}, // 1
            { 1,  1,  1,  0,  0,  1,  1,  1,  1,  1,  0,  0,  1,  1,  1}, // 2
            { 1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1}, // 3
            { 1,  0,  1,  1,  0,  1,  1,  1,  1,  0,  0,  1,  0,  0,  1}, // 4
            { 1,  1,  1,  1,  0,  0,  1,  1,  1,  0,  0,  1,  1,  1,  1}, // 5
            { 1,  1,  1,  1,  0,  0,  1,  1,  1,  1,  0,  1,  1,  1,  1}, // 6
            { 1,  1,  1,  0,  0,  1,  0,  0,  1,  0,  0,  1,  0,  0,  1}, // 7
            { 1,  1,  1,  1,  0,  1,  1,  1,  1,  1,  0,  1,  1,  1,  1}, // 8
            { 1,  1,  1,  1,  0,  1,  1,  1,  1,  0,  0,  1,  1,  1,  1}  // 9
    };

    // Ideal output neuron values
    public static final double[][] idealOutputNumbers = {
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // 0
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, // 1
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0}, // 2
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, // 3
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, // 4
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, // 5
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, // 6
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, // 7
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, // 8
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 1}  // 9
    };

    // Ideal input as string
    public static String getIdealNumber(int number){
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < idealInputNumbers[number].length; ++i){
            if(i % 3 == 0)
                result.append("\n");

            result.append(idealInputNumbers[number][i] == 1 ? 'x' : '_');
        }

        return result.toString();
    }
}
