package hr.fer.zemris.bachelor.LCS.Crossover;

import hr.fer.zemris.bachelor.LCS.Classifier.Classifier;
import hr.fer.zemris.bachelor.LCS.CodeFragment.CodeFragment;
import hr.fer.zemris.bachelor.RandomNumberGenerator.RandomNumberGenerator;

public class TwoPointCrossover implements Crossover {

    public void crossoverOperation(Classifier cl1, Classifier cl2) {
        if (cl1.getConditionSize() != cl2.getConditionSize()) {
            throw new IllegalArgumentException();
        }

        int x = RandomNumberGenerator.nextInt(0, cl1.getConditionSize() - 1);
        int y = RandomNumberGenerator.nextInt(0, cl2.getConditionSize() - 1);

        if (x > y) {
            int temp = x;
            x = y;
            y = temp;
        }

        CodeFragment temp;

        for (int i = x; i <= y; i++) {
            temp = cl1.getCondition(i);
            cl1.setCondition(i, cl2.getCondition(i));
            cl2.setCondition(i, temp);
        }
    }

}
