package hr.fer.zemris.bachelor.LCS.Classifier;

import hr.fer.zemris.bachelor.LCS.CodeFragment.CodeFragment;

public class Classifier {

    private CodeFragment[] condition;
    private int conditionSize;
    private double action;
    private double fitness;

    public Classifier(int conditionSize) {
        if (conditionSize <= 0) {
            throw new IllegalArgumentException();
        }

        this.conditionSize = conditionSize;
        this.condition = new CodeFragment[conditionSize];
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
        //TODO
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
        //TODO
        return true;
    }

}
