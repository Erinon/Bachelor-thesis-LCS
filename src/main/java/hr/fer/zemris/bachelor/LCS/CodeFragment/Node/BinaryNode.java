package hr.fer.zemris.bachelor.LCS.CodeFragment.Node;

public abstract class BinaryNode extends AbstractNode {

    AbstractNode left;
    AbstractNode right;

    abstract String getOperator();

    @Override
    public String toString() {
        return "(" + left.toString() + right.toString() + getOperator() + ")";
    }

}
