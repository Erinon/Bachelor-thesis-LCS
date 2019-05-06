package hr.fer.zemris.bachelor.DataProvider;

import hr.fer.zemris.bachelor.Constants.Constants;
import hr.fer.zemris.bachelor.NumberGenerator.NumberGenerator;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MuxDataGenerator implements DataProvider {

    private int controlBits;
    private int count = 0;
    private int size;

    public MuxDataGenerator(int controlBits) {
        if (controlBits < 1) {
            throw new IllegalArgumentException();
        }

        this.controlBits = controlBits;
        this.count = 0;
        this.size = controlBits + (int)Math.round(Math.pow(2, controlBits));
    }

    private void incCount() {
        if (controlBits < Constants.GREEDY_BREAKPOINT_BIT) {
            count = (count + 1) % (int)Math.round(Math.pow(2, size));
        }
    }

    private int generateDataPiece(boolean[] data, boolean randomize) {
        int i;
        int index = 0;
        int targetPow = (int)Math.round(Math.pow(2, controlBits - 1));

        for (i = 0; i < controlBits; i++) {
            if (data[i] = randomize ? NumberGenerator.nextBoolean() : (count & (1 << i)) != 0) {
                index += targetPow;
            }

            targetPow /= 2;
        }

        for (i = controlBits; i < size; i++) {
            data[i] = randomize ? NumberGenerator.nextBoolean() : (count & (1 << i)) != 0;
        }

        if (!randomize) {
            incCount();
        }

        return data[index + controlBits] ? 1 : 0;
    }

    public Set<DataPiece> getData() {
        Set<DataPiece> data = new HashSet<DataPiece>();

        int dataSize = controlBits < Constants.GREEDY_BREAKPOINT_BIT ? (int)Math.round(Math.pow(2, size)) : Constants.MAX_DATA_SIZE;

        for (int i = 0; i < dataSize; i++) {
            data.add(getNextDataPiece());
        }

        return data;
    }

    public DataPiece getNextDataPiece() {
        boolean[] input = new boolean[size];
        int output = generateDataPiece(input, controlBits >= Constants.GREEDY_BREAKPOINT_BIT);

        return new DataPiece(input, output);
    }

}
