package hr.fer.zemris.bachelor.LCS.Environment;

import hr.fer.zemris.bachelor.Constants.Constants;
import hr.fer.zemris.bachelor.DataProvider.DataPiece;
import hr.fer.zemris.bachelor.DataProvider.DataProvider;
import hr.fer.zemris.bachelor.DataProvider.MuxDataGenerator;

public class MuxEnvironment implements Environment {

    private DataProvider dataProvider;
    private DataPiece currentDataPiece;

    public MuxEnvironment(int bits) {
        this.dataProvider = new MuxDataGenerator(bits);
    }

    public boolean[] getInput() {
        currentDataPiece = dataProvider.getNextDataPiece();

        return currentDataPiece.getInput();
    }

    public double getReward(double action) {
        if (currentDataPiece.getOutput() == action) {
            return Constants.ENVIRONMENT_REWARD_CORRECT;
        } else {
            return Constants.ENVIRONMENT_REWARD_INCORRECT;
        }
    }

}
