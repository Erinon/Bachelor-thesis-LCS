package hr.fer.zemris.bachelor.Trainer;

import hr.fer.zemris.bachelor.LCS.CodeFragment.CodeFragment;
import hr.fer.zemris.bachelor.LCS.Crossover.Crossover;
import hr.fer.zemris.bachelor.LCS.Crossover.TwoPointCrossover;
import hr.fer.zemris.bachelor.LCS.Environment.Environment;
import hr.fer.zemris.bachelor.LCS.Environment.MuxEnvironment;
import hr.fer.zemris.bachelor.LCS.LearningClassifierSystem;
import hr.fer.zemris.bachelor.LCS.Mutation.Mutation;
import hr.fer.zemris.bachelor.LCS.Mutation.SimpleMutation;
import hr.fer.zemris.bachelor.LCS.Selection.Selection;
import hr.fer.zemris.bachelor.LCS.Selection.TournamentSelection;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MuxTrainer implements Trainer {
    private static final int[] populationSizes = {500, 1000, 2000, 5000, 10000, 50000};
    private static final int[] trainingExamples = {500000, 500000, 500000, 1000000, 2000000, 5000000};
    private static final int numberOfActions = 2;
    private int controlBits;
    private Selection selection;
    private Crossover crossover;
    private Mutation mutation;
    private CodeFragment[] reusedFragments;
    private int rfLen;
    private Map<Integer, List<double[]>> results;

    public MuxTrainer(int controlBits) {
        if (controlBits < 2) {
            throw new IllegalArgumentException();
        }

        this.controlBits = controlBits;

        this.selection = new TournamentSelection();
        this.crossover = new TwoPointCrossover();
        this.mutation = new SimpleMutation();
        this.reusedFragments = new CodeFragment[0];
        this.rfLen = 0;
        this.results = new HashMap<>();
    }

    private int calculateConditionSize(int controlBits) {
        return controlBits + (int)Math.round(Math.pow(2, controlBits));
    }

    public void train() {
        final int testNumber = 30;

        for (int i = 2; i <= controlBits; i++) {
            results.put(i, new LinkedList<>());

            int conditionSize = calculateConditionSize(i);

            Environment environment = new MuxEnvironment(i);

            LearningClassifierSystem lcs = new LearningClassifierSystem(conditionSize, numberOfActions, populationSizes[i - 2],
                    environment, selection, crossover, mutation, reusedFragments, rfLen);

            System.out.println("Training for " + conditionSize + " bits");

            for (int j = 0; j < trainingExamples[i - 2]; j++) {
                lcs.explore();

                if (j % 10000 == 0) {
                    double sum = 0.;

                    for (int k = 0; k < testNumber; k++) {
                        if (environment.checkOutput(lcs.exploit(environment.getInput()))) {
                            sum++;
                        }
                    }

                    results.get(i).add(new double[] {j, sum / testNumber});

                    System.out.printf("Run %7d:\t%.2f\n", j, sum / testNumber);
                    //System.out.println("Population size: " + lcs.getPopulationSize());
                }
            }

            Set<CodeFragment> newFragments = new HashSet<>(Arrays.asList(reusedFragments));
            newFragments.addAll(Arrays.asList(lcs.getCodeFragments()));

            reusedFragments = new CodeFragment[newFragments.size()];
            newFragments.toArray(reusedFragments);

            lcs.printTimes();

            //lcs.printClassifiers();

            /*for (CodeFragment cf : reusedFragments) {
                System.out.println(cf);
            }*/
        }

        writeResults();
    }

    private void writeResults() {
        for (int key : results.keySet()) {
            File current = new File("results/multiplexer/" + key + "bit.dat");

            try (Writer bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new BufferedOutputStream(
                                    new FileOutputStream(current)), StandardCharsets.UTF_8))) {
                for (double[] p : results.get(key)) {
                    bw.write(p[0] / 1000 + " " + p[1] * 100 + "\n");
                }
            } catch (IOException ignored) {
            }
        }
    }

}
