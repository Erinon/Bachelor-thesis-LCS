package hr.fer.zemris.bachelor.LCS.Mutation;

import hr.fer.zemris.bachelor.Constants.Constants;
import hr.fer.zemris.bachelor.LCS.Classifier.Classifier;
import hr.fer.zemris.bachelor.LCS.CodeFragment.CodeFragment;
import hr.fer.zemris.bachelor.NumberGenerator.NumberGenerator;

public class SimpleMutation implements Mutation {

    public void mutationOperation(Classifier cl, boolean[] input) {
        CodeFragment cf;

        for (int i = 0; i < cl.getConditionSize(); i++) {
            if (NumberGenerator.nextDouble() < Constants.MUTATION_PROBABILITY) {
                if (cl.getCondition(i).isDontCareFragment()) {
                    cf = CodeFragment.getRandomFragment(input);

                    while (!cf.evaluate(input)) {
                        cf = CodeFragment.getRandomFragment(input);
                    }

                    cl.setCondition(i, cf);
                } else {
                    cl.setCondition(i, CodeFragment.getDontCareFragment());
                }
            }
        }

        if (NumberGenerator.nextDouble() < Constants.MUTATION_PROBABILITY) {
            cl.setAction(-cl.getAction() + 1);
        }
    }

}
