package hr.fer.zemris.bachelor.LCS.Environment;

import hr.fer.zemris.bachelor.Constants.Constants;
import hr.fer.zemris.bachelor.RandomNumberGenerator.RandomNumberGenerator;

public class MajorityEnvironment implements Environment {

    private int size;

    public MajorityEnvironment(int size) {
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
        if (output < 0 || output > 1) {
            throw new IllegalArgumentException();
        }

        int ones = 0;

        for (int i = 0; i < size; i++) {
            if (input[i]) {
                ones++;
            }
        }

        if (2 * ones == size) {
            return true;
        }

        return output == (2 * ones > size ? 1: 0);
    }

    @Override
    public Environment newInstance(int bits) {
        return new MajorityEnvironment(bits);
    }

}
