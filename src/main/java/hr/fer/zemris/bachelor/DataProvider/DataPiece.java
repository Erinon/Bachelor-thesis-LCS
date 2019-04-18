package hr.fer.zemris.bachelor.DataProvider;

import java.util.Arrays;

public class DataPiece {

    private final boolean[] input;
    private final int output;

    public DataPiece(boolean[] input, int output) {
        this.input = input.clone();
        this.output = output;
    }

    public boolean[] getInput() {
        return input.clone();
    }

    public int getOutput() {
        return output;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(input) + output;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (boolean b : input) {
            sb.append(b ? "1" : "0");
        }

        sb.append('\t');
        sb.append("=>");
        sb.append('\t');
        sb.append(output);

        return sb.toString();
    }

}
