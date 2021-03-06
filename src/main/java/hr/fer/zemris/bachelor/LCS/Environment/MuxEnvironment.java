package hr.fer.zemris.bachelor.LCS.Environment;

import hr.fer.zemris.bachelor.Constants.Constants;
import hr.fer.zemris.bachelor.DataProvider.DataPiece;
import hr.fer.zemris.bachelor.DataProvider.DataProvider;
import hr.fer.zemris.bachelor.DataProvider.MuxDataGenerator;
import hr.fer.zemris.bachelor.RandomNumberGenerator.RandomNumberGenerator;

public class MuxEnvironment implements Environment {

    private DataProvider dataProvider;
    private DataPiece currentDataPiece;
    private int controlBits;
    private int size;

    public MuxEnvironment(int controlBits) {
        //this.dataProvider = new MuxDataGenerator(bits);
        this.controlBits = controlBits;
        this.size = controlBits + (int)Math.round(Math.pow(2, controlBits));
    }

    public boolean[] getInput() {
        boolean[] input = new boolean[size];

        for (int i = 0; i < size; i++) {
            input[i] = RandomNumberGenerator.nextBoolean();
        }

        return input;
        /*currentDataPiece = dataProvider.getNextDataPiece();

        return currentDataPiece.getInput();*/
    }

    public double getReward(boolean[] input, int action) {
        if (checkOutput(input, action)) {
            return Constants.ENVIRONMENT_REWARD_CORRECT;
        } else {
            return Constants.ENVIRONMENT_REWARD_INCORRECT;
        }
    }

    public boolean checkOutput(boolean[] input, int output) {
        int targetPow = (int)Math.round(Math.pow(2, controlBits - 1));
        int index = controlBits;
        int i;

        for (i = 0; i < controlBits; i++) {
            if (input[i]) {
                index += targetPow;
            }

            targetPow /= 2;
        }

        return (input[index] ? 1 : 0) == output;
        //return output == currentDataPiece.getOutput();
    }

}
