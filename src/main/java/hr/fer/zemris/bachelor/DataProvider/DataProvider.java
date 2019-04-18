package hr.fer.zemris.bachelor.DataProvider;

import java.util.Set;

public interface DataProvider {

    Set<DataPiece> getData();
    DataPiece getNextDataPiece();

}
