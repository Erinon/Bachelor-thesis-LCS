package hr.fer.zemris.bachelor.LCS.Environment;

import hr.fer.zemris.bachelor.Constants.Constants;
import hr.fer.zemris.bachelor.RandomNumberGenerator.RandomNumberGenerator;

public class CarryEnvironment implements Environment {

    private int size;

    public CarryEnvironment(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException();
        }

        this.size = size;
    }

    @Override
    public boolean[] getInput() {
        boolean[] input = new boolean[size];

        for (int i = 0; i < size; i++) {
            input[i] = RandomNumberGenerator.nextBoolean();
        }

        return input;
    }

    @Override
    public double getReward(boolean[] input, int action) {
        if (checkOutput(input, action)) {
            return Constants.ENVIRONMENT_REWARD_CORRECT;
        } else {
            return Constants.ENVIRONMENT_REWARD_INCORRECT;
        }
    }

    @Override
    public boolean checkOutput(boolean[] input, int output) {
        int numSize = size / 2;
        boolean c = false;
        boolean a, b;

        for (int i = 0; i < numSize; i++) {
            a = input[i];
            b = input[numSize + i];

            c = (a & b) | (c & (a ^ b));
        }

        return output == (c ? 1 : 0);
    }

    @Override
    public Environment newInstance(int bits) {
        return new CarryEnvironment(bits);
    }
}
