package hr.fer.zemris.bachelor.Trainer;

import hr.fer.zemris.bachelor.LCS.CodeFragment.CodeFragment;
import hr.fer.zemris.bachelor.LCS.Crossover.Crossover;
import hr.fer.zemris.bachelor.LCS.Environment.Environment;
import hr.fer.zemris.bachelor.LCS.LearningClassifierSystem;
import hr.fer.zemris.bachelor.LCS.Mutation.Mutation;
import hr.fer.zemris.bachelor.LCS.Selection.Selection;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public abstract class AbstractTrainer {

    private int[] populationSizes;
    private int[] trainingExamples;
    private int numberOfActions;
    private int averageInstances;
    private int[] bits;
    private int bitsSize;
    private String type;
    private Selection selection;
    private Crossover crossover;
    private Mutation mutation;
    private CodeFragment[] reusedFragments;
    private int rfLen;
    private Map<Integer, List<double[]>> results;

    abstract int[] getPopulationSizes();
    abstract int[] getTrainingExamples();
    abstract int[] getBits();
    abstract String getType();
    abstract int getNumberOfActions();
    abstract int getNumberOfInstancesForResultCalculation();
    abstract Selection getSelection();
    abstract Crossover getCrossover();
    abstract Mutation getMutation();
    abstract Environment getEnvironment(int bits);

    public AbstractTrainer() {
        this.reusedFragments = new CodeFragment[0];
        this.rfLen = 0;
        results = new HashMap<>();
    }

    private void updateAttributes() {
        this.populationSizes = getPopulationSizes();
        this.trainingExamples = getTrainingExamples();
        this.numberOfActions = getNumberOfActions();
        this.averageInstances = getNumberOfInstancesForResultCalculation();
        this.bits = getBits();
        this.bitsSize = this.bits.length;
        this.type = getType();
        this.selection = getSelection();
        this.crossover = getCrossover();
        this.mutation = getMutation();
    }

    public void train() {
        updateAttributes();

        for (int i = 0; i < bitsSize; i++) {
            results.put(bits[i], new LinkedList<>());

            Environment environment = getEnvironment(bits[i]);

            LearningClassifierSystem lcs = new LearningClassifierSystem(bits[i], numberOfActions, populationSizes[i],
                    environment, selection, crossover, mutation, reusedFragments, rfLen);

            System.out.println("Training " + type + " for " + bits[i] + " bits");

            Queue<Boolean> testValues = new LinkedList<>();
            int currentTestNumber = 0;
            int correctTests = 0;
            boolean output;
            boolean[] input;
            double accuracy;

            for (int j = 0; j < trainingExamples[i]; j++) {
                lcs.explore();

                input = environment.getInput();
                output = environment.checkOutput(input, lcs.exploit(input));
                testValues.add(output);
                if (output) {
                    correctTests++;
                }
                if (currentTestNumber < averageInstances) {
                    currentTestNumber++;
                } else if (testValues.remove()) {
                    correctTests--;
                }
                accuracy = (float)correctTests / currentTestNumber;

                results.get(bits[i]).add(new double[] {j, accuracy});

                //System.out.println(correctTests + " " + currentTestNumber);

                /*if (j % 1000 == 0) {
                    lcs.printTimes();
                    lcs.printSizes();
                }*/

                if (j % 10000 == 0) {
                    //System.out.println(correctTests + " " + currentTestNumber);
                    System.out.printf("Run %7d:\t%.2f\n", j, accuracy);
                    //System.out.println("Population size: " + lcs.getPopulationSize());
                }
            }

            int testInstances = 1000;
            correctTests = 0;

            for (int j = 0; j < testInstances; j++) {
                input = environment.getInput();
                output = environment.checkOutput(input, lcs.exploit(input));

                if (output) {
                    correctTests++;
                }
            }

            double mean = (double)correctTests / testInstances;
            double diffSumSq = correctTests * (1. - 2. * mean) + testInstances * mean * mean;
            double stdDev = Math.sqrt(diffSumSq / (testInstances - 1.));

            System.out.println("Mean: " + mean);
            System.out.println("Standard deviation: " + stdDev);

            Set<CodeFragment> newFragments = new HashSet<>(Arrays.asList(reusedFragments));
            newFragments.addAll(Arrays.asList(lcs.getCodeFragments()));

            reusedFragments = new CodeFragment[newFragments.size()];
            newFragments.toArray(reusedFragments);
            rfLen = reusedFragments.length;

            lcs.printTimes();

            System.out.println(reusedFragments.length);

            //lcs.printClassifiers();

            /*for (CodeFragment cf : reusedFragments) {
                System.out.println(cf);
            }*/
        }

        writeResults();
    }

    private void writeResults() {
        for (int key : results.keySet()) {
            File current = new File("results/" + type + "/" + key + "bit.dat");

            try (Writer bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new BufferedOutputStream(
                                    new FileOutputStream(current)), StandardCharsets.UTF_8))) {
                for (double[] p : results.get(key)) {
                    bw.write(p[0] / 1000 + " " + p[1] + "\n");
                }
            } catch (IOException ignored) {
            }
        }
    }

}
