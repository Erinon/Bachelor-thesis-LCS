package hr.fer.zemris.bachelor.NumberGenerator;

import java.util.Random;

public class NumberGenerator {

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
