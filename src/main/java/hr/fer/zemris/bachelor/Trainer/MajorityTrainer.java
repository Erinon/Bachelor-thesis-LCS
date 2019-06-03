package hr.fer.zemris.bachelor.Trainer;

import hr.fer.zemris.bachelor.LCS.Crossover.Crossover;
import hr.fer.zemris.bachelor.LCS.Crossover.TwoPointCrossover;
import hr.fer.zemris.bachelor.LCS.Environment.Environment;
import hr.fer.zemris.bachelor.LCS.Environment.MajorityEnvironment;
import hr.fer.zemris.bachelor.LCS.Mutation.Mutation;
import hr.fer.zemris.bachelor.LCS.Mutation.SimpleMutation;
import hr.fer.zemris.bachelor.LCS.Selection.Selection;
import hr.fer.zemris.bachelor.LCS.Selection.TournamentSelection;

import java.util.Arrays;

public class MajorityTrainer extends AbstractTrainer {

    private int[] populationSizes = new int[] {500, 1000, 2000};
    private int[] trainingExamples = new int[] {500000, 500000, 500000};
    private int[] bits = new int[] {3, 5, 7};
    private int num;

    public MajorityTrainer(int num) {
        super();

        this.num = num;
    }

    @Override
    int[] getPopulationSizes() {
        return Arrays.copyOfRange(populationSizes, 0, num);
    }

    @Override
    int[] getTrainingExamples() {
        return Arrays.copyOfRange(trainingExamples, 0, num);
    }

    @Override
    int[] getBits() {
        return Arrays.copyOfRange(bits, 0, num);
    }

    @Override
    String getType() {
        return "majority";
    }

    @Override
    int getNumberOfActions() {
        return 2;
    }

    @Override
    int getNumberOfInstancesForResultCalculation() {
        return 1000;
    }

    @Override
    Selection getSelection() {
        return new TournamentSelection();
    }

    @Override
    Crossover getCrossover() {
        return new TwoPointCrossover();
    }

    @Override
    Mutation getMutation() {
        return new SimpleMutation();
    }

    @Override
    Environment getEnvironment(int bits) {
        return new MajorityEnvironment(bits);
    }

}
