package hr.fer.zemris.bachelor.DataProvider;

import java.util.Set;

public class GeneratorTester {

    public static void main(String[] args) {
        DataProvider dp = new MuxDataGenerator(2);

        Set<DataPiece> dataSet = dp.getData();

        for (DataPiece d : dataSet) {
            System.out.println(d);
        }
    }

}
