package hr.fer.zemris.bachelor.LCS.Environment;

public interface Environment {

    boolean[] getInput();
    double getReward(boolean[] input, int action);
    boolean checkOutput(boolean[] input, int output);
    Environment newInstance(int bits);

}
