package hr.fer.zemris.bachelor.LCS;

import hr.fer.zemris.bachelor.Constants.Constants;
import hr.fer.zemris.bachelor.LCS.Classifier.Classifier;
import hr.fer.zemris.bachelor.LCS.CodeFragment.CodeFragment;
import hr.fer.zemris.bachelor.LCS.Environment.Environment;
import hr.fer.zemris.bachelor.LCS.Environment.MuxEnvironment;
import hr.fer.zemris.bachelor.RandomNumberGenerator.RandomNumberGenerator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LearningClassifierSystem {

    private int maxPopulationSize;
    private int nunmberOfActions;
    private int trainingExamples;
    private int conditionSize;
    private Environment environment;
    private Set<Classifier> population;
    private Set<Classifier> matchSet;
    private Set<Classifier> actionSet;


    public LearningClassifierSystem(int conditionSize, int numberOfActions, int maxPopulationSize, int trainingExamples) {
        this.maxPopulationSize = maxPopulationSize;
        this.nunmberOfActions = numberOfActions;
        this.trainingExamples = trainingExamples;
        this.conditionSize = conditionSize;
        this.environment = new MuxEnvironment(conditionSize);
        this.population = new HashSet<Classifier>();
        this.matchSet = new HashSet<Classifier>();
        this.actionSet = new HashSet<Classifier>();
    }

    private Classifier coveringOperation(boolean[] input, double action) {
        Classifier cl = new Classifier(conditionSize);
        CodeFragment cf;

        for (int i = 0; i < conditionSize; i++) {
            if (RandomNumberGenerator.nextDouble() < Constants.DONT_CARE_PROBABILITY) {
                cl.setCondition(i, CodeFragment.getDontCareFragment());
            } else {
                cf = CodeFragment.getRandomFragment(input);

                while (!cf.evaluate(input)) {
                    cf = CodeFragment.getRandomFragment(input);
                }

                cl.setCondition(i, cf);
            }
        }

        cl.setAction(action);

        return cl;
    }

    private Map<Double, Integer> matchClassifiers(boolean[] input) {
        Map<Double, Integer> matchedActions = new HashMap<Double, Integer>();

        for (double action = 0; action < nunmberOfActions; action++) {
            matchedActions.put(action, 0);
        }

        matchSet.clear();

        for (Classifier cl : population) {
            if (cl.doesMatch(input)) {
                matchSet.add(cl);

                matchedActions.put(cl.getAction(), matchedActions.get(cl.getAction()) + 1);
            }
        }

        return matchedActions;
    }

    private void coverActions(boolean[] input, Map<Double, Integer> matchedActions) {
        for (double action : matchedActions.keySet()) {
            if (action <= 0) {
                Classifier cover = coveringOperation(input, action);

                population.add(cover);
                matchSet.add(cover);

                matchedActions.put(action, 1);
            }
        }
    }

    private double[] calculatePredictionArray() {
        double[] predictionArray = new double[nunmberOfActions];

        double[][] params = new double[nunmberOfActions][2];

        for (Classifier cl : matchSet) {
            params[(int)Math.round(cl.getAction())][0] = params[(int)Math.round(cl.getAction())][0] + cl.getPrediction() * cl.getFitness();
            params[(int)Math.round(cl.getAction())][1] = params[(int)Math.round(cl.getAction())][1] + cl.getFitness();
        }

        for (int i = 0; i < nunmberOfActions; i++) {
            predictionArray[i] = params[i][0] / params[i][1];
        }

        return predictionArray;
    }

    private double selectAction(double[] predictionArray) {
        double sum = 0;

        for (double p : predictionArray) {
            sum += p;
        }

        double r = RandomNumberGenerator.nextDouble(0, sum);
        sum = 0.;
        int action = 0;

        for (int i = 0, l = predictionArray.length; i < l; i++) {
            sum += predictionArray[i];

            if (r <= sum) {
                action = i;

                break;
            }
        }

        return action;
    }

    private void formActionSet(double action) {
        actionSet.clear();

        for (Classifier cl : matchSet) {
            if (cl.getAction() == action) {
                actionSet.add(cl);
            }
        }
    }

    private void updateParameters(double reward) {
        
    }

    public void explore() {
        for (int i = 0; i < trainingExamples; i++) {
            boolean[] input = environment.getInput();

            Map<Double, Integer> matchedActions = matchClassifiers(input);

            coverActions(input, matchedActions);

            double[] predictionArray = calculatePredictionArray();

            double action = selectAction(predictionArray);

            formActionSet(action);

            double reward = environment.getReward(action);
        }
    }

}
