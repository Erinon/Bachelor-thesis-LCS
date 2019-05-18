package hr.fer.zemris.bachelor.LCS.Mutation;

import hr.fer.zemris.bachelor.Constants.Constants;
import hr.fer.zemris.bachelor.LCS.Classifier.Classifier;
import hr.fer.zemris.bachelor.LCS.CodeFragment.CodeFragment;
import hr.fer.zemris.bachelor.RandomNumberGenerator.RandomNumberGenerator;

public class SimpleMutation implements Mutation {

    public void mutationOperation(Classifier cl, boolean[] input, CodeFragment[] reusedFragments, int rfLen) {
        CodeFragment cf;

        for (int i = 0; i < cl.getConditionSize(); i++) {
            if (RandomNumberGenerator.nextDouble() < Constants.MUTATION_PROBABILITY) {
                if (cl.getCondition(i).isDontCareFragment()) {
                    cf = CodeFragment.getRandomFragment(input, reusedFragments, rfLen);

                    while (!cf.evaluate(input)) {
                        cf = CodeFragment.getRandomFragment(input, reusedFragments, rfLen);
                    }

                    cl.setCondition(i, cf);
                } else {
                    cl.setCondition(i, CodeFragment.getDontCareFragment());
                }
            }
        }

        if (RandomNumberGenerator.nextDouble() < Constants.MUTATION_PROBABILITY) {
            cl.setAction(-cl.getAction() + 1);
        }
    }

}
