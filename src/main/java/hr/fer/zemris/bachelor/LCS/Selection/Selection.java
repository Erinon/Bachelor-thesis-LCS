package hr.fer.zemris.bachelor.LCS.Selection;

import hr.fer.zemris.bachelor.LCS.Classifier.Classifier;

public interface Selection {

    Classifier selectParent(Classifier[] population, double sizeRatio);

}
