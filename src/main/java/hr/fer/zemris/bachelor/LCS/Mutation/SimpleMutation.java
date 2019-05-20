package hr.fer.zemris.bachelor.LCS.Mutation;

import hr.fer.zemris.bachelor.Constants.Constants;
import hr.fer.zemris.bachelor.LCS.Classifier.Classifier;
import hr.fer.zemris.bachelor.LCS.CodeFragment.CodeFragment;
import hr.fer.zemris.bachelor.RandomNumberGenerator.RandomNumberGenerator;

public class SimpleMutation implements Mutation {

    public void mutationOperation(Classifier cl, boolean[] input, int numOfActions, CodeFragment[] reusedFragments, int rfLen) {
        CodeFragment cf;

        for (int i = 0; i < cl.getConditionSize(); i++) {
            if (RandomNumberGenerator.nextDouble() < Constants.MUTATION_PROBABILITY) {
                if (cl.getCondition(i).isDontCareFragment()) {
                    do {
                        cf = CodeFragment.getRandomFragment(input, reusedFragments, rfLen);
                    } while (!cf.evaluate(input));

                    cl.setCondition(i, cf);
                } else {
                    cl.setCondition(i, CodeFragment.getDontCareFragment());
                }
            }
        }

        int action;

        if (RandomNumberGenerator.nextDouble() < Constants.MUTATION_PROBABILITY) {
            do {
                action = RandomNumberGenerator.nextInt(0, numOfActions - 1);
            } while (action == cl.getAction());

            cl.setAction(action);
        }
    }

}
