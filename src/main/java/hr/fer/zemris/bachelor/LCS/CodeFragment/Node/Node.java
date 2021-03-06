package hr.fer.zemris.bachelor.LCS.CodeFragment.Node;

public abstract class Node {

    public abstract boolean getValue(boolean[] input);
    public abstract String toString();

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    public abstract Node deepCopy();

}
