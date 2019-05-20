package hr.fer.zemris.bachelor.LCS.Mutation;

import hr.fer.zemris.bachelor.LCS.Classifier.Classifier;
import hr.fer.zemris.bachelor.LCS.CodeFragment.CodeFragment;

public interface Mutation {

    void mutationOperation(Classifier cl, boolean[] input, int numOfActions, CodeFragment[] reusedFragments, int rfLen);

}
