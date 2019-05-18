package hr.fer.zemris.bachelor.LCS.Environment;

public interface Environment {

    boolean[] getInput();
    double getReward(int action);
    boolean checkOutput(int output);

}
