package hr.fer.zemris.bachelor.Trainer;

import hr.fer.zemris.bachelor.LCS.CodeFragment.CodeFragment;
import hr.fer.zemris.bachelor.LCS.Crossover.Crossover;
import hr.fer.zemris.bachelor.LCS.Crossover.TwoPointCrossover;
import hr.fer.zemris.bachelor.LCS.Mutation.Mutation;
import hr.fer.zemris.bachelor.LCS.Mutation.SimpleMutation;
import hr.fer.zemris.bachelor.LCS.Selection.Selection;
import hr.fer.zemris.bachelor.LCS.Selection.TournamentSelection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParityTrainer implements Trainer {

    private static final int[] populationSizes = {500, 1000, 2000, 5000, 10000, 50000};
    private static final int[] trainingExamples = {500000, 500000, 500000, 1000000, 2000000, 5000000};
    private static final int numberOfActions = 2;
    private static final int averageInstances = 1000;
    private int bits;
    private Selection selection;
    private Crossover crossover;
    private Mutation mutation;
    private CodeFragment[] reusedFragments;
    private int rfLen;
    private Map<Integer, List<double[]>> results;

    public ParityTrainer(int bits) {
        if (bits <= 0) {
            throw new IllegalArgumentException();
        }

        this.bits = bits;

        this.selection = new TournamentSelection();
        this.crossover = new TwoPointCrossover();
        this.mutation = new SimpleMutation();
        this.reusedFragments = new CodeFragment[0];
        this.rfLen = 0;
        this.results = new HashMap<>();
    }

    public void train() {
        for (int i = 1; i < bits; i++) {
            
        }
    }

}
