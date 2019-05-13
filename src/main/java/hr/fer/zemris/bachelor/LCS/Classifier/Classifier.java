package hr.fer.zemris.bachelor.LCS.Classifier;

import hr.fer.zemris.bachelor.Constants.Constants;
import hr.fer.zemris.bachelor.LCS.CodeFragment.CodeFragment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Classifier {

    private CodeFragment[] condition;
    private int conditionSize;
    private double action;
    private double prediction;
    private double predictionError;
    private double fitness;
    private double accuracy;
    private double relativeAccuracy;
    private int timeStamp;
    private int experience;
    private double actionSetSize;
    private int numerosity;

    public Classifier(int conditionSize) {
        if (conditionSize <= 0) {
            throw new IllegalArgumentException();
        }

        this.conditionSize = conditionSize;
        this.condition = new CodeFragment[conditionSize];
        this.prediction = 0.;
        this.predictionError = 0.;
        this.fitness = 0.;
        this.accuracy = 0.;
        this.relativeAccuracy = 0.;
        this.timeStamp = 0;
        this.experience = 0;
        this.actionSetSize = 0.;
        this.numerosity = 1;
    }

    private Classifier(int conditionSize, double action, double prediction, double predictionError, double fitness, int timeStamp, double actionSetSize) {
        this(conditionSize);

        this.action = action;
        this.prediction = prediction;
        this.predictionError = predictionError;
        this.fitness = fitness;
        this.timeStamp = timeStamp;
        this.actionSetSize = actionSetSize;
    }

    public Classifier deepCopy() {
        Classifier cl = new Classifier(conditionSize, action, prediction, predictionError, fitness, timeStamp, actionSetSize);

        for (int i = 0; i < conditionSize; i++) {
            cl.setCondition(i, condition[i].deepCopy());
        }

        return cl;
    }

    public void updatePrediction(double reward) {
        prediction = prediction + Constants.LEARNING_RATE * (reward - prediction);
    }

    public void updatePredictionError(double reward) {
        predictionError = predictionError + Constants.LEARNING_RATE * (Math.abs(reward - prediction) - predictionError);
    }

    public void updateAccuracy() {
        if (predictionError < Constants.PREDICTION_ERROR_THRESHOLD) {
            accuracy = 1.;
        } else {
            accuracy = Constants.FITNESS_FALL_OFF_RATE * Math.pow(predictionError / Constants.PREDICTION_ERROR_THRESHOLD, -Constants.FITNESS_EXPONENT);
        }
    }

    public void updateRelativeAccuracy(double accuracySum) {
        relativeAccuracy = accuracy / accuracySum;
    }

    public void updateFitness() {
        fitness = fitness + Constants.LEARNING_RATE * (relativeAccuracy * numerosity - fitness);
    }

    public void updateTimestamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void updateExperience() {
        experience++;
    }

    public void updateActionSetSize(int lastSize) {
        actionSetSize = actionSetSize + Constants.LEARNING_RATE * (lastSize - actionSetSize);
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public void setRelativeAccuracy(double relativeAccuracy) {
        this.relativeAccuracy = relativeAccuracy;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setActionSetSize(double actionSetSize) {
        this.actionSetSize = actionSetSize;
    }

    public double getRelativeAccuracy() {
        return relativeAccuracy;
    }

    public double getTimeStamp() {
        return timeStamp;
    }

    public int getExperience() {
        return experience;
    }

    public double getActionSetSize() {
        return actionSetSize;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public int getNumerosity() {
        return numerosity;
    }

    public void setPrediction(double prediction) {
        this.prediction = prediction;
    }

    public void setPredictionError(double predictionError) {
        this.predictionError = predictionError;
    }

    public void setNumerosity(int numerosity) {
        this.numerosity = numerosity;
    }

    public double getPrediction() {
        return prediction;
    }

    public double getPredictionError() {
        return predictionError;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public int getConditionSize() {
        return conditionSize;
    }

    public CodeFragment getCondition(int index) {
        checkIndex(index);

        return condition[index];
    }

    public double getAction() {
        return action;
    }

    public void setCondition(int index, CodeFragment codeFragment) {
        checkIndex(index);

        this.condition[index] = codeFragment;
    }

    public void setAction(double action) {
        this.action = action;
    }

    private int getNumberOfDontCareCodeFragments() {
        int n = 0;

        for (CodeFragment cf : condition) {
            if (cf.isDontCareFragment()) {
                n++;
            }
        }

        return n;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= condition.length) {
            throw new IllegalArgumentException();
        }
    }

    public boolean doesMatch(boolean[] input) {
        for (int i = 0; i < conditionSize; i++) {
            if (!condition[i].evaluate(input)) {
                return false;
            }
        }

        return true;
    }

    public boolean isMoreGeneral(Classifier that) {
        int x = getNumberOfDontCareCodeFragments();
        int y = that.getNumberOfDontCareCodeFragments();

        if (x <= y) {
            return false;
        }

        Set<CodeFragment> Y = new HashSet<CodeFragment>(Arrays.asList(that.condition));

        for (CodeFragment cf : condition) {
            if (!cf.isDontCareFragment()) {
                if (!Y.contains(cf)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Classifier that = (Classifier) o;

        if (this.action != that.action) {
            return false;
        }

        int x = getNumberOfDontCareCodeFragments();
        int y = that.getNumberOfDontCareCodeFragments();

        if (x != y) {
            return false;
        }

        Set<CodeFragment> X = new HashSet<CodeFragment>(Arrays.asList(condition));
        Set<CodeFragment> Y = new HashSet<CodeFragment>(Arrays.asList(that.condition));

        return X.equals(Y);

    }

}
