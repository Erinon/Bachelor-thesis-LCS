package hr.fer.zemris.bachelor.RandomNumberGenerator;

import java.util.Random;

public class RandomNumberGenerator {

    private static final Random r = new Random();

    public static int nextInt() {
        return r.nextInt();
    }

    public static int nextInt(int lb, int ub) {
        if (lb > ub) {
            throw new IllegalArgumentException();
        }

        return lb + r.nextInt(ub - lb + 1);
    }

    public static double nextDouble() {
        return r.nextDouble();
    }

    public static double nextDouble(double lb, double ub) {
        if (lb > ub) {
            throw new IllegalArgumentException();
        }

        return lb + (ub - lb) * r.nextDouble();
    }

    public static boolean nextBoolean() {
        return r.nextBoolean();
    }

}
