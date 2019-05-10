package hr.fer.zemris.bachelor.LCS.Selection;

import hr.fer.zemris.bachelor.LCS.Classifier.Classifier;
import hr.fer.zemris.bachelor.RandomNumberGenerator.RandomNumberGenerator;

public class TournamentSelection implements Selection {

    public Classifier selectParent(Classifier[] population, double sizeRatio) {
        int popLength = population.length;
        int size = (int)(sizeRatio * popLength);
        Classifier winner = population[RandomNumberGenerator.nextInt(0, popLength - 1)];
        Classifier target;

        for (int i = 0; i < size - 1; i++) {
            target = population[RandomNumberGenerator.nextInt(0, popLength - 1)];

            if (target.getFitness() > winner.getFitness()) {
                winner = target;
            }
        }

        return winner;
    }

}
