package hr.fer.zemris.bachelor.LCS.Crossover;

import hr.fer.zemris.bachelor.Constants.Constants;
import hr.fer.zemris.bachelor.LCS.Classifier.Classifier;
import hr.fer.zemris.bachelor.LCS.CodeFragment.CodeFragment;
import hr.fer.zemris.bachelor.RandomNumberGenerator.RandomNumberGenerator;

public class TwoPointCrossover implements Crossover {

    public Classifier[] crossoverOperation(Classifier cl1, Classifier cl2) {
        if (cl1.getConditionSize() != cl2.getConditionSize()) {
            throw new IllegalArgumentException();
        }

        Classifier[] children = new Classifier[2];

        children[0] = new Classifier(cl1.getConditionSize());
        children[1] = new Classifier(cl2.getConditionSize());

        int x = RandomNumberGenerator.nextInt(0, cl1.getConditionSize() - 1);
        int y = RandomNumberGenerator.nextInt(0, cl2.getConditionSize() - 1);

        if (x > y) {
            int temp = x;
            x = y;
            y = temp;
        }

        for (int i = 0; i < x; i++) {
            children[0].setCondition(i, cl1.getCondition(i));
            children[1].setCondition(i, cl2.getCondition(i));
        }

        for (int i = x; i <= y; i++) {
            children[0].setCondition(i, cl2.getCondition(i));
            children[1].setCondition(i, cl1.getCondition(i));
        }

        for (int i = y + 1, s = cl1.getConditionSize(); i < s; i++) {
            children[0].setCondition(i, cl1.getCondition(i));
            children[1].setCondition(i, cl2.getCondition(i));
        }

        double newPrediction = (cl1.getPrediction() + cl2.getPrediction()) / 2.;

        children[0].setPrediction(newPrediction);
        children[1].setPrediction(newPrediction);

        double newPredictionError = Constants.PREDICTION_ERROR_REDUCTION * (cl1.getPredictionError() + cl2.getPredictionError()) / 2.;

        children[0].setPredictionError(newPredictionError);
        children[1].setPredictionError(newPredictionError);

        double newFitness = Constants.FITNESS_REDUCTION * (cl1.getFitness() + cl2.getFitness()) / 2.;

        children[0].setFitness(newFitness);
        children[1].setFitness(newFitness);

        children[0].setNumerosity(1);
        children[1].setNumerosity(1);

        children[0].setAction(cl1.getAction());
        children[1].setAction(cl2.getAction());

        return children;
    }

}
