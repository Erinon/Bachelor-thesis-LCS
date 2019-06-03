package hr.fer.zemris.bachelor.Trainer;

import hr.fer.zemris.bachelor.LCS.Crossover.Crossover;
import hr.fer.zemris.bachelor.LCS.Environment.Environment;
import hr.fer.zemris.bachelor.LCS.Mutation.Mutation;
import hr.fer.zemris.bachelor.LCS.Selection.Selection;

public class CarryTrainer extends AbstractTrainer {

    private int[] populationSizes = new int[] {200, 300, 400, 500, 1000, 2000};
    private int[] trainingExamples = new int[] {500000, 500000, 500000, 500000, 500000, 500000};
    private int[] bits = new int[] {2, 3, 4, 5, 6, 7};
    private int num;

    public CarryTrainer(int num) {
        super();

        this.num = num;
    }

    @Override
    int[] getPopulationSizes() {
        return new int[0];
    }

    @Override
    int[] getTrainingExamples() {
        return new int[0];
    }

    @Override
    int[] getBits() {
        return new int[0];
    }

    @Override
    String getType() {
        return null;
    }

    @Override
    int getNumberOfActions() {
        return 0;
    }

    @Override
    int getNumberOfInstancesForResultCalculation() {
        return 0;
    }

    @Override
    Selection getSelection() {
        return null;
    }

    @Override
    Crossover getCrossover() {
        return null;
    }

    @Override
    Mutation getMutation() {
        return null;
    }

    @Override
    Environment getEnvironment(int bits) {
        return null;
    }
}
