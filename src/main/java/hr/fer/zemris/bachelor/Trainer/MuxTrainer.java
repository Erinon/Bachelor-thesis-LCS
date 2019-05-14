package hr.fer.zemris.bachelor.Trainer;

import hr.fer.zemris.bachelor.LCS.Crossover.Crossover;
import hr.fer.zemris.bachelor.LCS.Crossover.TwoPointCrossover;
import hr.fer.zemris.bachelor.LCS.Environment.Environment;
import hr.fer.zemris.bachelor.LCS.Environment.MuxEnvironment;
import hr.fer.zemris.bachelor.LCS.LearningClassifierSystem;
import hr.fer.zemris.bachelor.LCS.Mutation.Mutation;
import hr.fer.zemris.bachelor.LCS.Mutation.SimpleMutation;
import hr.fer.zemris.bachelor.LCS.Selection.Selection;
import hr.fer.zemris.bachelor.LCS.Selection.TournamentSelection;

public class MuxTrainer implements Trainer {
    private int controlBits;
    private int conditionSize;
    private int numberOfActions;
    private Selection selection;
    private Crossover crossover;
    private Mutation mutation;

    public MuxTrainer(int controlBits, int numberOfActions) {
        this.controlBits = controlBits;
        this.conditionSize = controlBits + (int)Math.round(Math.pow(2, controlBits));
        this.numberOfActions = numberOfActions;

        this.selection = new TournamentSelection();
        this.crossover = new TwoPointCrossover();
        this.mutation = new SimpleMutation();
    }

    public void train() {
        int maxPopulationSize = 500;
        int trainingExamples = 500000;
        int testNumber = 29;

        Environment environment = new MuxEnvironment(controlBits);

        LearningClassifierSystem lcs = new LearningClassifierSystem(conditionSize, numberOfActions, maxPopulationSize, trainingExamples,
                environment, selection, crossover, mutation);

        for (int i = 0; i < trainingExamples; i++) {
            double action = lcs.explore();

            double sum = 0.;

            for (int j = 0; j < testNumber; j++) {
                if (environment.checkOutput(lcs.exploit(environment.getInput()))) {
                    sum++;
                }
            }

            System.out.println("Run " + i + 1 + ": " + sum / testNumber);
        }
    }

}
