package hr.fer.zemris.bachelor.LCS.CodeFragment.Node;

public abstract class BinaryNode extends Node {

    Node left;
    Node right;

    abstract String getOperator();

    @Override
    public String toString() {
        return "(" + left.toString() + right.toString() + getOperator() + ")";
    }

}
