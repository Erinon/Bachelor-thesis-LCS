package hr.fer.zemris.bachelor.Constants;

public class Constants {

    public static int GREEDY_BREAKPOINT_BIT = 4;
    public static int MAX_DATA_SIZE = 10000;
    public static int MAXIMUM_TREE_DEPTH = 2;
    public static double ENVIRONMENT_REWARD_CORRECT = 1000;
    public static double ENVIRONMENT_REWARD_INCORRECT = 0;
    public static double DONT_CARE_PROBABILITY = 0.33;
    public static double MUTATION_PROBABILITY = 0.04;
    public static double LEARNING_RATE = 0.2;
    public static double FITNESS_FALL_OFF_RATE = 0.1;
    public static double PREDICTION_ERROR_THRESHOLD = 10;
    public static double FITNESS_EXPONENT = 5;
    public static double PREDICTION_ERROR_REDUCTION = 0.25;
    public static double FITNESS_REDUCTION = 0.1;
    public static double GA_APPLICATION_THRESHOLD = 25;
    public static double TOURNAMENT_SIZE_RATIO = 0.4;
    public static double CROSSOVER_PROBABILITY = 0.8;
    public static double SUBSUMPTION_EXPERIENCE_THRESHOLD = 20;
    public static double DELETION_EXPERIENCE_THRESHOLD = 20;
    public static double DELETION_FRACTION_OF_MEAN_FITNESS = 0.1;
    public static double REUSE_EXPERIENCE_THRESHOLD = 20;

    public static void setDontCareProbability(double dontCareProbability) {
        Constants.DONT_CARE_PROBABILITY = dontCareProbability;
    }

    public static void setMutationProbability(double mutationProbability) {
        Constants.MUTATION_PROBABILITY = mutationProbability;
    }

}
