package hr.fer.zemris.bachelor.LCS.CodeFragment.Node;

public abstract class UnaryNode extends Node {

    Node child;

    abstract String getOperator();

    @Override
    public String toString() {
        return child.toString() + getOperator();
    }

}
