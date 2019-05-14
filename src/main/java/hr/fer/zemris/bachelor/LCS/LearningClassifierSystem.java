package hr.fer.zemris.bachelor.LCS;

import hr.fer.zemris.bachelor.Constants.Constants;
import hr.fer.zemris.bachelor.LCS.Classifier.Classifier;
import hr.fer.zemris.bachelor.LCS.CodeFragment.CodeFragment;
import hr.fer.zemris.bachelor.LCS.Crossover.Crossover;
import hr.fer.zemris.bachelor.LCS.Environment.Environment;
import hr.fer.zemris.bachelor.LCS.Mutation.Mutation;
import hr.fer.zemris.bachelor.LCS.Selection.Selection;
import hr.fer.zemris.bachelor.RandomNumberGenerator.RandomNumberGenerator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LearningClassifierSystem {

    private int maxPopulationSize;
    private int populationSize;
    private int nunmberOfActions;
    private int trainingExamples;
    private int conditionSize;
    private int time;
    private Environment environment;
    private Set<Classifier> population;
    private Set<Classifier> matchSet;
    private Set<Classifier> actionSet;
    private Selection selection;
    private Crossover crossover;
    private Mutation mutation;

    public LearningClassifierSystem(int conditionSize, int numberOfActions, int maxPopulationSize, int trainingExamples, Environment environment,
                                    Selection selection, Crossover crossover, Mutation mutation) {
        this.maxPopulationSize = maxPopulationSize;
        this.populationSize = 0;
        this.nunmberOfActions = numberOfActions;
        this.trainingExamples = trainingExamples;
        this.conditionSize = conditionSize;
        this.time = 0;
        this.environment = environment;
        this.population = new HashSet<Classifier>();
        this.matchSet = new HashSet<Classifier>();
        this.actionSet = new HashSet<Classifier>();
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
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

                populationSize++;

                matchedActions.put(action, 1);
            }
        }
    }

    private double[] calculatePredictionArray() {
        double[] predictionArray = new double[nunmberOfActions];
        double[] fitnessSumArray = new double[nunmberOfActions];

        for (Classifier cl : matchSet) {
            predictionArray[(int)Math.round(cl.getAction())] = predictionArray[(int)Math.round(cl.getAction())] + cl.getPrediction() * cl.getFitness();
            fitnessSumArray[(int)Math.round(cl.getAction())] = fitnessSumArray[(int)Math.round(cl.getAction())] + cl.getFitness();
        }

        for (int i = 0; i < nunmberOfActions; i++) {
            predictionArray[i] = predictionArray[i] / fitnessSumArray[i];
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

            if (r < sum) {
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
        double accuracySum = 0.;
        int actionSetSize = 0;

        for (Classifier cl : actionSet) {
            cl.updateExperience();
            cl.updatePrediction(reward);
            cl.updatePredictionError(reward);
            cl.updateAccuracy();

            actionSetSize += cl.getNumerosity();

            accuracySum += cl.getAccuracy() * cl.getNumerosity();
        }

        for (Classifier cl : actionSet) {
            cl.updateRelativeAccuracy(accuracySum);
            cl.updateFitness();
            cl.updateActionSetSize(actionSetSize);
        }
    }

    private void insertIntoPopulation(Classifier cl) {
        populationSize++;

        for (Classifier c : population) {
            if (cl.equals(c)) {
                c.setNumerosity(c.getNumerosity() + 1);

                return;
            }
        }

        population.add(cl);
    }

    private void geneticAlgorithm(boolean[] input) {
        int timeStampSum = 0;
        int actionSetSize = 0;

        for (Classifier cl : actionSet) {
            timeStampSum += cl.getTimeStamp() * cl.getNumerosity();
            actionSetSize += cl.getNumerosity();
        }

        if (time - (double)timeStampSum / actionSetSize <= Constants.GA_APPLICATION_THRESHOLD) {
            return;
        }

        for (Classifier cl : actionSet) {
            cl.updateTimestamp(time);
        }

        Classifier[] clArray = new Classifier[population.size()];
        clArray = population.toArray(clArray);

        Classifier p1 = selection.selectParent(clArray, Constants.TOURNAMENT_SIZE_RATIO);
        Classifier p2 = selection.selectParent(clArray, Constants.TOURNAMENT_SIZE_RATIO);

        Classifier c1 = p1.deepCopy();
        Classifier c2 = p2.deepCopy();

        c1.setNumerosity(1);
        c2.setNumerosity(1);

        c1.setExperience(0);
        c2.setExperience(0);

        if (RandomNumberGenerator.nextDouble() < Constants.CROSSOVER_PROBABILITY) {
            crossover.crossoverOperation(c1, c2);

            double newPrediction = (p1.getPrediction() + p2.getPrediction()) / 2.;

            c1.setPrediction(newPrediction);
            c2.setPrediction(newPrediction);

            double newPredictionError = (p1.getPredictionError() + p2.getPredictionError()) / 2.;

            c1.setPredictionError(newPredictionError);
            c2.setPredictionError(newPredictionError);

            double newFitness = (p1.getFitness() + p2.getFitness()) / 2.;

            c1.setFitness(newFitness);
            c2.setFitness(newFitness);
        }

        c1.setFitness(c1.getFitness() * Constants.FITNESS_REDUCTION);
        c2.setFitness(c2.getFitness() * Constants.FITNESS_REDUCTION);
        c1.setPredictionError(c1.getPredictionError() * Constants.PREDICTION_ERROR_REDUCTION);
        c2.setPredictionError(c2.getPredictionError() * Constants.PREDICTION_ERROR_REDUCTION);

        mutation.mutationOperation(c1, input);
        mutation.mutationOperation(c2, input);

        insertIntoPopulation(c1);
        insertIntoPopulation(c2);

        deleteFromPopulation();
        deleteFromPopulation();
    }

    private void deleteFromPopulation() {
        if (populationSize <= maxPopulationSize) {
            return;
        }

        double fitnessSum = 0.;

        for (Classifier cl : population) {
            fitnessSum += cl.getFitness();
        }

        double averageFitness = fitnessSum / populationSize;

        double voteSum = 0.;

        for (Classifier cl : population) {
            voteSum += cl.deletionVote(averageFitness);
        }

        double choicePoint = RandomNumberGenerator.nextDouble(0, voteSum);
        voteSum = 0.;

        for (Classifier cl : population) {
            voteSum += cl.deletionVote(averageFitness);

            if (choicePoint < voteSum) {
                if (cl.getNumerosity() > 1) {
                    cl.setNumerosity(cl.getNumerosity() - 1);
                } else {
                    population.remove(cl);
                    actionSet.remove(cl);
                }

                populationSize--;

                return;
            }
        }
    }

    private void actionSetSubsumption() {
        Classifier c = null;
        int numDontCareC = 0;
        int numDontCareCl;

        for (Classifier cl : actionSet) {
            if (cl.couldSubsume()) {
                numDontCareCl = cl.getNumberOfDontCareCodeFragments();

                if (c == null || numDontCareCl > numDontCareC ||
                        (numDontCareCl == numDontCareC && RandomNumberGenerator.nextDouble() < 0.5)) {
                    c = cl;
                    numDontCareC = numDontCareCl;
                }
            }
        }

        if (c != null) {
            for (Classifier cl : actionSet) {
                if (c.isMoreGeneral(cl)) {
                    c.setNumerosity(c.getNumerosity() + cl.getNumerosity());

                    population.remove(cl);
                    actionSet.remove(cl);
                }
            }
        }
    }

    public void explore() {
        time++;

        boolean[] input = environment.getInput();

        Map<Double, Integer> matchedActions = matchClassifiers(input);

        coverActions(input, matchedActions);

        double[] predictionArray = calculatePredictionArray();

        double action = selectAction(predictionArray);

        formActionSet(action);

        double reward = environment.getReward(action);

        updateParameters(reward);

        geneticAlgorithm(input);

        actionSetSubsumption();
    }

    public double exploit() {
        boolean[] input = environment.getInput();

        Map<Double, Integer> matchedActions = matchClassifiers(input);

        coverActions(input, matchedActions);

        double[] predictionArray = calculatePredictionArray();

        return selectAction(predictionArray);
    }

}
