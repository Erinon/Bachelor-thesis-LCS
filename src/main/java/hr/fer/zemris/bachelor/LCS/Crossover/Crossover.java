package hr.fer.zemris.bachelor.LCS.Crossover;

import hr.fer.zemris.bachelor.LCS.Classifier.Classifier;

public interface Crossover {

    Classifier[] crossoverOperation(Classifier cl1, Classifier cl2);

}
