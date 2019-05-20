package hr.fer.zemris.bachelor.LCS;

import hr.fer.zemris.bachelor.Constants.Constants;
import hr.fer.zemris.bachelor.LCS.Classifier.Classifier;
import hr.fer.zemris.bachelor.LCS.CodeFragment.CodeFragment;
import hr.fer.zemris.bachelor.LCS.Crossover.Crossover;
import hr.fer.zemris.bachelor.LCS.Environment.Environment;
import hr.fer.zemris.bachelor.LCS.Mutation.Mutation;
import hr.fer.zemris.bachelor.LCS.Selection.Selection;
import hr.fer.zemris.bachelor.RandomNumberGenerator.RandomNumberGenerator;

import java.util.HashSet;
import java.util.Set;

public class LearningClassifierSystem {

    private int maxPopulationSize;
    private int populationSize;
    private int numberOfActions;
    private int conditionSize;
    private int time;
    private Environment environment;
    private Set<Classifier> population;
    private Set<Classifier> matchSet;
    private Set<Classifier> actionSet;
    private Selection selection;
    private Crossover crossover;
    private Mutation mutation;
    private CodeFragment[] reusedFragments;
    private int rfLen;
    private int timeStampSum;
    private int actionSetSize;
    private long giTime;
    private long gmsTime;
    private long gms1;
    private long gms2;
    private long cpaTime;
    private long saTime;
    private long fasTime;
    private long grTime;
    private long upTime;
    private long gaTime;
    private long ga1;
    private long ga2;
    private long ga3;
    private long ga4;
    private long ga5;
    private long assTime;

    public LearningClassifierSystem(int conditionSize, int numberOfActions, int maxPopulationSize, Environment environment,
                                    Selection selection, Crossover crossover, Mutation mutation, CodeFragment[] reusedFragments, int rfLen) {
        this.maxPopulationSize = maxPopulationSize;
        this.populationSize = 0;
        this.numberOfActions = numberOfActions;
        this.conditionSize = conditionSize;
        this.time = 0;
        this.environment = environment;
        this.population = new HashSet<>();
        this.matchSet = new HashSet<>();
        this.actionSet = new HashSet<>();
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
        this.reusedFragments = reusedFragments;
        this.rfLen = rfLen;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    private Classifier coveringOperation(boolean[] input, int action) {
        Classifier cl = new Classifier(conditionSize);
        CodeFragment cf;

        for (int i = 0; i < conditionSize; i++) {
            if (RandomNumberGenerator.nextDouble() < Constants.DONT_CARE_PROBABILITY) {
                cl.setCondition(i, CodeFragment.getDontCareFragment());
            } else {
                do {
                    cf = CodeFragment.getRandomFragment(input, reusedFragments, rfLen);
                } while (!cf.evaluate(input));

                cl.setCondition(i, cf);
            }
        }

        cl.setAction(action);

        return cl;
    }

    private void generateMatchSet(boolean[] input) {
        long t1;
        long t2;

        int[] matchedActions = new int[numberOfActions];

        matchSet = new HashSet<>();

        for (Classifier cl : population) {
            t1 = System.currentTimeMillis();

            boolean match = cl.doesMatch(input);

            t2 = System.currentTimeMillis();

            gms1 += t2 - t1;

            if (match) {
                matchSet.add(cl);

                matchedActions[cl.getAction()] = matchedActions[cl.getAction()] + 1;
            }

            t1 = System.currentTimeMillis();

            gms2 += t1 - t2;
        }

        boolean found = true;

        Classifier inserted;

        while (found) {
            found = false;

            for (int a = 0; a < numberOfActions; a++) {
                //System.out.println(matchedActions[a]);
                if (matchedActions[a] <= 0) {
                    inserted = insertIntoPopulation(coveringOperation(input, a));

                    if (inserted != null) {
                        matchSet.add(inserted);
                        matchedActions[a] = matchedActions[a] + 1;
                    }

                    Classifier deleted = deleteFromPopulation();

                    if (deleted != null && deleted.doesMatch(input)) {
                        matchedActions[deleted.getAction()] = matchedActions[deleted.getAction()] - 1;
                        System.out.println("true");
                        found = true;

                        break;
                    }
                }
            }
        }

        /*for (Classifier cl : matchSet) {
            System.out.println(cl);
        }*/
    }

    private double[] calculatePredictionArray() {
        double[] predictionArray = new double[numberOfActions];
        double[] fitnessSumArray = new double[numberOfActions];

        int action;

        for (Classifier cl : matchSet) {
            action = cl.getAction();

            predictionArray[action] = predictionArray[action] + cl.getPrediction() * cl.getFitness() * cl.getNumerosity();
            fitnessSumArray[action] = fitnessSumArray[action] + cl.getFitness() * cl.getNumerosity();
        }

        for (int i = 0; i < numberOfActions; i++) {
            predictionArray[i] = predictionArray[i] / fitnessSumArray[i];
        }

        return predictionArray;
    }

    private int selectAction(double[] predictionArray) {
        double sum = 0;

        for (double p : predictionArray) {
            sum += p;
        }

        double r = RandomNumberGenerator.nextDouble(0, sum);
        sum = 0.;

        for (int i = 0; i < numberOfActions - 1; i++) {
            sum += predictionArray[i];

            if (r < sum) {
                return i;
            }
        }

        return numberOfActions - 1;
    }

    private void formActionSet(int action) {
        actionSet = new HashSet<>();

        for (Classifier cl : matchSet) {
            //System.out.println(cl);
            //System.out.println(action);
            if (cl.getAction() == action) {
                actionSet.add(cl);
            }
        }
    }

    private void updateParameters(double reward) {
        double accuracySum = 0.;
        actionSetSize = 0;

        for (Classifier cl : actionSet) {
            cl.updateExperience();
            cl.updatePrediction(reward);
            cl.updatePredictionError(reward);
            cl.updateAccuracy();

            actionSetSize += cl.getNumerosity();

            accuracySum += cl.getAccuracy() * cl.getNumerosity();
        }

        timeStampSum = 0;

        for (Classifier cl : actionSet) {
            cl.updateRelativeAccuracy(accuracySum);
            cl.updateFitness();
            cl.updateActionSetSize(actionSetSize);

            timeStampSum += cl.getTimeStamp() * cl.getNumerosity();
        }
    }

    private Classifier insertIntoPopulation(Classifier cl) {
        populationSize++;

        for (Classifier c : population) {
            if (cl.equals(c)) {
                c.setNumerosity(c.getNumerosity() + 1);

                return null;
            }
        }

        population.add(cl);

        return cl;
    }

    private void mutateChild(Classifier c, Classifier p1, Classifier p2, boolean[] input) {
        long t1 = System.currentTimeMillis();

        mutation.mutationOperation(c, input, numberOfActions, reusedFragments, rfLen);

        long t2 = System.currentTimeMillis();

        ga4 += t2 - t1;

        if (p1.doesSubsume(c)) {
            p1.setNumerosity(p1.getNumerosity() + 1);
            populationSize++;
        } else if (p2.doesSubsume(c)) {
            p2.setNumerosity(p2.getNumerosity() + 1);
            populationSize++;
        } else {
            insertIntoPopulation(c);
        }

        t1 = System.currentTimeMillis();

        deleteFromPopulation();

        t2 = System.currentTimeMillis();

        ga5 += t2 - t1;
    }

    private void geneticAlgorithm(boolean[] input) {
        long t1 = System.currentTimeMillis();

        if (time - (double)timeStampSum / actionSetSize <= Constants.GA_APPLICATION_THRESHOLD) {
            return;
        }

        for (Classifier cl : actionSet) {
            cl.updateTimestamp(time);
        }

        long t2 = System.currentTimeMillis();

        ga1 += t2 - t1;

        Classifier[] clArray = new Classifier[actionSet.size()];
        clArray = actionSet.toArray(clArray);

        //System.out.println(actionSet.size());

        Classifier p1 = selection.selectParent(clArray, Constants.TOURNAMENT_SIZE_RATIO);
        Classifier p2 = selection.selectParent(clArray, Constants.TOURNAMENT_SIZE_RATIO);

        Classifier c1 = p1.deepCopy();
        Classifier c2 = p2.deepCopy();

        c1.setNumerosity(1);
        c2.setNumerosity(1);

        c1.setExperience(0);
        c2.setExperience(0);

        c1.setActionSetSize(0);
        c2.setActionSetSize(0);

        c1.setTimeStamp(0);
        c2.setTimeStamp(0);

        t1 = System.currentTimeMillis();

        ga2 += t1 - t2;

        if (RandomNumberGenerator.nextDouble() < Constants.CROSSOVER_PROBABILITY) {
            //System.out.println("crossover");
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

        t2 = System.currentTimeMillis();

        ga3 += t2 - t1;

        c1.setFitness(c1.getFitness() * Constants.FITNESS_REDUCTION);
        c2.setFitness(c2.getFitness() * Constants.FITNESS_REDUCTION);
        c1.setPredictionError(c1.getPredictionError() * Constants.PREDICTION_ERROR_REDUCTION);
        c2.setPredictionError(c2.getPredictionError() * Constants.PREDICTION_ERROR_REDUCTION);

        mutateChild(c1, p1, p2, input);

        mutateChild(c2, p1, p2, input);
    }

    private Classifier deleteFromPopulation() {
        if (populationSize <= maxPopulationSize) {
            return null;
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

        Classifier deleted = null;

        for (Classifier cl : population) {
            voteSum += cl.deletionVote(averageFitness);

            if (choicePoint < voteSum) {
                if (cl.getNumerosity() > 1) {
                    cl.setNumerosity(cl.getNumerosity() - 1);
                } else {
                    deleted = cl;

                    population.remove(cl);
                    matchSet.remove(cl);
                    actionSet.remove(cl);
                }

                populationSize--;

                return deleted;
            }
        }

        return null;
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
                    //actionSet.remove(cl);
                }
            }
        }
    }

    public CodeFragment[] getCodeFragments() {
        Set<CodeFragment> fragSet = new HashSet<>();

        double fitnessSum = 0.;

        for (Classifier cl : population) {
            fitnessSum += cl.getFitness();
        }

        double fitnessAvg = fitnessSum / populationSize;

        for (Classifier cl : population) {
            if (cl.getExperience() > Constants.REUSE_EXPERIENCE_THRESHOLD && cl.getFitness() > fitnessAvg) {
                fragSet.addAll(cl.getCodeFragments());
            }
        }

        CodeFragment[] frags = new CodeFragment[fragSet.size()];

        fragSet.toArray(frags);

        return frags;
    }

    public int explore() {
        time++;

        long t1 = System.currentTimeMillis();

        boolean[] input = environment.getInput();

        giTime += System.currentTimeMillis() - t1;

        int action = exploit(input);

        t1 = System.currentTimeMillis();

        formActionSet(action);

        long t2 = System.currentTimeMillis();

        fasTime += t2 - t1;

        double reward = environment.getReward(action);

        t1 = System.currentTimeMillis();

        grTime += t1 - t2;

        updateParameters(reward);

        t2 = System.currentTimeMillis();

        upTime += t2 - t1;

        geneticAlgorithm(input);

        t1 = System.currentTimeMillis();

        gaTime += t1 - t2;

        actionSetSubsumption();

        t2 = System.currentTimeMillis();

        assTime += t2 - t1;

        return action;
    }

    public int exploit(boolean[] input) {
        long t1 = System.currentTimeMillis();

        generateMatchSet(input);

        long t2 = System.currentTimeMillis();

        gmsTime += t2 - t1;

        double[] predictionArray = calculatePredictionArray();

        t1 = System.currentTimeMillis();

        cpaTime += t1 - t2;

        int action = selectAction(predictionArray);

        t2 = System.currentTimeMillis();

        saTime += t2 - t1;

        return action;
    }

    public void printClassifiers() {
        for (Classifier cl : population) {
            System.out.println(cl);
        }
    }

    public void printTimes() {
        double sum = (giTime + gmsTime + cpaTime + saTime + fasTime + grTime + upTime + gaTime + assTime);

        System.out.printf("Total time:\t%.2f\n", sum / 60000.);
        System.out.printf("Get input:\t%.2f\n", giTime / sum * 100);
        System.out.printf("Match set:\t%.2f\n", gmsTime / sum * 100);
        System.out.printf("Match set 1:\t%.2f\n", (double)gms1 / gmsTime * 100);
        System.out.printf("Match set 2:\t%.2f\n", (double)gms2 / gmsTime * 100);
        System.out.printf("Pred array:\t%.2f\n", cpaTime / sum * 100);
        System.out.printf("Sel action:\t%.2f\n", saTime / sum * 100);
        System.out.printf("Action set:\t%.2f\n", fasTime / sum * 100);
        System.out.printf("Get reward:\t%.2f\n", grTime / sum * 100);
        System.out.printf("Upd params:\t%.2f\n", upTime / sum * 100);
        System.out.printf("Genetic alg:\t%.2f\n", gaTime / sum * 100);
        System.out.printf("Gen alg 1:\t%.2f\n", (double)ga1 / gaTime * 100);
        System.out.printf("Gen alg 2:\t%.2f\n", (double)ga2 / gaTime * 100);
        System.out.printf("Gen alg 3:\t%.2f\n", (double)ga3 / gaTime * 100);
        System.out.printf("Gen alg 4:\t%.2f\n", (double)ga4 / gaTime * 100);
        System.out.printf("Gen alg 5:\t%.2f\n", (double)ga5 / gaTime * 100);
        System.out.printf("AS subsumpt:\t%.2f\n", assTime / sum * 100);
    }

}
