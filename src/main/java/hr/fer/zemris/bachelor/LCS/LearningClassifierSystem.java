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


    public LearningClassifierSystem(int conditionSize, int numberOfActions, int maxPopulationSize, int trainingExamples) {
        this.maxPopulationSize = maxPopulationSize;
        this.nunmberOfActions = numberOfActions;
        this.trainingExamples = trainingExamples;
        this.conditionSize = conditionSize;
        this.environment = new MuxEnvironment(conditionSize);
        this.population = new HashSet<Classifier>();
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

    public void explore() {
        for (int i = 0; i < trainingExamples; i++) {
            boolean[] input = environment.getInput();
            Map<Double, Integer> matchedActions = new HashMap<Double, Integer>();

            for (double action = 0; action < nunmberOfActions; action++) {
                matchedActions.put(action, 0);
            }

            Set<Classifier> matchSet = new HashSet<Classifier>();

            // Match classifiers
            for (Classifier cl : population) {
                if (cl.doesMatch(input)) {
                    matchSet.add(cl);

                    matchedActions.put(cl.getAction(), matchedActions.get(cl.getAction()) + 1);
                }
            }

            for (double action : matchedActions.keySet()) {
                if (action <= 0) {
                    Classifier cover = coveringOperation(input, action);

                    population.add(cover);
                    matchSet.add(cover);

                    matchedActions.put(action, 1);
                }
            }

            
        }
    }

}
